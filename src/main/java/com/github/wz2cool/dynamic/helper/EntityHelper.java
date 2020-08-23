package com.github.wz2cool.dynamic.helper;

import com.github.wz2cool.dynamic.exception.PropertyNotFoundInternalException;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;

/**
 * @author Frank
 */
public class EntityHelper {
    private EntityHelper() {
        throw new UnsupportedOperationException();
    }

    public static String getTableName(final Class<?> tableClass) {
        if (tableClass == null) {
            throw new NullPointerException("tableClass");
        }

        if (tableClass.isAnnotationPresent(Table.class)) {
            Table table = tableClass.getAnnotation(Table.class);
            String dbTableName = table.name();
            if (StringUtils.isNotBlank(dbTableName)) {
                return dbTableName;
            }
        }

        String useTableName = tableClass.getSimpleName();
        return camelCaseToUnderscore(useTableName);
    }

    public static String getColumnNameByProperty(final String propertyName, final Field[] properties) {
        Field matchProperty = getPropertyField(propertyName, properties);
        Column column = getColumnByProperty(propertyName, properties);
        if (column != null && StringUtils.isNotBlank(column.name())) {
            return column.name();
        }


        String usePropertyName = matchProperty.getName();
        return camelCaseToUnderscore(usePropertyName);
    }

    public static Column getColumnByProperty(final String propertyName, final Field[] properties) {
        Field matchProperty = getPropertyField(propertyName, properties);
        if (matchProperty.isAnnotationPresent(Column.class)) {
            return matchProperty.getAnnotation(Column.class);
        }

        return null;
    }

    public static Field getPropertyField(final String propertyName, final Field[] properties) {
        if (StringUtils.isBlank(propertyName) || properties == null || properties.length == 0) {
            throw new PropertyNotFoundInternalException(String.format("Can't find property: %s", propertyName));
        }

        Field result = null;
        for (Field property : properties) {
            if (propertyName.trim().equalsIgnoreCase(property.getName())) {
                result = property;
                break;
            }
        }

        if (result != null) {
            return result;
        } else {
            throw new PropertyNotFoundInternalException(String.format("Can't find property: %s", propertyName));
        }
    }

    public static String camelCaseToUnderscore(String str) {
        return str.replaceAll("(.)(\\p{Lu})", "$1_$2").toLowerCase();
    }
}
