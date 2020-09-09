package com.github.wz2cool.dynamic.memory;

import com.github.wz2cool.dynamic.EntityCache;
import tk.mybatis.mapper.entity.EntityField;
import tk.mybatis.mapper.mapperhelper.FieldHelper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Frank
 * @date 2020/08/24
 **/
public class FieldCache {

    private static final Map<Class, Map<String, EntityField>> ENTITY_FIELD_MAP = new ConcurrentHashMap<>();
    private static FieldCache instance = new FieldCache();

    private FieldCache() {
    }

    public static FieldCache getInstance() {
        return instance;
    }

    public EntityField getEntityProperty(Class clazz, String propertyName) {
        Map<String, EntityField> entityFieldMap = ENTITY_FIELD_MAP.get(clazz);
        if (Objects.nonNull(entityFieldMap)) {
            return entityFieldMap.get(propertyName);
        }
        Map<String, EntityField> map = new ConcurrentHashMap<>();
        List<EntityField> entityFieldList = FieldHelper.getProperties(clazz);
        for (EntityField entityField : entityFieldList) {
            map.put(entityField.getName(), entityField);
        }
        ENTITY_FIELD_MAP.put(clazz, map);
        return map.get(propertyName);
    }
}
