package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sgu.beo.model.ProductVariant;

public class ProductVariantDAOTest {

    @Test
    public void testInsert() {
        for (int i = 0; i < 30; i++) {
            ProductVariant p = new ProductVariant();

            p.setProduct_id(2);
            p.setColor("red");
            p.setImg_url("test.png");
            p.setCost(100);
            p.setPrice(150);
            p.setStatus("test");

            boolean rs = ProductVariantDAO.getInstance().insert(p);

            assertTrue(rs, "phải trả về true");
        }

    }

}
