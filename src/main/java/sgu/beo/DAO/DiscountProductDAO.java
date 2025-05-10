package sgu.beo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sgu.beo.model.Brand;
import sgu.beo.model.Category;
import sgu.beo.model.Gender;
import sgu.beo.model.Product;

public class DiscountProductDAO extends BaseDAO<Void> {
    private static final Logger logger = LogManager.getLogger(DiscountProductDAO.class);

    protected DiscountProductDAO() {
    }

    public static DiscountProductDAO getInstance() {
        return getInstance(DiscountProductDAO.class);
    }

    // Thêm 1 product vào 1 discount
    public boolean addProductToDiscount(int discountId, int productId) {
        String sql = "INSERT INTO discount_product (discount_id, product_id) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setInt(1, discountId);
            smt.setInt(2, productId);
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    // Xoá product ra khỏi discount
    public boolean removeProductFromDiscount(int discountId, int productId) {
        String sql = "DELETE FROM discount_product WHERE discount_id = ? AND product_id = ?";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setInt(1, discountId);
            smt.setInt(2, productId);
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    // Lấy tất cả sản phẩm trong 1 discount
    public List<Product> findProductsByDiscountId(int discountId) {
        String sql = " SELECT p.* FROM product p\r\n" + //
                "            JOIN discount_product dp ON p.id = dp.product_id\r\n" + //
                "            WHERE dp.discount_id = ?";
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setInt(1, discountId);
            ResultSet rs = smt.executeQuery();
            while (rs.next()) {
                products.add(mapRow(rs));
            }
            return products;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public boolean insert(Void entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public boolean update(Void entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Void> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Void findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Void> findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));

        Brand br = new Brand();
        br.setId(rs.getInt("brand_id"));
        product.setBrand(br);

        Category cate = new Category();
        cate.setId(rs.getInt("category_id"));
        product.setCategory(cate);

        product.setGender(Gender.valueOf(rs.getString("gender")));
        product.set_deleted(rs.getBoolean("is_deleted"));
        return product;
    }
}