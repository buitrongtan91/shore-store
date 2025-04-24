package sgu.beo.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sgu.beo.model.Supplier;

public class SupplierDAO extends BaseDAO<Supplier> {
    private static final Logger logger = LogManager.getLogger(SupplierDAO.class);

    protected SupplierDAO() {
    }

    public static SupplierDAO getInstance() {
        return getInstance(SupplierDAO.class);
    }

    @Override
    public boolean insert(Supplier supplier) {
        String sql = "INSERT INTO supplier (name, phone, email, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, supplier.getName());
            smt.setString(2, supplier.getPhone());
            smt.setString(3, supplier.getEmail());
            smt.setString(4, supplier.getAddress());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public boolean update(Supplier supplier) {
        String sql = "UPDATE supplier SET name = ?, phone = ?, email = ?, address = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, supplier.getName());
            smt.setString(2, supplier.getPhone());
            smt.setString(3, supplier.getEmail());
            smt.setString(4, supplier.getAddress());
            smt.setInt(5, supplier.getId());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<Supplier> findAll() {
        String sql = "SELECT * FROM supplier";
        List<Supplier> suppliers = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(sql)) {
            while (rs.next()) {
                suppliers.add(mapRow(rs));
            }
            return suppliers;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public Supplier findById(int id) {
        String sql = "SELECT * FROM supplier WHERE id = ?";
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
    public List<Supplier> findByName(String name) {
        String sql = "SELECT * FROM supplier WHERE LOWER(name) LIKE ?";
        List<Supplier> suppliers = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, "%" + name.toLowerCase() + "%");
            try (ResultSet rs = smt.executeQuery()) {
                while (rs.next()) {
                    suppliers.add(mapRow(rs));
                }
            }
            return suppliers;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    private Supplier mapRow(ResultSet rs) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setId(rs.getInt("id"));
        supplier.setName(rs.getString("name"));
        supplier.setPhone(rs.getString("phone"));
        supplier.setEmail(rs.getString("email"));
        supplier.setAddress(rs.getString("address"));
        supplier.set_deleted(rs.getBoolean("is_deleted"));
        return supplier;
    }
}
