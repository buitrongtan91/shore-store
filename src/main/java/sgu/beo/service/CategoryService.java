package sgu.beo.service;

import java.util.List;

import sgu.beo.DAO.CategoryDAO;
import sgu.beo.model.Category;

public class CategoryService {
    public static CategoryDAO categoryDAO = CategoryDAO.getInstance();

    public static List<Category> getAll() {
        return categoryDAO.findAll();
    }
}
