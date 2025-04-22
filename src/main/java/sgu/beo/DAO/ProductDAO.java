package sgu.beo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sgu.beo.model.Gender;
import sgu.beo.model.Product;

public class ProductDAO extends BaseDAO<Product> {
    private static final Logger logger = LogManager.getLogger(ProductDAO.class);

    protected ProductDAO() {
    }

    public static ProductDAO getInstance() {
        return getInstance(ProductDAO.class);
    }

    @Override
    public boolean insert(Product product) {
        String sql = "INSERT INTO product (name, brand_id, category_id, gender) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, product.getName());
            smt.setInt(2, product.getBrand_id());
            smt.setInt(3, product.getCategory_id());
            smt.setString(4, product.getGender().toString());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }

    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE product SET name = ?, brand_id = ?, category_id = ?, gender = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, product.getName());
            smt.setInt(2, product.getBrand_id());
            smt.setInt(3, product.getCategory_id());
            smt.setString(4, product.getGender().toString());
            smt.setInt(5, product.getId());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(sql)) {
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
    public Product findById(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setInt(1, id);
            try (ResultSet rs = smt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(sql, e);
        }
        return null;
    }

    @Override
    public List<Product> findByName(String name) {
        String sql = "SELECT * FROM product WHERE LOWER(name) LIKE ?";
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, "%" + name.toLowerCase() + "%");
            try (ResultSet rs = smt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }
            return products;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setBrand_id(rs.getInt("brand_id"));
        product.setCategory_id(rs.getInt("category_id"));
        product.setGender(Gender.valueOf(rs.getString("gender")));
        product.set_deleted(rs.getBoolean("is_deleted"));
        return product;
    }
}
