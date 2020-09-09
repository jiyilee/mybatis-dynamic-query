package com.github.wz2cool.dynamic.memory;

import com.github.wz2cool.dynamic.mybatis.db.model.entity.table.Product;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.mapper.entity.EntityField;

/**
 * @author Frank
 * @date 2020/08/24
 **/
public class FieldCacheTest {

    private FieldCache fieldCache = FieldCache.getInstance();

    @Test
    public void TestGetProperty() {
        EntityField entityField = fieldCache.getEntityProperty(Product.class, "price");
        Assert.assertEquals(1, 1);
    }
}
