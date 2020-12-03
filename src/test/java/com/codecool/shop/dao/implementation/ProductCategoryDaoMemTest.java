package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ProductCategoryDaoMemTest {

    private static ProductCategoryDao productDataStore;
    private static ProductCategory mockCategory;
    private static ProductCategory mockCategory2;
    private static ProductCategory mockCategory3;


    @BeforeAll
    static void beforeAll() {
        productDataStore = ProductCategoryDaoMem.getInstance();
        mockCategory = mock(ProductCategory.class);
        mockCategory2 = mock(ProductCategory.class);
        mockCategory3 = mock(ProductCategory.class);
    }

    @BeforeEach
    void BeforeEach() {
        productDataStore.add(mockCategory);
        productDataStore.add(mockCategory2);
        productDataStore.add(mockCategory3);
    }

    @AfterEach
    void AfterEach() {
        productDataStore.removeAll();
    }

    @Test
    void testGetAll_NotEmptyList_MoreThanZero() {
        int expected = 3;

        assertEquals(expected, productDataStore.getAll().size());
    }

    @Test
    void testAdd_OneProductCategory_SizeIncreasesByOne() {
        int size = productDataStore.getAll().size();

        productDataStore.add(mockCategory);

        int expected = size + 1;

        assertEquals(expected, productDataStore.getAll().size());
    }

    @Test
    void testFind_NotExistingId_ReturnNull() {
        assertEquals(null, productDataStore.find(4));
    }

    @Test
    void testFind_ExistingId_ReturnExpectedCategory() {
        when(mockCategory2.getId()).thenReturn(2);

        assertEquals(mockCategory2, productDataStore.find(2));
    }

    @Test
    void testRemove_NotExistingId_SizeNotChange() {
        productDataStore.remove(4);

        int expected = 3;

        assertEquals(expected, productDataStore.getAll().size());
    }

    @Test
    void testRemove_ExistingId_SizeDecreasing() {
        productDataStore.remove(2);

        int expected = 2;

        assertEquals(expected, productDataStore.getAll().size());
    }

    @Test
    void testRemoveAll_ExistingId_SizeEqualsZero() {
        productDataStore.removeAll();

        int expected = 0;

        assertEquals(expected, productDataStore.getAll().size());
    }

}
