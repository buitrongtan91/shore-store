package sgu.beo.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import sgu.beo.model.Supplier;

import java.util.List;

public class SupplierDAOTest {

    private SupplierDAO supplierDAO;

    @BeforeEach
    public void setUp() {
        supplierDAO = SupplierDAO.getInstance();
    }

    @Test
    public void testInsert() {
        Supplier supplier = new Supplier();
        supplier.setName("Supplier Test");
        supplier.setPhone("0123456789");
        supplier.setEmail("supplier@test.com");
        supplier.setAddress("123 Test Street");

        boolean isInserted = supplierDAO.insert(supplier);
        assertTrue(isInserted, "The supplier should be inserted successfully.");
    }

    @Test
    public void testUpdate() {
        Supplier supplier = new Supplier();
        supplier.setId(1); // Giả sử ID đã tồn tại trong DB
        supplier.setName("Updated Supplier");
        supplier.setPhone("0987654321");
        supplier.setEmail("updated@supplier.com");
        supplier.setAddress("456 Update Ave");

        boolean isUpdated = supplierDAO.update(supplier);
        assertTrue(isUpdated, "The supplier should be updated successfully.");
    }

    @Test
    public void testFindAll() {
        List<Supplier> suppliers = supplierDAO.findAll();
        assertNotNull(suppliers, "The list of suppliers should not be null.");
        assertTrue(suppliers.size() > 0, "There should be at least one supplier in the list.");
    }

    @Test
    public void testFindById() {
        int id = 1; // Giả sử ID này tồn tại
        Supplier supplier = supplierDAO.findById(id);
        assertNotNull(supplier, "The supplier should be found by ID.");
        assertEquals(id, supplier.getId(), "The supplier ID should match.");
    }

    @Test
    public void testFindByName() {
        String name = "supplier";
        List<Supplier> suppliers = supplierDAO.findByName(name);
        suppliers.forEach(supplier -> {
            System.out.println(supplier);
            assertTrue(supplier.getName().toLowerCase().contains(name.toLowerCase()),
                    "The supplier name should contain the search term.");
        });
        assertNotNull(suppliers, "The list of suppliers should not be null.");
        assertTrue(suppliers.size() > 0, "There should be at least one supplier matching the name.");
    }
}
