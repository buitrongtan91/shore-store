package sgu.beo.service;

import java.util.List;
import sgu.beo.DAO.BrandDAO;
import sgu.beo.DAO.CategoryDAO;
import sgu.beo.DAO.ProductDAO;
import sgu.beo.DAO.ProductVariantDAO;
import sgu.beo.DAO.StockDAO;
import sgu.beo.model.Product;
import sgu.beo.model.ProductVariant;
import sgu.beo.model.Stock;

public class ProductService {
    public static ProductDAO productDAO = ProductDAO.getInstance();
    public static ProductVariantDAO productVariantDAO = ProductVariantDAO.getInstance();
    public static StockDAO stockDAO = StockDAO.getInstance();
    public static BrandDAO brandDAO = BrandDAO.getInstance();
    public static CategoryDAO categoryDAO = CategoryDAO.getInstance();

    public static List<Product> getAll() {
        List<Product> products = productDAO.findAll();
        products.forEach(p -> {
            List<ProductVariant> variants = productVariantDAO.findAllByProductId(p.getId());
            variants.forEach(v -> {
                List<Stock> stocks = stockDAO.findAllByProductVariantId(v.getId());
                v.setStocks(stocks);
            });
            p.setProductVariants(variants);
            p.setBrand(brandDAO.findById(p.getBrand().getId()));
            p.setCategory(categoryDAO.findById(p.getCategory().getId()));
        });

        return products;
    }

    public static boolean updateProduct(Product product) {
        return productDAO.update(product);
    }

    public static boolean createProductVariant(ProductVariant productVariant) {
        int id = productVariantDAO.insertAndGetId(productVariant);
        if (id > 0) {
            return true;
        }
        return false;
    }

    public static boolean updateProductVariant(ProductVariant variant) {
        return productVariantDAO.update(variant);
    }

    public static boolean createProduct(Product product) {
        return productDAO.insert(product);
    }
}
