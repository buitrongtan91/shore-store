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

import sgu.beo.model.Category;

public class CategoryDAO extends BaseDAO<Category> {
    private static final Logger logger = LogManager.getLogger(CategoryDAO.class);

    protected CategoryDAO() {
    }

    public static CategoryDAO getInstance() {
        return getInstance(CategoryDAO.class);
    }

    @Override
    public boolean insert(Category category) {
        String sql = "INSERT INTO category (name, description) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, category.getName());
            smt.setString(2, category.getDescription());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public boolean update(Category category) {
        String sql = "UPDATE category SET name = ?, description = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, category.getName());
            smt.setString(2, category.getDescription());
            smt.setInt(3, category.getId());
            return smt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM category";
        List<Category> categories = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(sql)) {
            while (rs.next()) {
                categories.add(mapRow(rs));
            }
            return categories;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    @Override
    public Category findById(int id) {
        String sql = "SELECT * FROM category WHERE id = ?";
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
    public List<Category> findByName(String name) {
        String sql = "SELECT * FROM category WHERE LOWER(name) LIKE ?";
        List<Category> categories = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, "%" + name.toLowerCase() + "%");
            try (ResultSet rs = smt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapRow(rs));
                }
            }
            return categories;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    private Category mapRow(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.set_deleted(rs.getBoolean("is_deleted"));
        return category;
    }
}
