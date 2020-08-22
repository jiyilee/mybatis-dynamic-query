package com.github.wz2cool.dynamic.memory.helper;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Frank
 * @date 2020/08/22
 **/
public class ExpressionHelperTest {


    @Test
    public void testDefaultConstructor() {
        String result = ExpressionHelper.getEqualExpression(Integer.class, "x.getAge()", 30);
        Assert.assertEquals("x.getAge() != null && x.getAge().compareTo(Integer.valueOf(\"30\")) == 0", result);
    }
}
