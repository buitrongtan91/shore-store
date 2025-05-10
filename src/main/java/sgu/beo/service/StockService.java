package sgu.beo.service;

import sgu.beo.DAO.StockDAO;
import sgu.beo.model.Stock;

public class StockService {
    public static StockDAO stockDAO = StockDAO.getInstance();

    public static int getAvailableQuantity(int id, String size) {
        Stock stock = stockDAO.findByProductVariantIdAndSize(id, size);
        return stock.getQuantity_in_stock();
    }
}
