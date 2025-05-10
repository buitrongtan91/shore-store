package sgu.beo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sgu.beo.model.Discount;

public class DiscountDAO extends BaseDAO<Discount> {
    private static final Logger logger = LogManager.getLogger(DiscountDAO.class);

    protected DiscountDAO() {
    }

    public static DiscountDAO getInstance() {
        return getInstance(DiscountDAO.class);
    }

    @Override
    public boolean insert(Discount discount) {
        String sql = "INSERT INTO discount (name, description, discount_value, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, discount.getName());
            smt.setString(2, discount.getDescription());
            smt.setLong(3, discount.getDiscount_value());
            smt.setTimestamp(4, Timestamp.valueOf(discount.getStart_date()));
            smt.setTimestamp(5, Timestamp.valueOf(discount.getEnd_date()));
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public boolean update(Discount discount) {
        String sql = "UPDATE discount SET name = ?, description = ?, discount_value = ?, start_date = ?, end_date = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, discount.getName());
            smt.setString(2, discount.getDescription());
            smt.setLong(3, discount.getDiscount_value());
            smt.setTimestamp(4, Timestamp.valueOf(discount.getStart_date()));
            smt.setTimestamp(5, Timestamp.valueOf(discount.getEnd_date()));
            smt.setInt(6, discount.getId());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<Discount> findAll() {
        String sql = "SELECT * FROM discount";
        List<Discount> discounts = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(sql)) {
            while (rs.next()) {
                discounts.add(mapRow(rs));
            }
            return discounts;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public Discount findById(int id) {
        String sql = "SELECT * FROM discount WHERE id = ?";
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

    // Phương thức tìm kiếm discount theo tên
    public List<Discount> findByName(String name) {
        String sql = "SELECT * FROM discount WHERE name LIKE ?";
        List<Discount> discounts = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, "%" + name + "%");
            try (ResultSet rs = smt.executeQuery()) {
                while (rs.next()) {
                    discounts.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(sql, e);
        }
        return discounts;
    }

    private Discount mapRow(ResultSet rs) throws SQLException {
        Discount discount = new Discount();
        discount.setId(rs.getInt("id"));
        discount.setName(rs.getString("name"));
        discount.setDescription(rs.getString("description"));
        discount.setDiscount_value(rs.getLong("discount_value"));
        discount.setStart_date(rs.getTimestamp("start_date").toLocalDateTime());
        discount.setEnd_date(rs.getTimestamp("end_date").toLocalDateTime());
        discount.set_active(rs.getBoolean("is_active"));
        discount.set_deleted(rs.getBoolean("is_deleted"));
        return discount;
    }
}
