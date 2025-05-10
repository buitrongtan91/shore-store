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

import sgu.beo.model.Promotion;

public class PromotionDAO extends BaseDAO<Promotion> {
    protected static final Logger logger = LogManager.getLogger(PromotionDAO.class);

    protected PromotionDAO() {
    }

    public static PromotionDAO getInstance() {
        return getInstance(PromotionDAO.class);
    }

    @Override
    public boolean insert(Promotion entity) {
        String sql = "INSERT INTO promotion (name, description, promotion_type, promotion_value, min_invoice_value, max_discount_value, start_date, end_date ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, entity.getName());
            stm.setString(2, entity.getDescription());
            stm.setString(3, entity.getPromotion_type());
            stm.setLong(4, entity.getPromotion_value());
            stm.setLong(5, entity.getMin_invoice_value());
            stm.setLong(6, entity.getMax_discount_value());
            stm.setTimestamp(7, Timestamp.valueOf(entity.getStart_date()));
            stm.setTimestamp(8, Timestamp.valueOf(entity.getEnd_date()));

            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public boolean update(Promotion entity) {
        String sql = "UPDATE promotion SET name = ?, description = ?, promotion_type = ?, promotion_value = ?, min_invoice_value = ?, max_discount_value = ?, start_date = ?, end_date = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, entity.getName());
            stm.setString(2, entity.getDescription());
            stm.setString(3, entity.getPromotion_type());
            stm.setLong(4, entity.getPromotion_value());
            stm.setLong(5, entity.getMin_invoice_value());
            stm.setLong(6, entity.getMax_discount_value());
            stm.setTimestamp(7, Timestamp.valueOf(entity.getStart_date()));
            stm.setTimestamp(8, Timestamp.valueOf(entity.getEnd_date()));
            stm.setInt(9, entity.getId());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<Promotion> findAll() {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM promotion";
        try (Connection conn = getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                promotions.add(mapRow(rs));
            }
            return promotions;
        } catch (Exception e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public Promotion findById(int id) {
        String sql = "SELECT * FROM promotion WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }

        } catch (Exception e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public List<Promotion> findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    public Promotion mapRow(ResultSet rs) throws SQLException {
        Promotion pr = new Promotion();

        pr.setId(rs.getInt("id"));
        pr.setName(rs.getString("name"));
        pr.setDescription(rs.getString("description"));
        pr.setPromotion_type(rs.getString("promotion_type"));
        pr.setPromotion_value(rs.getLong("promotion_value"));
        pr.setStart_date(rs.getTimestamp("start_date").toLocalDateTime());
        pr.setEnd_date(rs.getTimestamp("end_date").toLocalDateTime());
        pr.setMin_invoice_value(rs.getLong("min_invoice_value"));
        pr.setMax_discount_value(rs.getLong("max_discount_value"));
        pr.set_active(rs.getBoolean("is_active"));
        pr.set_deleted(rs.getBoolean("is_deleted"));

        return pr;

    }
}
