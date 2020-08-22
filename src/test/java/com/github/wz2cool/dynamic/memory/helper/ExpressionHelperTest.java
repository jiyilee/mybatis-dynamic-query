package com.github.wz2cool.dynamic.memory.helper;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @author Frank
 * @date 2020/08/22
 **/
public class ExpressionHelperTest {


    @Test
    public void testIntegerEqual() {
        String result = ExpressionHelper.getEqualExpression(Integer.class, "x.getAge()", 30);
        Assert.assertEquals("x.getAge() != null && x.getAge().compareTo(Integer.valueOf(\"30\")) == 0", result);
    }

    @Test
    public void testBigDecimalEqual() {
        String result = ExpressionHelper.getEqualExpression(BigDecimal.class, "x.getPrice()", new BigDecimal(10));
        Assert.assertEquals("x.getPrice() != null && x.getPrice().compareTo(new BigDecimal(\"10\")) == 0", result);
    }

    @Test
    public void testDateEqual() {
        String result = ExpressionHelper.getEqualExpression(Date.class, "x.getIssueDate",
                Date.valueOf(LocalDate.of(2000, 1, 1)));
        Assert.assertEquals("x.getIssueDate != null && Long.valueOf(x.getIssueDate.getTime())).compareTo(Long.valueOf(\"946656000000\")) == 0", result);
    }
}
