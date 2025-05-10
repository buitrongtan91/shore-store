package sgu.beo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sgu.beo.model.ImportInvoiceDetail;

public class ImportInvoiceDetailDAO extends BaseDAO<ImportInvoiceDetail> {

    private static final Logger logger = LogManager.getLogger(ImportInvoiceDetailDAO.class);

    protected ImportInvoiceDetailDAO() {
    }

    public static ImportInvoiceDetailDAO getInstance() {
        return getInstance(ImportInvoiceDetailDAO.class);
    }

    @Override
    public boolean insert(ImportInvoiceDetail entity) {
        String sql = "INSERT INTO import_invoice_detail (import_invoice_id, product_variant_id, quantity, unit_price, total_price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, entity.getImport_invoice_id());
            stm.setInt(2, entity.getProduct_variant_id());
            stm.setInt(3, entity.getQuantity());
            stm.setLong(4, entity.getUnit_price());
            stm.setLong(5, entity.getTotal_price());
            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            logger.error(sql, e);
            return false;
        }

    }

    @Override
    public boolean update(ImportInvoiceDetail entity) {
        String sql = "UPDATE import_invoice_detail SET product_variant_id = ?, quantity = ?, total_price = ?, unit_price = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, entity.getProduct_variant_id());
            stm.setInt(2, entity.getQuantity());
            stm.setLong(3, entity.getTotal_price());
            stm.setLong(4, entity.getUnit_price());
            stm.setInt(5, entity.getId());

            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<ImportInvoiceDetail> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public List<ImportInvoiceDetail> findAllByInvoiceId(int id) {
        String sql = "SELECT * FROM import_invoice_detail WHERE import_invoice_id = ?";
        List<ImportInvoiceDetail> importInvoiceDetails = new ArrayList<>();

        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    importInvoiceDetails.add(mapRow(rs));
                }
            }
            return importInvoiceDetails;
        } catch (Exception e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public ImportInvoiceDetail findById(int id) {
        String sql = "SELECT * FROM import_invoice_detail WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            return null;
        } catch (Exception e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public List<ImportInvoiceDetail> findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    private ImportInvoiceDetail mapRow(ResultSet rs) throws SQLException {
        ImportInvoiceDetail invoiceDetail = new ImportInvoiceDetail();
        invoiceDetail.setId(rs.getInt("id"));
        invoiceDetail.setImport_invoice_id(rs.getInt("import_invoice_id"));
        invoiceDetail.setProduct_variant_id(rs.getInt("product_variant_id"));
        invoiceDetail.setUnit_price(rs.getLong("unit_price"));
        invoiceDetail.setQuantity(rs.getInt("quantity"));
        invoiceDetail.setTotal_price(rs.getLong("total_price"));
        invoiceDetail.set_deleted(rs.getBoolean("is_deleted"));
        return invoiceDetail;
    }
}
