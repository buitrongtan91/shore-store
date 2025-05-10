package sgu.beo.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sgu.beo.model.ProductVariant;

public class ProductVariantDAO extends BaseDAO<ProductVariant> {
    private static final Logger logger = LogManager.getLogger(ProductVariantDAO.class);

    protected ProductVariantDAO() {
    }

    public static ProductVariantDAO getInstance() {
        return getInstance(ProductVariantDAO.class);
    }

    @Override
    public boolean insert(ProductVariant variant) {
        String sql = "INSERT INTO product_variant (product_id, color, cost, img_url, status, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setInt(1, variant.getProduct_id());
            smt.setString(2, variant.getColor());
            smt.setLong(3, variant.getCost());
            smt.setString(4, variant.getImg_url());
            smt.setString(5, variant.getStatus());
            smt.setLong(6, variant.getPrice());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    public int insertAndGetId(ProductVariant variant) {
        String sql = "INSERT INTO product_variant (product_id, color, cost, img_url, status, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement smt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            smt.setInt(1, variant.getProduct_id());
            smt.setString(2, variant.getColor());
            smt.setLong(3, variant.getCost());
            smt.setString(4, variant.getImg_url());
            smt.setString(5, variant.getStatus());
            smt.setLong(6, variant.getPrice());

            int affectedRows = smt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Chèn variant thất bại, không có dòng nào bị ảnh hưởng.");
            }

            try (ResultSet generatedKeys = smt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Lấy ID vừa sinh ra
                } else {
                    throw new SQLException("Chèn variant thất bại, không lấy được ID.");
                }
            }
        } catch (SQLException e) {
            logger.error(sql, e);
            return -1; // hoặc ném ngoại lệ nếu bạn muốn xử lý ở lớp gọi
        }
    }

    @Override
    public boolean update(ProductVariant variant) {
        String sql = "UPDATE product_variant SET color = ?, cost = ?, img_url = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, variant.getColor());
            smt.setLong(2, variant.getCost());
            smt.setString(3, variant.getImg_url());
            smt.setInt(4, variant.getId());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<ProductVariant> findAll() {
        String sql = "SELECT * FROM product_variant";
        List<ProductVariant> variants = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(sql)) {
            while (rs.next()) {
                variants.add(mapRow(rs));
            }
            return variants;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public ProductVariant findById(int id) {
        String sql = "SELECT * FROM product_variant WHERE id = ?";
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

    public List<ProductVariant> findAllByProductId(int id) {
        String sql = "SELECT * FROM product_variant WHERE product_id = ?";
        List<ProductVariant> variants = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    variants.add(mapRow(rs));
                }
                return variants;
            }
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public List<ProductVariant> findByName(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    private ProductVariant mapRow(ResultSet rs) throws SQLException {
        ProductVariant variant = new ProductVariant();
        variant.setId(rs.getInt("id"));
        variant.setProduct_id(rs.getInt("product_id"));
        variant.setColor(rs.getString("color"));
        variant.setCost(rs.getLong("cost"));
        variant.setImg_url(rs.getString("img_url"));
        variant.setStatus(rs.getString("status"));
        variant.set_deleted(rs.getBoolean("is_deleted"));
        variant.setPrice(rs.getLong("price"));
        return variant;
    }
}
