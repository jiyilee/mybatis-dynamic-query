package com.github.wz2cool.dynamic.memory;

import com.github.wz2cool.dynamic.FilterDescriptor;

/**
 * @author Frank
 * @date 2020/08/24
 **/
public class QueryHelper {

    private static final FieldCache FIELD_CACHE = FieldCache.getInstance();

    public static String getFilter(Class clazz, FilterDescriptor filterDescriptor) {
        ExpressionHelper.getEqualExpression(clazz, filterDescriptor)
    }
}
