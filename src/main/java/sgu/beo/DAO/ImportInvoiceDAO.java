package sgu.beo.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sgu.beo.model.ImportInvoice;

public class ImportInvoiceDAO extends BaseDAO<ImportInvoice> {
    private static final Logger logger = LogManager.getLogger(ImportInvoiceDAO.class);

    protected ImportInvoiceDAO() {
    }

    public static ImportInvoiceDAO getInstance() {
        return getInstance(ImportInvoiceDAO.class);
    }

    @Override
    public boolean insert(ImportInvoice invoice) {
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    public int insertAndGetId(ImportInvoice invoice) {
        String sql = "INSERT INTO import_invoice (supplier_id, employee_id, import_date, total_amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement smt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            smt.setInt(1, invoice.getSupplier_id());
            smt.setInt(2, invoice.getEmployee_id());
            smt.setTimestamp(3, Timestamp.valueOf(invoice.getImport_date()));
            smt.setLong(4, invoice.getTotal_amount());
            if (smt.executeUpdate() > 0) {
                try (ResultSet rs = smt.getGeneratedKeys()) {
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
    public boolean update(ImportInvoice invoice) {
        String sql = "UPDATE import_invoice SET supplier_id = ?, total_amount = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setInt(1, invoice.getSupplier_id());
            smt.setLong(2, invoice.getTotal_amount());
            smt.setInt(3, invoice.getId());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<ImportInvoice> findAll() {
        String sql = "SELECT * FROM import_invoice";
        List<ImportInvoice> invoices = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(sql)) {
            while (rs.next()) {
                invoices.add(mapRow(rs));
            }
            return invoices;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public ImportInvoice findById(int id) {
        String sql = "SELECT * FROM import_invoice WHERE id = ?";
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
    public List<ImportInvoice> findByName(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    private ImportInvoice mapRow(ResultSet rs) throws SQLException {
        ImportInvoice invoice = new ImportInvoice();

        invoice.setId(rs.getInt("id"));
        invoice.setSupplier_id(rs.getInt("supplier_id"));
        invoice.setEmployee_id(rs.getInt("employee_id"));
        invoice.setImport_date(rs.getTimestamp("import_date").toLocalDateTime());
        invoice.setTotal_amount(rs.getLong("total_amount"));
        invoice.set_deleted(rs.getBoolean("is_deleted"));

        return invoice;
    }
}
