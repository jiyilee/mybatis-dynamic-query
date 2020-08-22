package com.github.wz2cool.dynamic.memory.helper;

import com.github.wz2cool.dynamic.FilterOperator;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Frank on 7/17/2017.
 */
class ExpressionHelper {

    static String getExpression(final FilterOperator operator, Class propertyType, final String getProperty, final Object filterValue) {
        switch (operator) {
            case EQUAL:
                return getEqualExpression(propertyType, getProperty, filterValue);
            default:
                throw new UnsupportedOperationException(String.format("not support operator: %s", operator));
        }
    }

    static String getEqualExpression(Class propertyType, String getProperty, Object filterValue) {
        if (filterValue == null) {
            return String.format("%s == null", getProperty);
        }
        String propertyExpression = getPropertyExpression(propertyType, getProperty);
        String valueExpression = getValueExpression(propertyType, filterValue);
        // 因为前面已经判断了筛选值不是空, BigDecimal 一定要用compareTo 才能判断是否相等
        return String.format("%s != null && %s.compareTo(%s) == 0", getProperty, propertyExpression, valueExpression);
    }

    private static String getPropertyExpression(Class propertyType, String getProperty) {
        if (Date.class.isAssignableFrom(propertyType)) {
            return String.format("Long.valueOf(\"%s\"))", getProperty);
        }
        return getProperty;
    }

    private static String getValueExpression(Class propertyType, Object filterValue) {
        if (filterValue == null) {
            return "null";
        }
        String expression = filterValue.toString();
        if (propertyType == BigDecimal.class) {
            return String.format("new BigDecimal(\"%s\")", expression);
        }
        if (propertyType == Byte.class) {
            return String.format("Byte.valueOf(\"%s\")", expression);
        }
        if (Date.class.isAssignableFrom(propertyType)) {
            return String.format("Long.valueOf(\"%s\")", ((Date) filterValue).getTime());
        }
        if (propertyType == Double.class) {
            return String.format("Double.valueOf(\"%s\")", expression);
        }
        if (propertyType == Float.class) {
            return String.format("Float.valueOf(\"%s\")", expression);
        }
        if (propertyType == Integer.class) {
            return String.format("Integer.valueOf(\"%s\")", expression);
        }
        if (propertyType == Long.class) {
            return String.format("Long.valueOf(\"%s\")", expression);
        }
        if (propertyType == Short.class) {
            return String.format("Short.valueOf(\"%s\")", expression);
        }
        return expression;
    }


}
