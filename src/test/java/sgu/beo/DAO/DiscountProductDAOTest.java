package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import sgu.beo.model.Product;

public class DiscountProductDAOTest {
    @Test
    public void testAddProductToDiscount() {
        boolean rs = DiscountProductDAO.getInstance().addProductToDiscount(1, 1);
        assertTrue(rs);
    }

    @Test
    public void testRemoveProductFromDiscount() {
        boolean rs = DiscountProductDAO.getInstance().removeProductFromDiscount(1, 1);
        assertTrue(rs);
    }

    @Test
    public void testFindProductByDiscountId() {
        List<Product> products = DiscountProductDAO.getInstance().findProductsByDiscountId(1);
        products.forEach(p -> System.out.println(p));
        assertTrue(products.size() > 0);
    }
}
