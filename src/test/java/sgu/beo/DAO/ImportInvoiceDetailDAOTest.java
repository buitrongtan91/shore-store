package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import sgu.beo.model.ImportInvoiceDetail;

public class ImportInvoiceDetailDAOTest {

    @Test
    public void testInsert() {
        ImportInvoiceDetail invoiceDetail = new ImportInvoiceDetail();
        invoiceDetail.setImport_invoice_id(2);
        invoiceDetail.setProduct_variant_id(1);
        invoiceDetail.setQuantity(100);
        invoiceDetail.setUnit_price(50);
        invoiceDetail.setTotal_price(1000);

        boolean rs = ImportInvoiceDetailDAO.getInstance().insert(invoiceDetail);

        assertTrue(rs, "");
    }

    @Test
    public void testUpdate() {
        ImportInvoiceDetail invoiceDetail = new ImportInvoiceDetail();

        invoiceDetail.setId(1);
        invoiceDetail.setProduct_variant_id(1);
        invoiceDetail.setUnit_price(1);
        invoiceDetail.setQuantity(1);
        invoiceDetail.setTotal_price(1);

        boolean rs = ImportInvoiceDetailDAO.getInstance().update(invoiceDetail);

        System.out.println(rs);

        assertTrue(rs, "");
    }

    @Test
    public void testFindAllByInvoiceId() {
        List<ImportInvoiceDetail> rs = ImportInvoiceDetailDAO.getInstance().findAllByInvoiceId(2);

        rs.forEach(p -> {
            System.out.println(p);
        });
        assertNotNull(rs);
        assertTrue(rs.size() > 0);
    }

    @Test
    public void testFindById() {
        ImportInvoiceDetail invoiceDetail = new ImportInvoiceDetail();

        invoiceDetail = ImportInvoiceDetailDAO.getInstance().findById(1);

        System.out.println(invoiceDetail);
        assertNotNull(invoiceDetail);
    }
}
