package sgu.beo.service;

import java.util.List;

import sgu.beo.DAO.SupplierDAO;
import sgu.beo.model.Supplier;

public class SupplierService {
    public static final SupplierDAO supplierDAO = SupplierDAO.getInstance();

    public static List<Supplier> getSupplierList() {
        return supplierDAO.findAll();
    }
}
