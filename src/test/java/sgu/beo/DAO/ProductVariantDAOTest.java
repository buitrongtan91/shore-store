package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sgu.beo.model.ProductVariant;

public class ProductVariantDAOTest {

    @Test
    public void testInsert() {
        ProductVariant p = new ProductVariant();

        p.setProduct_id(1);
        p.setColor("red");
        p.setSize(29.5);
        p.setImg_url("test.img");
        p.setCost(100);
        p.setPrice(150);
        p.setQuantity(100);
        p.setStatus("test");

        boolean rs = ProductVariantDAO.getInstance().insert(p);

        assertTrue(rs, "phải trả về true");

    }

}
