package com.ontimize.jee.server.dao;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ontimize.db.EntityResult;
import com.ontimize.db.NullValue;
import com.ontimize.jee.common.exceptions.OntimizeJEEException;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.common.tools.CheckingTools;
import com.ontimize.jee.common.tools.EntityResultTools;
import com.ontimize.jee.common.tools.MapTools;
import com.ontimize.jee.common.tools.ObjectTools;

@Component
public class One2OneDaoHelper implements ApplicationContextAware {

	//@formatter:off
	/**
	 * Will define how one to one will work.
	 * <br>When <b>DIRECT</b> type means that both tables has the same primary key, the first insert in main dao, and the key will be passed to the secondary daos.
	 * <br>When <b>INVERSE</b> type means that the main dao is really the main entity and have reference to all other daos with foreign key o them.
	 */
	//@formatter:on
	public enum One2OneType {
		DIRECT, INVERSE
	};

	/** The application context. */
	protected ApplicationContext applicationContext;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext (org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public EntityResult insert(DefaultOntimizeDaoHelper daoHelper, IOntimizeDaoSupport mainDao, List<OneToOneSubDao> secondaryDaos,
			Map<?, ?> attributesValues, One2OneType type) throws OntimizeJEERuntimeException {
		if (type == One2OneType.INVERSE) {
			return this.insertInverse(daoHelper, mainDao, secondaryDaos, attributesValues);
		} else {
			return this.insertDirect(daoHelper, mainDao, secondaryDaos, attributesValues);
		}
	}

	protected EntityResult insertDirect(DefaultOntimizeDaoHelper daoHelper, IOntimizeDaoSupport mainDao, List<OneToOneSubDao> secondaryDaos,
			Map<?, ?> attributesValues) {
		try {
			// Insert in main dao
			EntityResult resInsertMain = daoHelper.insert(mainDao, attributesValues);
			CheckingTools.checkValidEntityResult(resInsertMain, "E_INSERTING_MAIN_DAO");

			// Insert in secondary daos
			if (secondaryDaos != null) {
				for (OneToOneSubDao subDao : secondaryDaos) {
					// Consider to add link Key (it can be primary key or another one)
					MapTools.safePut((Map<Object, Object>) attributesValues, subDao.getKeySecondary(), resInsertMain.get(subDao.getKey()));

					// Check to insert in second dao + update main dao to reference
					if (this.checkColumns(subDao.getDao(), attributesValues, subDao.getNotEnoughColumns())) {
						this.checkRequiredColumns(attributesValues, subDao.getRequiredColumns(), true);
						EntityResult resInsertSecondary = this.insertSubDao(daoHelper, attributesValues, subDao);
						CheckingTools.checkValidEntityResult(resInsertSecondary, "E_INSERTING_SEOCONDARY_DAO");

						// TODO consider to add moreover the keys of one2one tables
					}
				}
			}
			return resInsertMain;
		} catch (Exception ex) {
			throw new OntimizeJEERuntimeException("E_INSERTING_ONE_TO_ONE_", ex);
		}
	}

	protected EntityResult insertInverse(DefaultOntimizeDaoHelper daoHelper, IOntimizeDaoSupport mainDao, List<OneToOneSubDao> secondaryDaos,
			Map<?, ?> attributesValues) {
		try {
			// Insert in secondary daos
			if (secondaryDaos != null) {
				for (OneToOneSubDao subDao : secondaryDaos) {
					// Check to insert in second dao + update main dao to reference
					if (this.checkColumns(subDao.getDao(), attributesValues, subDao.getNotEnoughColumns())//
							&& !ObjectTools.containsIgnoreCase(attributesValues, subDao.getKeySecondary())) {
						this.checkRequiredColumns(attributesValues, subDao.getRequiredColumns(), true);
						EntityResult resInsertSecondary = this.insertSubDao(daoHelper, attributesValues, subDao);

						// Consider to add link Key (it can be primary key or another one)
						MapTools.safePut((Map<Object, Object>) attributesValues, subDao.getKey(), resInsertSecondary.get(subDao.getKeySecondary()));
					}
				}
			}

			// Insert in main dao
			EntityResult resInsertMain = daoHelper.insert(mainDao, attributesValues);
			CheckingTools.checkValidEntityResult(resInsertMain, "E_INSERTING_MAIN_DAO");

			// TODO consider to add moreover the keys of one2one tables

			return resInsertMain;
		} catch (Exception ex) {
			throw new OntimizeJEERuntimeException("E_INSERTING_ONE_TO_ONE_", ex);
		}
	}

	public EntityResult update(DefaultOntimizeDaoHelper daoHelper, IOntimizeDaoSupport mainDao, List<OneToOneSubDao> secondaryDaos,
			Map<?, ?> attributesValues, Map<?, ?> keysValues, One2OneType type) throws OntimizeJEERuntimeException {
		try {
			EntityResult result = null;
			// Checks formodifications in main dao
			if (this.checkColumns(mainDao, attributesValues, null)) {
				EntityResult resUpdateMain = daoHelper.update(mainDao, attributesValues, keysValues);
				CheckingTools.checkValidEntityResult(resUpdateMain, "E_UPDATING_MAIN_DAO");
				result = resUpdateMain;
			}

			// Insert in secondary daos
			if (secondaryDaos != null) {
				for (OneToOneSubDao subDao : secondaryDaos) {
					// Checks for modifications in second dao
					if (this.checkColumns(subDao.getDao(), attributesValues, null)) {
						// Checks is secondary exists (then update) else insert
						boolean joinKeyIsMain = keysValues.containsKey(subDao.getKeySecondary());
						Object keyValue = this.checksIfExists(daoHelper, mainDao, subDao.getDao(), keysValues, subDao.getKeySecondary());
						if (keyValue != null) {
							this.checkRequiredColumns(attributesValues, subDao.getRequiredColumns(), false);
							EntityResult resUpdateSecond = this.updateSubDao(daoHelper, attributesValues, subDao, keyValue);
							CheckingTools.checkValidEntityResult(resUpdateSecond, "E_UPDATING_SEOCONDARY_DAO");
							result = resUpdateSecond;
						} else if (this.checkColumns(subDao.getDao(), attributesValues, subDao.getNotEnoughColumns())) {
							this.checkRequiredColumns(attributesValues, subDao.getRequiredColumns(), true);
							Map<Object, Object> values = new Hashtable<>();
							values.putAll(attributesValues);
							values.putAll(keysValues);
							EntityResult resInsertSecondary = this.insertSubDao(daoHelper, values, subDao);
							CheckingTools.checkValidEntityResult(resInsertSecondary, "E_INSERTING_SEOCONDARY_DAO");
							if ((type == One2OneType.INVERSE) && !joinKeyIsMain) {
								// Update reference from main dao to secondary
								EntityResult resUpdateMain = daoHelper.update(mainDao,
										EntityResultTools.keysvalues(subDao.getKey(), resInsertSecondary.get(subDao.getKeySecondary())), keysValues);
								CheckingTools.checkValidEntityResult(resUpdateMain, "E_UPDATING_MAIN_DAO");
							}
							result = resInsertSecondary;
						}
					}
				}
			}
			if (result == null) {
				throw new Exception("NO_DATA_TO_MODIFY");
			}
			return result;
		} catch (OntimizeJEERuntimeException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new OntimizeJEERuntimeException("E_UPDATING_ONE_TO_ONE", ex);
		}
	}

	public EntityResult delete(DefaultOntimizeDaoHelper daoHelper, IOntimizeDaoSupport mainDao, List<OneToOneSubDao> secondaryDaos,
			Map<?, ?> attributesValues) {
		try {
			// Delete in secondary daos
			if (secondaryDaos != null) {
				List<String> queryColumns = new ArrayList<>();
				for (OneToOneSubDao subDao : secondaryDaos) {
					queryColumns.add(subDao.getKey());
				}
				EntityResult resQuery = daoHelper.query(mainDao, attributesValues, queryColumns);

				if (resQuery.calculateRecordNumber() != 1) {
					throw new OntimizeJEERuntimeException("E_QUERY_MAIN_DAO");
				}

				for (OneToOneSubDao subDao : secondaryDaos) {
					Object idSubDao = ((Vector<Object>) resQuery.get(subDao.getKey())).get(0);
					if (idSubDao != null) {
						this.deleteSubDao(daoHelper, EntityResultTools.keysvalues(subDao.getKeySecondary(), idSubDao), subDao);
					}
				}
			}

			// Delete in main dao
			EntityResult resDeleteMain = daoHelper.delete(mainDao, attributesValues);

			return resDeleteMain;
		} catch (Exception ex) {
			throw new OntimizeJEERuntimeException("E_DELETING_ONE_TO_ONE_", ex);
		}
	}

	private EntityResult deleteSubDao(DefaultOntimizeDaoHelper daoHelper, Map<?, ?> attributesValues, OneToOneSubDao subDao)
			throws OntimizeJEEException {
		if (subDao.getListener() != null) {
			subDao.getListener().preDelete(subDao.getDao(), attributesValues);
		}

		EntityResult resDeleteSecondary = daoHelper.delete(subDao.getDao(), attributesValues);

		if (subDao.getListener() != null) {
			subDao.getListener().postDelete(subDao.getDao(), attributesValues, resDeleteSecondary);
		}
		CheckingTools.checkValidEntityResult(resDeleteSecondary, "E_DELETING_SECONDARY_DAO");
		return resDeleteSecondary;
	}

	private EntityResult insertSubDao(DefaultOntimizeDaoHelper daoHelper, Map<?, ?> attributesValues, OneToOneSubDao subDao)
			throws OntimizeJEEException {
		if (subDao.getListener() != null) {
			subDao.getListener().preInsert(subDao.getDao(), attributesValues);
		}

		EntityResult resInsertSecondary = daoHelper.insert(subDao.getDao(), attributesValues);

		if (subDao.getListener() != null) {
			subDao.getListener().postInsert(subDao.getDao(), attributesValues, resInsertSecondary);
		}
		CheckingTools.checkValidEntityResult(resInsertSecondary, "E_INSERTING_SEOCONDARY_DAO");
		return resInsertSecondary;
	}

	private EntityResult updateSubDao(DefaultOntimizeDaoHelper daoHelper, Map<?, ?> attributesValues, OneToOneSubDao subDao, Object keyValue) {
		Hashtable<Object, Object> kv2 = EntityResultTools.keysvalues(subDao.getKeySecondary(), keyValue);
		if (subDao.getListener() != null) {
			subDao.getListener().preUpdate(subDao.getDao(), attributesValues, kv2);
		}

		EntityResult resUpdateSecond = daoHelper.update(subDao.getDao(), attributesValues, kv2);

		if (subDao.getListener() != null) {
			subDao.getListener().postUpdate(subDao.getDao(), attributesValues, kv2, resUpdateSecond);
		}
		return resUpdateSecond;
	}

	protected Object checksIfExists(DefaultOntimizeDaoHelper daoHelper, IOntimizeDaoSupport mainDao, IOntimizeDaoSupport secondaryDao,
			Map<?, ?> keysValues, String joinKeySecondary) throws Exception {
		Object secondaryKey = null;
		if (!keysValues.containsKey(joinKeySecondary)) {
			EntityResult resQuery = daoHelper.query(mainDao, keysValues, EntityResultTools.attributes(joinKeySecondary));
			CheckingTools.checkValidEntityResult(resQuery, "E_QUERYING_KEYS_IN_MAIN_DAO");
			secondaryKey = ((Vector) resQuery.get(joinKeySecondary)).get(0);
		} else {
			secondaryKey = keysValues.get(joinKeySecondary);
		}
		if (secondaryKey == null) {
			return null;
		}
		EntityResult resQuery = daoHelper.query(secondaryDao, EntityResultTools.keysvalues(joinKeySecondary, secondaryKey),
				EntityResultTools.attributes(joinKeySecondary));
		CheckingTools.checkValidEntityResult(resQuery, "E_QUERYING_IN_SECONDARY_DAO");
		return (resQuery.calculateRecordNumber() == 1) ? secondaryKey : null;
	}

	protected boolean checkColumns(IOntimizeDaoSupport dao, Map<?, ?> valuesToChange, List<String> notEnoughColumns) {
		List<DaoProperty> cudProperties = dao.getCudProperties();
		if (cudProperties != null) {
			for (DaoProperty daoProp : cudProperties) {
				String propertyName = daoProp.getPropertyName();
				if (ObjectTools.containsIgnoreCase(valuesToChange, propertyName) //
						&& ((notEnoughColumns == null) || !ObjectTools.containsIgnoreCase(notEnoughColumns, propertyName))) {
					return true;
				}
			}
		}
		return false;
	}

	protected void checkRequiredColumns(Map<?, ?> valuesToChange, List<String> requiredColumns, boolean toInsert) {
		if (requiredColumns != null) {
			for (String s : requiredColumns) {
				if (//
						(toInsert && (!valuesToChange.containsKey(s) || (valuesToChange.get(s) == null) || (valuesToChange.get(s) instanceof NullValue))) //
						|| //
						((valuesToChange.containsKey(s) && (valuesToChange.get(s) == null)) || (valuesToChange.get(s) instanceof NullValue))) {//
					throw new OntimizeJEERuntimeException("E_REQUIRED_" + s);
				}
			}
		}
	}

	public static class OneToOneSubDao {
		protected IOntimizeDaoSupport	dao;
		protected String				key;
		protected String				keySecondary;

		/**
		 * If specified, this columns is required in insert action to ensure not insert empty data in this secondary dao.
		 */
		protected List<String>			notEnoughColumns;

		/**
		 * Columns required to have a value (not nullables)
		 */
		protected List<String>			requiredColumns;

		protected IListenerSubDao		listener;

		public OneToOneSubDao(IOntimizeDaoSupport dao, String key) {
			this(dao, key, key);
		}

		public OneToOneSubDao(IOntimizeDaoSupport dao, String key, String keyecondary) {
			this(dao, key, key, null);
		}

		public OneToOneSubDao(IOntimizeDaoSupport dao, String key, String keyecondary, List<String> notEnoughColumns) {
			this(dao, key, key, null, null);
		}

		public OneToOneSubDao(IOntimizeDaoSupport dao, String key, String keySecondary, List<String> notEnoughColumns, List<String> requiredColumns) {
			this(dao, key, key, null, null, null);
		}

		public OneToOneSubDao(IOntimizeDaoSupport dao, String key, String keySecondary, List<String> notEnoughColumns, List<String> requiredColumns,
				IListenerSubDao listener) {
			this.dao = dao;
			this.key = key;
			this.keySecondary = keySecondary;
			this.notEnoughColumns = notEnoughColumns;
			this.requiredColumns = requiredColumns;
			this.listener = listener;
		}

		public IOntimizeDaoSupport getDao() {
			return this.dao;
		}

		public String getKey() {
			return this.key;
		}

		public String getKeySecondary() {
			return this.keySecondary;
		}

		public List<String> getNotEnoughColumns() {
			return this.notEnoughColumns;
		}

		public List<String> getRequiredColumns() {
			return this.requiredColumns;
		}

		public IListenerSubDao getListener() {
			return this.listener;
		}
	}

	public static interface IListenerSubDao {
		void preInsert(IOntimizeDaoSupport dao, Map<?, ?> values);

		void postInsert(IOntimizeDaoSupport dao, Map<?, ?> values, EntityResult resInsert);

		void preDelete(IOntimizeDaoSupport dao, Map<?, ?> values);

		void postDelete(IOntimizeDaoSupport dao, Map<?, ?> values, EntityResult resDelete);

		void preUpdate(IOntimizeDaoSupport dao, Map<?, ?> av, Hashtable<Object, Object> kv);

		void postUpdate(IOntimizeDaoSupport dao, Map<?, ?> av, Hashtable<Object, Object> kv, EntityResult resUpdate);
	}

	public static class AbstractListenerSubDao implements IListenerSubDao {

		@Override
		public void preInsert(IOntimizeDaoSupport dao, Map<?, ?> values) {}

		@Override
		public void postInsert(IOntimizeDaoSupport dao, Map<?, ?> values, EntityResult resInsert) {}

		@Override
		public void preDelete(IOntimizeDaoSupport dao, Map<?, ?> values) {}

		@Override
		public void postDelete(IOntimizeDaoSupport dao, Map<?, ?> values, EntityResult resDelete) {}

		@Override
		public void preUpdate(IOntimizeDaoSupport dao, Map<?, ?> av, Hashtable<Object, Object> kv) {}

		@Override
		public void postUpdate(IOntimizeDaoSupport dao, Map<?, ?> av, Hashtable<Object, Object> kv, EntityResult resUpdate) {}

	}
}