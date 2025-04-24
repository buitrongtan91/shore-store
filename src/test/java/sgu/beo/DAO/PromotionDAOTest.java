package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import sgu.beo.model.Promotion;

public class PromotionDAOTest {
    @Test
    public void testInsert() {
        Promotion pr = new Promotion();

        pr.setName("test");
        pr.setDescription("test");
        pr.setPromotion_type("%");
        pr.setPromotion_value(50);
        pr.setMin_invoice_value(100);
        pr.setMax_discount_value(10);
        pr.setStart_date(LocalDateTime.now());
        pr.setEnd_date(LocalDateTime.now().plusDays(5));

        boolean rs = PromotionDAO.getInstance().insert(pr);

        assertTrue(rs);
    }
}
