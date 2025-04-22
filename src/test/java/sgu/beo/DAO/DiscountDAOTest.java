package sgu.beo.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sgu.beo.model.Discount;

import java.time.LocalDateTime;
import java.util.List;

public class DiscountDAOTest {

    private DiscountDAO discountDAO;

    @BeforeEach
    public void setUp() {
        // Tạo một instance của DiscountDAO
        discountDAO = DiscountDAO.getInstance();
    }

    @Test
    public void testInsert() {
        Discount discount = new Discount();
        discount.setName("Discount Test");
        discount.setDescription("Test Description");
        discount.setStart_date(LocalDateTime.now());
        discount.setEnd_date(LocalDateTime.now().plusDays(10));

        boolean isInserted = discountDAO.insert(discount);
        assertTrue(isInserted, "The discount should be inserted successfully.");
    }

    @Test
    public void testUpdate() {
        Discount discount = new Discount();
        discount.setId(1); // Giả sử ID đã có trong DB
        discount.setName("Updated Discount");
        discount.setDescription("Updated Description");
        discount.setStart_date(LocalDateTime.now());
        discount.setEnd_date(LocalDateTime.now().plusDays(5));

        boolean isUpdated = discountDAO.update(discount);
        assertTrue(isUpdated, "The discount should be updated successfully.");
    }

    @Test
    public void testFindAll() {
        List<Discount> discounts = discountDAO.findAll();
        assertNotNull(discounts, "The list of discounts should not be null.");
        assertTrue(discounts.size() > 0, "There should be at least one discount in the list.");
    }

    @Test
    public void testFindById() {
        int id = 1; // Giả sử ID này có trong DB
        Discount discount = discountDAO.findById(id);
        assertNotNull(discount, "The discount should be found by ID.");
        assertEquals(id, discount.getId(), "The discount ID should match.");
    }

    @Test
    public void testFindByName() {
        String name = "Discount";
        List<Discount> discounts = discountDAO.findByName(name);
        discounts.forEach(discount -> {
            System.out.println(discount);
            assertTrue(discount.getName().toLowerCase().contains(name.toLowerCase()),
                    "The discount name should contain the search term.");
        });
        assertNotNull(discounts, "The list of discounts should not be null.");
        assertTrue(discounts.size() > 0, "There should be at least one discount matching the name.");
    }
}
