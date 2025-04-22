package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sgu.beo.model.Category;

public class CategoryDAOTest {
    private CategoryDAO categoryDAO;

    @BeforeEach
    public void setUp() {
        // Tạo một instance của CategoryDAO
        categoryDAO = CategoryDAO.getInstance();
    }

    @Test
    public void testInsert() {
        Category category = new Category();
        category.setName("Category Test");
        category.setDescription("Test description");

        boolean isInserted = categoryDAO.insert(category);
        assertTrue(isInserted, "The category should be inserted successfully.");
    }

    @Test
    public void testUpdate() {
        Category category = new Category();
        category.setId(1); // Giả sử ID đã có trong DB
        category.setName("Updated Category");
        category.setDescription("Updated description");

        boolean isUpdated = categoryDAO.update(category);
        assertTrue(isUpdated, "The category should be updated successfully.");
    }

    @Test
    public void testFindAll() {
        List<Category> categories = categoryDAO.findAll();
        assertNotNull(categories, "The list of categories should not be null.");
        assertTrue(categories.size() > 0, "There should be at least one category in the list.");
    }

    @Test
    public void testFindById() {
        int id = 1; // Giả sử ID này có trong DB
        Category category = categoryDAO.findById(id);
        assertNotNull(category, "The category should be found by ID.");
        assertEquals(id, category.getId(), "The category ID should match.");
    }

    @Test
    public void testFindByName() {
        String name = "category";
        List<Category> categories = categoryDAO.findByName(name);
        categories.forEach(category -> {
            System.out.println(category);
            assertTrue(category.getName().toLowerCase().contains(name.toLowerCase()),
                    "The category name should contain the search term.");
        });
        assertNotNull(categories, "The list of categories should not be null.");
        assertTrue(categories.size() > 0, "There should be at least one category matching the name.");
    }
}
