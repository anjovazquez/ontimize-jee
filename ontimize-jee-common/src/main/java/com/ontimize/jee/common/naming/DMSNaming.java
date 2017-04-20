package com.ontimize.jee.common.naming;

public interface DMSNaming {
	String	DOCUMENT_ID_DMS_DOCUMENT							= "ID_DMS_DOC";
	String	DOCUMENT_UPDATE_DATE								= "UPDATE_DATE";
	String	DOCUMENT_UPDATE_BY									= "UPDATE_BY";
	String	DOCUMENT_DOCUMENT_NAME								= "DOC_NAME";
	String	DOCUMENT_OWNER_ID									= "OWNER_ID";
	String	DOCUMENT_DOCUMENT_DESCRIPTION						= "DOC_DESCRIPTION";
	String	DOCUMENT_DOCUMENT_KEYWORDS							= "DOC_KEYWORDS";

	String	DOCUMENT_FILE_ID_DMS_DOCUMENT_FILE					= "ID_DMS_DOC_FILE";
	String	DOCUMENT_FILE_NAME									= "FILE_NAME";
	String	DOCUMENT_FILE_TYPE									= "FILE_TYPE";
	// String DOCUMENT_FILE_SEQUENCE = "FILE_SEQUENCE";

	String	RELATED_DOCUMENT_ID_DMS_RELATED_DOCUMENT			= "ID_DMS_RELATED_DOC";
	String	RELATED_DOCUMENT_ID_DMS_DOCUMENT_MASTER				= "ID_DMS_DOC_MASTER";
	String	RELATED_DOCUMENT_ID_DMS_DOCUMENT_CHILD				= "ID_DMS_DOC_CHILD";

	String	DOCUMENT_PROPERTY_ID_DMS_DOCUMENT_PROPERTY			= "ID_DMS_DOC_PROPERTY";
	String	DOCUMENT_PROPERTY_DOCUMENT_PROPERTY_KEY				= "DOC_PROPERTY_KEY";
	String	DOCUMENT_PROPERTY_DOCUMENT_PROPERTY_VALUE			= "DOC_PROPERTY_VALUE";

	String	DOCUMENT_FILE_VERSION_ID_DMS_DOCUMENT_FILE_VERSION	= "ID_DMS_DOC_FILE_VERSION";
	String	DOCUMENT_FILE_VERSION_FILE_PATH						= "FILE_PATH";
	String	DOCUMENT_FILE_VERSION_VERSION						= "VERSION";
	String	DOCUMENT_FILE_VERSION_FILE_DESCRIPTION				= "FILE_DESCRIPTION";
	String	DOCUMENT_FILE_VERSION_FILE_SIZE						= "FILE_SIZE";
	String	DOCUMENT_FILE_VERSION_IS_ACTIVE						= "IS_ACTIVE";
	String	DOCUMENT_FILE_VERSION_THUMBNAIL						= "THUMBNAIL";
	String	DOCUMENT_FILE_VERSION_FILE_ADDED_DATE				= "FILE_ADDED_DATE";
	String	DOCUMENT_FILE_VERSION_FILE_ADDED_USER				= "FILE_ADDED_USER_ID";

	String	CATEGORY_ID_CATEGORY								= "ID_DMS_DOC_CATEGORY";
	String	CATEGORY_CATEGORY_NAME								= "CATEGORY_NAME";
	String	CATEGORY_ID_CATEGORY_PARENT							= "ID_DMS_DOC_CATEGORY_PARENT";

	String	ERROR_FILE_ID_MANDATORY								= "dms.E_FILE_ID_IS_MANDATORY";
	String	ERROR_ACTIVE_VERSION_NOT_FOUND						= "dms.E_ACTIVE_VERSION_NOT_FOUND";
	String	ERROR_DOCUMENT_ID_MANDATORY							= "dms.DOCUMENT_ID_MANDATORY";
	String	ERROR_FILE_NAME_MANDATORY							= "dms.E_FILE_NAME_MANDATORY";
	String	ERROR_ERROR_CREATING_FILE							= "dms.E_ERROR_CREATING_FILE";
	String	ERROR_DOCUMENT_NOT_FOUND							= "dms.E_DOCUMENT_NOT_FOUND";
	String	ERROR_FILE_ALREADY_EXISTS							= "dms.E_FILE_ALREADY_EXISTS";
	String	ERROR_NO_FILE_SEQUENCE								= "dms.E_NO_FILE_SEQUENCE";
	String	ERROR_NO_FILE_VERSION								= "dms.E_NO_FILE_VERSION";
	String	ERROR_FILE_VERSION_ID_IS_MANDATORY					= "dms.E_FILE_VERSION_ID_IS_MANDATORY";
	String	ERROR_FILE_VERSION_NOT_FOUND						= "dms.E_FILE_VERSION_NOT_FOUND";
	String	ERROR_FILE_NOT_FOUND								= "dms.E_FILE_NOT_FOUND";
	String	ERROR_CREATING_FILE_VERSION							= "dms.E_CREATING_FILE_VERSION";
	String	ERROR_PROPERTY_KEY_MANDATORY						= "dms.E_PROPERTY_KEY_MANDATORY";
	String	ERROR_INPUTSTREAM_IS_MANDATORY						= "dms.E_INPUTSTREAM_IS_MANDATORY";
	String	ERROR_VERSION_ALREADY_EXISTS						= "dms.E_VERSION_ALREADY_EXISTS";
	String	ERROR_CATEGORY_NAME_MANDATORY						= "dms.E_CATEGORY_NAME_MANDATORY";
	String	ERROR_CATEGORY_ID_MANDATORY							= "dms.E_CATEGORY_ID_MANDATORY";

}