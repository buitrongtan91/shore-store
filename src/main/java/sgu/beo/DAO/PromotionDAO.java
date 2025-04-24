package sgu.beo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
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
            stm.setDouble(4, entity.getPromotion_value());
            stm.setDouble(5, entity.getMin_invoice_value());
            stm.setDouble(6, entity.getMax_discount_value());
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Promotion> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Promotion findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
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
        pr.setPromotion_value(rs.getDouble("promotion_value"));
        pr.setStart_date(rs.getTimestamp("start_date").toLocalDateTime());
        pr.setEnd_date(rs.getTimestamp("end_date").toLocalDateTime());
        pr.setMin_invoice_value(rs.getDouble("min_invoice_value"));
        pr.setMax_discount_value(rs.getDouble("max_discount_value"));
        pr.set_active(rs.getBoolean("is_active"));
        pr.set_deleted(rs.getBoolean("is_deleted"));

        return pr;

    }
}
