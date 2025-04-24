package sgu.beo.DAO;

import org.junit.jupiter.api.*;
import sgu.beo.model.ImportInvoice;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImportInvoiceDAOTest {

    private static ImportInvoiceDAO invoiceDAO;

    @BeforeAll
    public static void setup() {
        invoiceDAO = ImportInvoiceDAO.getInstance();
    }

    @Test
    @Order(1)
    public void testInsert() {
        ImportInvoice invoice = new ImportInvoice();
        invoice.setSupplier_id(1); // Giả sử supplier id = 1 tồn tại
        invoice.setEmployee_id(1); // Giả sử employee id = 1 tồn tại
        invoice.setImport_date(LocalDateTime.now());
        invoice.setTotal_amount(500000);

        int result = invoiceDAO.insertAndGetId(invoice);
        System.out.println(result);
        assertTrue(result != -1, "Insert should return id");

    }

    @Test
    @Order(2)
    public void testFindById() {
        ImportInvoice invoice = invoiceDAO.findById(2);
        assertNotNull(invoice, "Invoice should not be null");
        assertEquals(2, invoice.getId());
    }

    @Test
    @Order(3)
    public void testUpdate() {
        ImportInvoice invoice = invoiceDAO.findById(3);
        invoice.setTotal_amount(999999);

        boolean result = invoiceDAO.update(invoice);
        assertTrue(result, "Update should return true");

        ImportInvoice updatedInvoice = invoiceDAO.findById(3);
        assertEquals(999999, updatedInvoice.getTotal_amount());
    }

    @Test
    @Order(4)
    public void testFindAll() {
        List<ImportInvoice> invoices = invoiceDAO.findAll();
        assertNotNull(invoices, "List should not be null");
        assertTrue(invoices.size() > 0, "There should be at least one invoice");
    }

    @Test
    @Order(5)
    public void testFindByName() {
        List<ImportInvoice> result = invoiceDAO.findByName("Anything");
        assertNotNull(result);
        assertEquals(0, result.size(), "findByName should return empty list");
    }
}
