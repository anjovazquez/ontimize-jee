package com.ontimize.jee.common.tools.ertools;

import java.util.List;
import java.util.Map;

import com.ontimize.db.EntityResult;

public interface IAggregateFunction<T extends IPartialAggregateValue> {

	Map<String, Object> computeAggregatedGroupValue(T partialValue);

	T onNewGroupRecord(T partialValue, EntityResult res, int idx);

	List<String> getAggregatedColumnNames();

}