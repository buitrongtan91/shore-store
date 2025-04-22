package sgu.beo;

import java.sql.Connection;

import sgu.beo.DAO.BrandDAO;
import sgu.beo.DAO.CategoryDAO;
import sgu.beo.DAO.ProductDAO;
import sgu.beo.model.Brand;
import sgu.beo.model.Category;
import sgu.beo.model.Gender;
import sgu.beo.model.Product;
import sgu.beo.util.HibernateUtil;
import sgu.beo.util.JdbcUtil;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
        HibernateUtil.shutdown();
    }
}
