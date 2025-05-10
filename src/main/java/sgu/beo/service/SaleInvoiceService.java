package sgu.beo.service;

import java.time.LocalDateTime;
import java.util.List;

import sgu.beo.DAO.SaleInvoiceDAO;
import sgu.beo.DAO.SaleInvoiceDetailDAO;
import sgu.beo.DAO.StockDAO;
import sgu.beo.DTO.CartItemDTO;
import sgu.beo.model.Customer;
import sgu.beo.model.Employee;
import sgu.beo.model.Promotion;
import sgu.beo.model.SaleInvoice;
import sgu.beo.model.SaleInvoiceDetail;
import sgu.beo.model.Stock;

public class SaleInvoiceService {
    public static SaleInvoiceDAO saleInvoiceDAO = SaleInvoiceDAO.getInstance();
    public static SaleInvoiceDetailDAO saleInvoiceDetailDAO = SaleInvoiceDetailDAO.getInstance();
    public static StockDAO stockDAO = StockDAO.getInstance();

    public static boolean addSaleInvoice(Employee employee, Customer customer, List<CartItemDTO> cartItems,
            Promotion promotion) {
        if (cartItems == null || cartItems.isEmpty()) {
            return false;
        }

        long totalAmount = 0;
        for (CartItemDTO item : cartItems) {
            totalAmount += item.getTotalPrice();
        }
        long finalAmount = totalAmount;

        SaleInvoice invoice = new SaleInvoice();
        invoice.setCustomer_id(customer.getId());
        invoice.setEmployee_id(employee.getId());
        invoice.setSale_date(LocalDateTime.now());

        if (promotion != null) {
            long discountValue;
            if (promotion.getPromotion_type().equalsIgnoreCase("percent")) {
                discountValue = totalAmount * promotion.getPromotion_value() / 100;
                if (discountValue > promotion.getMax_discount_value()) {
                    discountValue = promotion.getMax_discount_value();
                }
            } else {
                discountValue = promotion.getPromotion_value();
            }
            finalAmount = totalAmount - discountValue;

            invoice.setPromotion_id(promotion.getId());
            invoice.setPromotion_amount(discountValue);
        }
        invoice.setTotal_amount(totalAmount);
        invoice.setFinal_amount(finalAmount);

        int invoiceId = saleInvoiceDAO.insertAndGetId(invoice);
        if (invoiceId <= 0) {
            return false;
        }

        boolean success = true;

        for (CartItemDTO item : cartItems) {
            SaleInvoiceDetail detail = new SaleInvoiceDetail();
            detail.setSale_invoice_id(invoiceId);
            detail.setProduct_variant_id(item.getProductVariant().getId());
            detail.setQuantity(item.getQuantity());
            detail.setUnit_price(item.getProductVariant().getPrice());
            detail.setTotal_price(item.getTotalPrice());
            detail.setSize(item.getSize());

            Stock stock = stockDAO.findByProductVariantIdAndSize(item.getProductVariant().getId(), item.getSize());
            stock.setQuantity_in_stock(stock.getQuantity_in_stock() - item.getQuantity());
            stockDAO.updateQuantity(stock);

            if (!saleInvoiceDetailDAO.insert(detail)) {
                success = false;
                break;
            }
        }

        return success;
    }
}
