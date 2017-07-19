package com.frwan.query.dynamic;

import com.frwan.query.dynamic.mybatis.DatabaseType;
import com.frwan.query.dynamic.mybatis.MybatisQueryProvider;
import com.frwan.query.dynamic.mybatis.ParamExpression;
import com.frwan.query.dynamic.mybatis.db.mapper.NorthwindDao;
import com.frwan.query.dynamic.mybatis.db.model.entity.table.Category;
import com.frwan.query.dynamic.mybatis.db.model.entity.table.Product;
import com.frwan.query.dynamic.mybatis.db.model.entity.view.ProductView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by Frank on 2017/7/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
public class DbFilterTest {

    @Value("${spring.profiles.active}")
    private String active;

    @Autowired
    private NorthwindDao northwindDao;
    MybatisQueryProvider mybatisQueryProvider;

    @Before
    public void beforeTest() {
        DatabaseType databaseType;
        if ("mysql".equalsIgnoreCase(active)) {
            databaseType = DatabaseType.MYSQL;
        } else if ("sqlserver".equalsIgnoreCase(active)) {
            databaseType = DatabaseType.SQLSERVER;
        } else if ("postresql".equalsIgnoreCase(active)) {
            databaseType = DatabaseType.POSTRESQL;
        } else {
            databaseType = DatabaseType.H2;
        }
        mybatisQueryProvider = new MybatisQueryProvider(databaseType);
    }

    @Test
    public void testDbInit() throws Exception {
        List<Category> categories = northwindDao.getCategories();
        assertEquals(true, categories.size() > 0);

        List<Product> products = northwindDao.getProducts();
        assertEquals(true, products.size() > 0);
    }

    @Test
    public void testLessThan() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.LESS_THAN, 3);
        ParamExpression paramExpression = mybatisQueryProvider.getWhereExpression(ProductView.class, idFilter);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(paramExpression.getParamMap());
        queryParams.put("whereExpression", paramExpression.getExpression());
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams);
        assertEquals(true, productViews.stream().allMatch(x -> x.getProductID() < 3));
    }

    @Test
    public void testLessThanOrEqual() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.LESS_THAN_OR_EQUAL, 3);
        ParamExpression paramExpression = mybatisQueryProvider.getWhereExpression(ProductView.class, idFilter);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(paramExpression.getParamMap());
        queryParams.put("whereExpression", paramExpression.getExpression());
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams);
        assertEquals(true, productViews.stream().allMatch(x -> x.getProductID() <= 3));
    }

    @Test
    public void testEqual() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.EQUAL, 2);

        ParamExpression paramExpression = mybatisQueryProvider.getWhereExpression(ProductView.class, idFilter);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(paramExpression.getParamMap());
        queryParams.put("whereExpression", paramExpression.getExpression());

        ProductView productView =
                northwindDao.getProductViewsByDynamic(queryParams).stream().findFirst().orElse(null);
        assertEquals(Long.valueOf(2), productView.getProductID());
    }

    @Test
    public void testEqualNull() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.EQUAL, null);

        ParamExpression paramExpression = mybatisQueryProvider.getWhereExpression(ProductView.class, idFilter);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(paramExpression.getParamMap());
        queryParams.put("whereExpression", paramExpression.getExpression());

        ProductView productView =
                northwindDao.getProductViewsByDynamic(queryParams).stream().findFirst().orElse(null);
        assertEquals(null, productView);
    }

    @Test
    public void testNotEqual() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.NOT_EQUAL, 2);

        ParamExpression paramExpression = mybatisQueryProvider.getWhereExpression(ProductView.class, idFilter);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(paramExpression.getParamMap());
        queryParams.put("whereExpression", paramExpression.getExpression());

        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams);
        assertEquals(true, productViews.stream().noneMatch(x -> x.getProductID() == 2));
    }

    @Test
    public void testNotEqualNull() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.NOT_EQUAL, null);

        ParamExpression paramExpression = mybatisQueryProvider.getWhereExpression(ProductView.class, idFilter);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(paramExpression.getParamMap());
        queryParams.put("whereExpression", paramExpression.getExpression());

        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams);
        assertEquals(true, productViews.stream().noneMatch(x -> x.getProductID() == null));
    }


    @Test
    public void testGreaterThanOrEqual() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.GREATER_THAN_OR_EQUAL, 2);
        ParamExpression paramExpression = mybatisQueryProvider.getWhereExpression(ProductView.class, idFilter);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(paramExpression.getParamMap());
        queryParams.put("whereExpression", paramExpression.getExpression());
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams);
        assertEquals(true, productViews.stream().allMatch(x -> x.getProductID() >= 2));
    }

    @Test
    public void testGreaterThan() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.GREATER_THAN, 2);
        ParamExpression paramExpression = mybatisQueryProvider.getWhereExpression(ProductView.class, idFilter);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(paramExpression.getParamMap());
        queryParams.put("whereExpression", paramExpression.getExpression());
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams);
        assertEquals(true, productViews.stream().allMatch(x -> x.getProductID() > 2));
    }

    @Test
    public void testStartWith() throws Exception {
        FilterDescriptor nameFilter =
                new FilterDescriptor(FilterCondition.AND, "categoryName", FilterOperator.START_WITH, "Be");
        Map<String, Object> queryParams =
                mybatisQueryProvider.getWhereQueryParamMap(
                        ProductView.class, "whereExpression", nameFilter);
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams);
        assertEquals("Beverages", productViews.get(0).getCategoryName());
    }

    @Test
    public void testEndWith() throws Exception {
        FilterDescriptor nameFilter =
                new FilterDescriptor(FilterCondition.AND, "categoryName", FilterOperator.END_WITH, "l");
        Map<String, Object> queryParams =
                mybatisQueryProvider.getWhereQueryParamMap(
                        ProductView.class, "whereExpression", nameFilter);
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams);
        assertEquals("Oil", productViews.get(0).getCategoryName());
    }

    @Test
    public void testIn() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.IN, new int[]{2, 4});
        Map<String, Object> queryParams =
                mybatisQueryProvider.getWhereQueryParamMap(
                        ProductView.class, "whereExpression", idFilter);
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams);
        assertEquals(Long.valueOf(2), productViews.get(0).getProductID());
        assertEquals(Long.valueOf(4), productViews.get(1).getProductID());
    }

    @Test
    public void testNotIn() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.NOT_IN, new int[]{2, 4});
        Map<String, Object> queryParams =
                mybatisQueryProvider.getWhereQueryParamMap(
                        ProductView.class, "whereExpression", idFilter);
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams);
        assertEquals(Long.valueOf(1), productViews.get(0).getProductID());
        assertEquals(Long.valueOf(3), productViews.get(1).getProductID());
    }

    @Test
    public void testBetween() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.BETWEEN, new int[]{2, 4});
        Map<String, Object> queryParams =
                mybatisQueryProvider.getWhereQueryParamMap(
                        ProductView.class, "whereExpression", idFilter);
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams)
                .stream().sorted(Comparator.comparing(ProductView::getProductID)).collect(Collectors.toList());
        assertEquals(Long.valueOf(2), productViews.get(0).getProductID());
        assertEquals(Long.valueOf(3), productViews.get(1).getProductID());
        assertEquals(Long.valueOf(4), productViews.get(2).getProductID());
    }

    @Test
    public void testBITWISE_GREATER_ZERO() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.BITAND_GREATER_ZERO, 2);
        Map<String, Object> queryParams =
                mybatisQueryProvider.getWhereQueryParamMap(
                        ProductView.class, "whereExpression", idFilter);
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams)
                .stream().sorted(Comparator.comparing(ProductView::getProductID)).collect(Collectors.toList());
        assertEquals(Long.valueOf(2), productViews.get(0).getProductID());
        assertEquals(Long.valueOf(3), productViews.get(1).getProductID());
    }

    @Test
    public void testBITWISE_EQUAL_ZERO() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.BITAND_EQUAL_ZERO, 2);
        Map<String, Object> queryParams =
                mybatisQueryProvider.getWhereQueryParamMap(
                        ProductView.class, "whereExpression", idFilter);
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams)
                .stream().sorted(Comparator.comparing(ProductView::getProductID)).collect(Collectors.toList());
        ;
        assertEquals(Long.valueOf(1), productViews.get(0).getProductID());
        assertEquals(Long.valueOf(4), productViews.get(1).getProductID());
    }


    @Test
    public void testBITWISE_EQUAL_INPUT() throws Exception {
        FilterDescriptor idFilter =
                new FilterDescriptor(FilterCondition.AND, "productID", FilterOperator.BITAND_EQUAL_INPUT, 3);
        Map<String, Object> queryParams =
                mybatisQueryProvider.getWhereQueryParamMap(
                        ProductView.class, "whereExpression", idFilter);
        List<ProductView> productViews = northwindDao.getProductViewsByDynamic(queryParams);
        assertEquals(Long.valueOf(3), productViews.get(0).getProductID());
    }
}