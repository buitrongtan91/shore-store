package sgu.beo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import sgu.beo.model.Product;

public class ProductServiceTest {
    @Test
    public void testFindAll() {
        List<Product> products = ProductService.getAll();
        products.forEach(p -> System.out.println(p));
        assertTrue(products.size() > 0);
    }
}
