package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import sgu.beo.model.SaleInvoice;

public class SaleInvoiceDAOTest {
    @Test
    public void testInsertAndGetId() {
        SaleInvoice invoice = new SaleInvoice();
        invoice.setCustomer_id(1);
        invoice.setEmployee_id(1);
        invoice.setSale_date(LocalDateTime.now());
        invoice.setTotal_amount(100);
        invoice.setPromotion_id(1);
        invoice.setPromotion_amount(10);
        invoice.setFinal_amount(50);

        int rs = SaleInvoiceDAO.getInstance().insertAndGetId(invoice);
        System.out.println(rs);
        assertTrue(rs > -1);
    }

    @Test
    public void testUpdate() {
        SaleInvoice invoice = new SaleInvoice();
        invoice.setCustomer_id(1);
        invoice.setTotal_amount(100);
        invoice.setPromotion_id(1);
        invoice.setPromotion_amount(10);
        invoice.setFinal_amount(50);
        invoice.setId(2);

        boolean rs = SaleInvoiceDAO.getInstance().update(invoice);
        System.out.println(rs);
        assertTrue(rs);
    }

    @Test
    public void testFindAll() {
        List<SaleInvoice> rs = SaleInvoiceDAO.getInstance().findAll();
        rs.forEach(p -> System.out.println(p));
        assertTrue(rs.size() > 0);
    }

    @Test
    public void testFindById() {
        SaleInvoice rs = SaleInvoiceDAO.getInstance().findById(2);
        System.out.println(rs);
        assertNotNull(rs);
    }
}
