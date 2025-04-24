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

import sgu.beo.model.Employee;
import sgu.beo.model.User;

public class EmployeeDAO extends BaseDAO<Employee> {
    private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);

    protected EmployeeDAO() {
    }

    public static EmployeeDAO getInstance() {
        return getInstance(EmployeeDAO.class);
    }

    @Override
    public boolean insert(Employee employee) {
        String sql = "INSERT INTO employee (name, phone, address, email, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, employee.getName());
            smt.setString(2, employee.getPhone());
            smt.setString(3, employee.getAddress());
            smt.setString(4, employee.getEmail());
            smt.setString(5, employee.getRole());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public boolean update(Employee employee) {
        String sql = "UPDATE employee SET name = ?, phone = ?, address = ?, email = ?, role = ?, user_id = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, employee.getName());
            smt.setString(2, employee.getPhone());
            smt.setString(3, employee.getAddress());
            smt.setString(4, employee.getEmail());
            smt.setString(5, employee.getRole());
            smt.setInt(6, employee.getUser().getId());
            smt.setInt(7, employee.getId());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employee";
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(sql)) {
            while (rs.next()) {
                employees.add(mapRow(rs));
            }
            return employees;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public Employee findById(int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
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
    public List<Employee> findByName(String name) {
        String sql = "SELECT * FROM employee WHERE LOWER(name) LIKE ?";
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, "%" + name.toLowerCase() + "%");
            try (ResultSet rs = smt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapRow(rs));
                }
            }
            return employees;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        User user = new User();
        user.setId(rs.getInt("user_id"));

        employee.setId(rs.getInt("id"));
        employee.setUser(user);
        employee.setName(rs.getString("name"));
        employee.setPhone(rs.getString("phone"));
        employee.setAddress(rs.getString("address"));
        employee.setEmail(rs.getString("email"));
        employee.setRole(rs.getString("role"));
        employee.set_deleted(rs.getBoolean("is_deleted"));
        return employee;
    }
}
