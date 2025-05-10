package sgu.beo.service;

import java.util.List;

import java.util.stream.Collectors;

import sgu.beo.DAO.CustomerDAO;
import sgu.beo.model.Customer;
import sgu.beo.util.Result;

public class CustomerService {
    public class CustomerServiceResponse {

    }

    public static CustomerDAO customerDAO = CustomerDAO.getInstance();

    public static List<Customer> getCustomerList() {
        return customerDAO.findAll();
    }

    public static List<Customer> search(String string) {
        List<Customer> customers = customerDAO.findAll(); // lấy toàn bộ danh sách khách hàng

        if (string == null || string.trim().isEmpty()) {
            return customers; // nếu không nhập gì thì trả về toàn bộ danh sách
        }

        String keyword = string.toLowerCase();

        return customers.stream()
                .filter(customer -> customer.getName().toLowerCase().contains(keyword) || // lọc theo mã
                        customer.getPhone().toLowerCase().contains(keyword) // lọc theo số điện thoại
                )
                .collect(Collectors.toList());
    }

    public Result<Customer> addCustomer(Customer customer) {
        // Kiểm tra dữ liệu đầu vào
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            return new Result<>(false, "Tên khách hàng không được để trống.");
        }
        if (customer.getPhone() == null || customer.getPhone().trim().isEmpty()) {
            return new Result<>(false, "Số điện thoại không được để trống.");
        }

        // Kiểm tra trùng số điện thoại
        List<Customer> existingCustomers = customerDAO.findAll();
        for (Customer c : existingCustomers) {
            if (c.getPhone().equals(customer.getPhone())) {
                return new Result<>(false, "Số điện thoại đã tồn tại.");
            }
        }

        // Gọi DAO để lưu vào DB
        boolean inserted = customerDAO.insert(customer);
        if (inserted) {
            return new Result<>(true, "Thêm khách hàng thành công.", customer);
        } else {
            return new Result<>(false, "Lỗi khi lưu khách hàng vào cơ sở dữ liệu.");
        }
    }
}
