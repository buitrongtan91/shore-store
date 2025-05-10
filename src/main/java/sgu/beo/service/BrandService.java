package sgu.beo.service;

import java.util.List;

import sgu.beo.DAO.BrandDAO;
import sgu.beo.model.Brand;

public class BrandService {
    public static BrandDAO brandDAO = BrandDAO.getInstance();

    public static List<Brand> getAll() {
        return brandDAO.findAll();
    }
}
