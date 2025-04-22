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

import sgu.beo.model.Brand;

public class BrandDAO extends BaseDAO<Brand> {
    private static final Logger logger = LogManager.getLogger(BrandDAO.class);

    protected BrandDAO() {
    }

    public static BrandDAO getInstance() {
        return BaseDAO.getInstance(BrandDAO.class);
    }

    @Override
    public boolean insert(Brand b) {
        String sql = "INSERT INTO brand (name, logo_url, description) VALUES (?, ?, ?) ";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, b.getName());
            stm.setString(2, b.getLogoUrl());
            stm.setString(3, b.getDescription());
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public boolean update(Brand b) {
        String sql = "UPDATE brand SET name = ?, logo_url = ?, description = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, b.getName());
            stm.setString(2, b.getLogoUrl());
            stm.setString(3, b.getDescription());
            stm.setInt(4, b.getId());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<Brand> findAll() {
        String sql = "SELECT * FROM brand";
        List<Brand> brands = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                brands.add(mapRow(rs));
            }
            return brands;
        } catch (Exception e) {
            logger.error(sql, e);
            return null;
        }

    }

    @Override
    public Brand findById(int id) {
        String sql = "SELECT * FROM brand WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql);) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (Exception e) {
            logger.error(sql, e);
        }
        return null;
    }

    @Override
    public List<Brand> findByName(String name) {
        String sql = "SELECT * FROM brand WHERE LOWER(name) LIKE ?";
        List<Brand> brands = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement smt = conn.prepareStatement(sql)) {
            smt.setString(1, "%" + name.toLowerCase() + "%");
            try (ResultSet rs = smt.executeQuery()) {
                while (rs.next()) {
                    brands.add(mapRow(rs));
                }
            }
            return brands;
        } catch (SQLException e) {
            logger.error(sql, e);
            return null;
        }
    }

    private Brand mapRow(ResultSet rs) throws SQLException {
        Brand brand = new Brand();
        brand.setId(rs.getInt("id"));
        brand.setName(rs.getString("name"));
        brand.setLogoUrl(rs.getString("logo_url"));
        brand.setDescription(rs.getString("description"));
        brand.set_deleted(rs.getBoolean("is_deleted"));
        return brand;
    }
}
