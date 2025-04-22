package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sgu.beo.model.Brand;

public class BrandDAOTest {
    private BrandDAO brandDAO;

    @BeforeEach
    public void setUp() {
        // Tạo một instance của BrandDAO
        brandDAO = BrandDAO.getInstance();
    }

    @Test
    public void testInsert() {
        Brand brand = new Brand();
        brand.setName("Brand Test");
        brand.setLogoUrl("http://logo.com");
        brand.setDescription("Test description");

        boolean isInserted = brandDAO.insert(brand);
        assertTrue(isInserted, "The brand should be inserted successfully.");
    }

    @Test
    public void testUpdate() {
        Brand brand = new Brand();
        brand.setId(1); // Giả sử ID đã có trong DB
        brand.setName("Updated Brand");
        brand.setLogoUrl("http://updated-logo.com");
        brand.setDescription("Updated description");

        boolean isUpdated = brandDAO.update(brand);
        assertTrue(isUpdated, "The brand should be updated successfully.");
    }

    @Test
    public void testFindAll() {
        List<Brand> brands = brandDAO.findAll();
        assertNotNull(brands, "The list of brands should not be null.");
        assertTrue(brands.size() > 0, "There should be at least one brand in the list.");
    }

    @Test
    public void testFindById() {
        int id = 1; // Giả sử ID này có trong DB
        Brand brand = brandDAO.findById(id);
        assertNotNull(brand, "The brand should be found by ID.");
        assertEquals(id, brand.getId(), "The brand ID should match.");
    }

    @Test
    public void testFindByName() {
        String name = "brand";
        List<Brand> brands = brandDAO.findByName(name);
        brands.forEach(brand -> {
            System.out.println(brand);
            assertTrue(brand.getName().toLowerCase().contains(name.toLowerCase()),
                    "The brand name should contain the search term.");
        });
        assertNotNull(brands, "The list of brands should not be null.");
        assertTrue(brands.size() > 0, "There should be at least one brand matching the name.");
    }
}
