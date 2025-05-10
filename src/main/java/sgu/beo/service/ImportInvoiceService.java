package sgu.beo.service;

import java.time.LocalDateTime;
import java.util.List;

import sgu.beo.DAO.ImportInvoiceDAO;
import sgu.beo.DAO.ImportInvoiceDetailDAO;
import sgu.beo.DAO.StockDAO;
import sgu.beo.DTO.CartItemDTO;
import sgu.beo.model.Employee;
import sgu.beo.model.ImportInvoice;
import sgu.beo.model.ImportInvoiceDetail;
import sgu.beo.model.SaleInvoiceDetail;
import sgu.beo.model.Stock;
import sgu.beo.model.Supplier;

public class ImportInvoiceService {
    public static ImportInvoiceDAO importInvoiceDAO = ImportInvoiceDAO.getInstance();
    public static ImportInvoiceDetailDAO importInvoiceDetailDAO = ImportInvoiceDetailDAO.getInstance();
    public static StockDAO stockDAO = StockDAO.getInstance();

    public static boolean addImportInvoice(Employee employee, Supplier supplier, List<CartItemDTO> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return false;
        }

        long totalAmount = 0;
        for (CartItemDTO item : cartItems) {
            totalAmount += item.getTotalCost();
        }

        ImportInvoice invoice = new ImportInvoice();
        invoice.setEmployee_id(employee.getId());
        invoice.setSupplier_id(supplier.getId());
        invoice.setImport_date(LocalDateTime.now());
        invoice.setTotal_amount(totalAmount);

        int invoiceId = importInvoiceDAO.insertAndGetId(invoice);

        if (invoiceId <= 0) {
            return false;
        }

        boolean success = true;

        for (CartItemDTO item : cartItems) {
            ImportInvoiceDetail detail = new ImportInvoiceDetail();
            detail.setImport_invoice_id(invoiceId);
            detail.setProduct_variant_id(item.getProductVariant().getId());
            detail.setQuantity(item.getQuantity());
            detail.setUnit_price(item.getProductVariant().getCost());
            detail.setTotal_price(item.getTotalCost());
            detail.setSize(item.getSize());

            Stock stock = stockDAO.findByProductVariantIdAndSize(item.getProductVariant().getId(), item.getSize());
            if (stock != null) {
                stock.setQuantity_in_stock(stock.getQuantity_in_stock() + item.getQuantity());
                stockDAO.updateQuantity(stock);
            } else {
                Stock st = new Stock();
                st.setProduct_variant_id(item.getProductVariant().getId());
                st.setSize(item.getSize());
                st.setQuantity_in_stock(item.getQuantity());
                stockDAO.insert(st);
            }

            if (!importInvoiceDetailDAO.insert(detail)) {
                success = false;
                break;
            }
        }

        return success;
    }
}
