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

import sgu.beo.model.Customer;

public class CustomerDAO extends BaseDAO<Customer> {
    protected static final Logger logger = LogManager.getLogger(CustomerDAO.class);

    protected CustomerDAO() {
    }

    public static CustomerDAO getInstance() {
        return getInstance(CustomerDAO.class);
    }

    @Override
    public boolean insert(Customer entity) {
        String sql = "INSERT INTO Customer (name, phone, email, address) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getPhone());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getAddress());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int insertAndGetId(Customer entity) {
        String sql = "INSERT INTO customer (name, phone, email, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, entity.getName());
            stm.setString(2, entity.getPhone());
            stm.setString(3, entity.getEmail());
            stm.setString(4, entity.getAddress());
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
    public boolean update(Customer entity) {
        String sql = "UPDATE customer SET name = ?, phone = ?, email = ?, address = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, entity.getName());
            stm.setString(2, entity.getPhone());
            stm.setString(3, entity.getEmail());
            stm.setString(4, entity.getAddress());
            stm.setInt(5, entity.getId());
            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Connection conn = getConnection(); Statement stm = conn.createStatement()) {
            try (ResultSet rs = stm.executeQuery(sql)) {
                while (rs.next()) {
                    customers.add(mapRow(rs));
                }
            }
            return customers;

        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
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
    public List<Customer> findByName(String name) {
        String sql = "SELECT * FROM customer WHERE LOWER(name) LIKE ?";
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, "%" + name.toLowerCase() + "%");
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapRow(rs));

                }
                return customers;
            }

        } catch (Exception e) {
            logger.error(sql, e);
            return null;
        }
    }

    public List<Customer> findByPhone(String phone) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE phone = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, phone);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapRow(rs));
                }
            }
            return customers;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer cus = new Customer();

        cus.setId(rs.getInt("id"));
        cus.setName(rs.getString("name"));
        cus.setPhone(rs.getString("phone"));
        cus.setEmail(rs.getString("email"));
        cus.setAddress(rs.getString("address"));
        cus.set_deleted(rs.getBoolean("is_deleted"));

        return cus;
    }
}
