package sgu.beo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sgu.beo.model.SaleInvoiceDetail;

public class SaleInvoiceDetailDAO extends BaseDAO<SaleInvoiceDetail> {
    protected static final Logger logger = LogManager.getLogger(SaleInvoiceDetailDAO.class);

    protected SaleInvoiceDetailDAO() {

    }

    public static SaleInvoiceDetailDAO getInstance() {
        return getInstance(SaleInvoiceDetailDAO.class);
    }

    @Override
    public boolean insert(SaleInvoiceDetail entity) {
        String sql = "INSERT INTO sale_invoice_detail (sale_invoice_id, product_variant_id, quantity, unit_price, discount_id, discount_amount, total_price, size) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, entity.getSale_invoice_id());
            stm.setInt(2, entity.getProduct_variant_id());
            stm.setInt(3, entity.getQuantity());
            stm.setLong(4, entity.getUnit_price());
            stm.setInt(5, entity.getDiscount_id());
            stm.setLong(6, entity.getDiscount_amount());
            stm.setLong(7, entity.getTotal_price());
            stm.setString(8, entity.getSize());
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public boolean update(SaleInvoiceDetail entity) {
        String sql = "UPDATE sale_invoice_detail SET product_variant_id = ?, quantity = ?, unit_price = ?, discount_id = ?, discount_amount = ?, total_price = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, entity.getProduct_variant_id());
            stm.setInt(2, entity.getQuantity());
            stm.setLong(3, entity.getUnit_price());
            stm.setInt(4, entity.getDiscount_id());
            stm.setLong(5, entity.getDiscount_amount());
            stm.setLong(6, entity.getTotal_price());
            stm.setInt(7, entity.getId());

            return stm.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<SaleInvoiceDetail> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public List<SaleInvoiceDetail> findAllByInvoiceId(int id) {
        String sql = "SELECT * FROM sale_invoice_detail WHERE sale_invoice_id = ?";
        List<SaleInvoiceDetail> saleInvoices = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    saleInvoices.add(mapRow(rs));
                }
            }
            return saleInvoices;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public SaleInvoiceDetail findById(int id) {
        String sql = "SELECT * FROM sale_invoice_detail WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public List<SaleInvoiceDetail> findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    private SaleInvoiceDetail mapRow(ResultSet rs) throws SQLException {
        SaleInvoiceDetail invoice = new SaleInvoiceDetail();

        invoice.setId(rs.getInt("id"));
        invoice.setSale_invoice_id(rs.getInt("sale_invoice_id"));
        invoice.setProduct_variant_id(rs.getInt("product_variant_id"));
        invoice.setQuantity(rs.getInt("quantity"));
        invoice.setUnit_price(rs.getLong("unit_price"));
        invoice.setDiscount_id(rs.getInt("discount_id"));
        invoice.setDiscount_amount(rs.getLong("discount_amount"));
        invoice.setTotal_price(rs.getLong("total_price"));
        invoice.set_deleted(rs.getBoolean("is_deleted"));

        return invoice;
    }
}
