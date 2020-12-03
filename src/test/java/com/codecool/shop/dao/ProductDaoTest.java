package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDaoTest {
    private static ProductDao productDataStore;
    private static Product mockProduct;
    private static Product mockProduct2;
    private static Product mockProduct3;
    private static Supplier supplier = new Supplier("First", "");
    private static ProductCategory productCategory = new ProductCategory("First", "", "");

    @BeforeEach
    void beforeEach() {
        //productDataStore.removeAll();
    }

    @BeforeAll
    static void beforeAll(){
        productDataStore = ProductDaoMem.getInstance();
        mockProduct = mock(Product.class);
        mockProduct2 = mock(Product.class);
        mockProduct3 = mock(Product.class);
        when(mockProduct.getId()).thenReturn(0);
        when(mockProduct2.getId()).thenReturn(1);
        when(mockProduct3.getId()).thenReturn(2);
        when(mockProduct.getSupplier()).thenReturn(supplier);
        when(mockProduct2.getSupplier()).thenReturn(supplier);
        when(mockProduct3.getSupplier()).thenReturn(new Supplier("Third", ""));
        when(mockProduct.getProductCategory()).thenReturn(productCategory);
        when(mockProduct2.getProductCategory()).thenReturn(productCategory);
        when(mockProduct3.getProductCategory()).thenReturn(new ProductCategory("Third", "", ""));
    }

    @Order(1)
    @Test
    void testAdd_oneProduct_sizeIncreasesByOne() throws SQLException {
        productDataStore.add(mockProduct);

        int expected = 1;

        assertEquals(expected, productDataStore.getAll().size());
    }

    @Order(2)
    @Test
    void testAdd_twoProduct_sizeIncreasesByTwo() throws SQLException {
        productDataStore.add(mockProduct);
        productDataStore.add(mockProduct2);

        int expected = 2;

        assertEquals(expected, productDataStore.getAll().size());
    }

    @Order(3)
    @Test
    void testAdd_zeroProduct_sizeNotIncreasing() {
        int expected = 0;

        assertEquals(expected, productDataStore.getAll().size());
    }

    @Order(4)
    @Test
    void testFind_addOneProduct_foundThisProduct() throws SQLException {
        productDataStore.add(mockProduct);

        assertEquals(mockProduct, productDataStore.find(mockProduct.getId()));
    }

    @Order(5)
    @Test
    void testFind_addThreeProduct_foundOneOfThem() throws SQLException {
        productDataStore.add(mockProduct);
        productDataStore.add(mockProduct2);
        productDataStore.add(mockProduct3);

        assertEquals(mockProduct3, productDataStore.find(mockProduct3.getId()));
    }

    @Order(6)
    @Test
    void testRemove_addOneRemoveOne_sizeEqualsToZero() throws SQLException {
        productDataStore.add(mockProduct);
        productDataStore.remove(mockProduct.getId());

        int expected = 0;

        assertEquals(expected, productDataStore.getAll().size());
    }

    @Order(7)
    @Test
    void testRemove_addThreeRemoveOne_sizeEqualsToTwo() throws SQLException {
        productDataStore.add(mockProduct);
        productDataStore.add(mockProduct2);
        productDataStore.add(mockProduct3);
        productDataStore.remove(mockProduct.getId());

        int expected = 2;

        assertEquals(expected, productDataStore.getAll().size());
    }

    @Order(8)
    @Test
    void testGetAll_nothingAdded_sizeEqualsToZero() {
        int size = productDataStore.getAll().size();

        int expected = 0;

        assertEquals(expected, size);
    }

    @Order(9)
    @Test
    void testGetBySupplier_oneAdded_returnsThatSupplier() throws SQLException {
        productDataStore.add(mockProduct);

        int size = productDataStore.getBy(mockProduct.getSupplier()).size();
        int expected = 1;

        assertEquals(expected, size);
    }

    @Order(10)
    @Test
    void testGetBySupplier_threeAdded_returnsOneOfTheSupplier() throws SQLException {
        productDataStore.add(mockProduct);
        productDataStore.add(mockProduct2);
        productDataStore.add(mockProduct3);

        int size = productDataStore.getBy(mockProduct.getSupplier()).size();
        int expected = 2;

        assertEquals(expected, size);
    }

    @Order(11)
    @Test
    void testGetByCategory_oneAdded_returnsThatCategory() throws SQLException {
        productDataStore.add(mockProduct);

        int size = productDataStore.getBy(mockProduct.getProductCategory()).size();
        int expected = 1;

        assertEquals(expected, size);
    }

    @Order(11)
    @Test
    void testGetByCategory_threeAdded_returnsThatCategory() throws SQLException {
        productDataStore.add(mockProduct);
        productDataStore.add(mockProduct2);
        productDataStore.add(mockProduct3);

        int size = productDataStore.getBy(mockProduct.getProductCategory()).size();
        int expected = 2;

        assertEquals(expected, size);
    }

    @Order(12)
    @Test
    void testRemoveAll_addedThreeRemoveThree_sizeEqualsToZero() throws SQLException {
        productDataStore.add(mockProduct);
        productDataStore.add(mockProduct2);
        productDataStore.add(mockProduct3);
        //productDataStore.removeAll();

        int expected = 0;

        assertEquals(expected, productDataStore.getAll().size());
    }
}