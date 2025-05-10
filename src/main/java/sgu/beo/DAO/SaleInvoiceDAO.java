package sgu.beo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sgu.beo.model.SaleInvoice;

public class SaleInvoiceDAO extends BaseDAO<SaleInvoice> {
    protected static final Logger logger = LogManager.getLogger(SaleInvoiceDAO.class);

    protected SaleInvoiceDAO() {

    }

    public static SaleInvoiceDAO getInstance() {
        return getInstance(SaleInvoiceDAO.class);
    }

    @Override
    public boolean insert(SaleInvoice entity) {
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    public int insertAndGetId(SaleInvoice entity) {
        String sql = "INSERT INTO sale_invoice (customer_id, employee_id, sale_date, total_amount, promotion_id, promotion_amount, final_amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stm.setInt(1, entity.getCustomer_id());
            stm.setInt(2, entity.getEmployee_id());
            stm.setTimestamp(3, Timestamp.valueOf(entity.getSale_date()));
            stm.setLong(4, entity.getTotal_amount());
            if (entity.getPromotion_id() != null) {
                stm.setInt(5, entity.getPromotion_id());
            } else {
                stm.setNull(5, Types.INTEGER);
            }
            stm.setLong(6, entity.getPromotion_amount());
            stm.setLong(7, entity.getFinal_amount());

            if (stm.executeUpdate() > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);

                    }

                }
            }
            return -1;

        } catch (SQLException e) {
            logger.error(sql, e);
            return -1;
        }
    }

    @Override
    public boolean update(SaleInvoice entity) {
        String sql = "UPDATE sale_invoice SET customer_id = ?, promotion_id = ?, promotion_amount = ?, total_amount = ?, final_amount = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, entity.getCustomer_id());
            stm.setInt(2, entity.getPromotion_id());
            stm.setLong(3, entity.getPromotion_amount());
            stm.setLong(4, entity.getTotal_amount());
            stm.setLong(5, entity.getFinal_amount());
            stm.setInt(6, entity.getId());

            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<SaleInvoice> findAll() {
        String sql = "SELECT * FROM sale_invoice";
        List<SaleInvoice> invoices = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                invoices.add(mapRow(rs));
            }
            return invoices;

        } catch (Exception e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public SaleInvoice findById(int id) {
        String sql = "SELECT * FROM sale_invoice WHERE id = ?";
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
    public List<SaleInvoice> findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    private SaleInvoice mapRow(ResultSet rs) throws SQLException {
        SaleInvoice invoice = new SaleInvoice();
        invoice.setId(rs.getInt("id"));
        invoice.setCustomer_id(rs.getInt("customer_id"));
        invoice.setEmployee_id(rs.getInt("employee_id"));
        invoice.setPromotion_id(rs.getInt("promotion_id"));
        invoice.setPromotion_amount(rs.getLong("promotion_amount"));
        invoice.setTotal_amount(rs.getLong("total_amount"));
        invoice.setFinal_amount(rs.getLong("final_amount"));
        invoice.setSale_date(rs.getTimestamp("sale_date").toLocalDateTime());
        invoice.set_deleted(rs.getBoolean("is_deleted"));

        return invoice;
    }
}
