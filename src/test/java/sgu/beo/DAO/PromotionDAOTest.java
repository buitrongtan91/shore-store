package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import sgu.beo.model.Promotion;

public class PromotionDAOTest {
    @Test
    public void testInsert() {
        Promotion pr = new Promotion();

        pr.setName("test");
        pr.setDescription("test");
        pr.setPromotion_type("flat");
        pr.setPromotion_value(100000);
        pr.setMin_invoice_value(200000);
        pr.setMax_discount_value(100000);
        pr.setStart_date(LocalDateTime.now());
        pr.setEnd_date(LocalDateTime.now().plusDays(5));

        boolean rs = PromotionDAO.getInstance().insert(pr);

        assertTrue(rs);
    }

    @Test
    public void testUpdate() {
        Promotion pr = new Promotion();

        pr.setName("test update");
        pr.setDescription("test update");
        pr.setPromotion_type("%");
        pr.setPromotion_value(50);
        pr.setMin_invoice_value(100);
        pr.setMax_discount_value(10);
        pr.setStart_date(LocalDateTime.now());
        pr.setEnd_date(LocalDateTime.now().plusDays(5));
        pr.setId(1);

        boolean rs = PromotionDAO.getInstance().update(pr);

        assertTrue(rs);
    }

    @Test
    public void testFindAll() {
        List<Promotion> promotions = PromotionDAO.getInstance().findAll();
        promotions.forEach(p -> System.out.println(p));
        assertTrue(promotions.size() > 0);
    }

    @Test
    public void testFindById() {
        Promotion pr = PromotionDAO.getInstance().findById(1);

        System.out.println(pr);

        assertNotNull(pr);
    }
}
