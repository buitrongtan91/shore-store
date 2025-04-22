package sgu.beo.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sgu.beo.model.Gender;
import sgu.beo.model.Product;

import java.util.List;

public class ProductDAOTest {

    private ProductDAO productDAO;

    @BeforeEach
    public void setUp() {
        // Tạo một instance của ProductDAO
        productDAO = ProductDAO.getInstance();
    }

    @Test
    public void testInsert() {
        Product product = new Product();
        product.setName("Product Test");
        product.setBrand_id(1);
        product.setCategory_id(1);
        product.setGender(Gender.MALE);

        boolean isInserted = productDAO.insert(product);
        assertTrue(isInserted, "The product should be inserted successfully.");
    }

    @Test
    public void testUpdate() {
        Product product = new Product();
        product.setId(8); // Giả sử ID đã có trong DB
        product.setName("Updated Product");
        product.setBrand_id(1);
        product.setCategory_id(1);
        product.setGender(Gender.FEMALE);

        boolean isUpdated = productDAO.update(product);
        assertTrue(isUpdated, "The product should be updated successfully.");
    }

    @Test
    public void testFindAll() {
        List<Product> products = productDAO.findAll();
        assertNotNull(products, "The list of products should not be null.");
        assertTrue(products.size() > 0, "There should be at least one product in the list.");
    }

    @Test
    public void testFindById() {
        int id = 8; // Giả sử ID này có trong DB
        Product product = productDAO.findById(id);
        assertNotNull(product, "The product should be found by ID.");
        assertEquals(id, product.getId(), "The product ID should match.");
    }

    @Test
    public void testFindByName() {
        String name = "product";
        List<Product> products = productDAO.findByName(name);
        products.forEach(product -> {
            System.out.println(product);
            assertTrue(product.getName().toLowerCase().contains(name.toLowerCase()),
                    "The product name should contain the search term.");
        });
        assertNotNull(products, "The list of products should not be null.");
        assertTrue(products.size() > 0, "There should be at least one product matching the name.");
    }
}
