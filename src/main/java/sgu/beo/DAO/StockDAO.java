package sgu.beo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sgu.beo.model.Stock;

public class StockDAO extends BaseDAO<Stock> {
    protected static final Logger logger = LogManager.getLogger(StockDAO.class);

    protected StockDAO() {

    }

    public static StockDAO getInstance() {
        return getInstance(StockDAO.class);
    }

    @Override
    public boolean insert(Stock entity) {
        String sql = "INSERT INTO stock (product_variant_id, size, quantity_in_stock) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, entity.getProduct_variant_id());
            stm.setString(2, entity.getSize());
            stm.setInt(3, entity.getQuantity_in_stock());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public boolean update(Stock entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public boolean updateQuantity(Stock entity) {
        String sql = "UPDATE stock SET quantity_in_stock = ? WHERE product_variant_id = ? AND size = ?";
        try (Connection conn = getConnection();
                PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, entity.getQuantity_in_stock());
            stm.setInt(2, entity.getProduct_variant_id());
            stm.setString(3, entity.getSize());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.error(sql, e);
            return false;
        }
    }

    @Override
    public List<Stock> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Stock findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public List<Stock> findAllByProductVariantId(int id) {
        String sql = "SELECT * FROM stock WHERE product_variant_id = ?";
        List<Stock> stocks = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    stocks.add(mapRow(rs));
                }

                return stocks;
            }
        } catch (Exception e) {
            logger.error(sql, e);
            return null;
        }
    }

    public Stock findByProductVariantIdAndSize(int id, String size) {
        String sql = "SELECT * FROM stock WHERE product_variant_id = ? AND size = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.setString(2, size);
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
    public List<Stock> findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    private Stock mapRow(ResultSet rs) throws SQLException {
        Stock st = new Stock();
        st.setId(rs.getInt("id"));
        st.setProduct_variant_id(rs.getInt("product_variant_id"));
        st.setSize(rs.getString("size"));
        st.setQuantity_in_stock(rs.getInt("quantity_in_stock"));
        return st;
    }
}
