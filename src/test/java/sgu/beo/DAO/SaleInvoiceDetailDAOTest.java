package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import sgu.beo.model.SaleInvoiceDetail;

public class SaleInvoiceDetailDAOTest {
    @Test
    public void testInsert() {
        SaleInvoiceDetail invoice = new SaleInvoiceDetail();
        invoice.setSale_invoice_id(2);
        invoice.setProduct_variant_id(1);
        invoice.setQuantity(10);
        invoice.setUnit_price(100);
        invoice.setDiscount_id(1);
        invoice.setDiscount_amount(10);
        invoice.setTotal_price(200);

        boolean rs = SaleInvoiceDetailDAO.getInstance().insert(invoice);

        assertTrue(rs);
    }

    @Test
    public void testUpdate() {
        SaleInvoiceDetail invoice = new SaleInvoiceDetail();
        invoice.setProduct_variant_id(1);
        invoice.setQuantity(10);
        invoice.setUnit_price(100);
        invoice.setDiscount_id(1);
        invoice.setDiscount_amount(10);
        invoice.setTotal_price(200);
        invoice.setId(1);

        boolean rs = SaleInvoiceDetailDAO.getInstance().update(invoice);

        assertTrue(rs);
    }

    @Test
    public void testFindAllByInvoiceId() {
        List<SaleInvoiceDetail> rs = SaleInvoiceDetailDAO.getInstance().findAllByInvoiceId(2);
        rs.forEach(p -> System.out.println(p));
        assertTrue(rs.size() > 0);
    }

    @Test
    public void testFindById() {
        SaleInvoiceDetail invoice = SaleInvoiceDetailDAO.getInstance().findById(1);
        System.out.println(invoice);
        assertNotNull(invoice);
    }
}
