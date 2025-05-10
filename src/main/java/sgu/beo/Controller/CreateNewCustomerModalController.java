package sgu.beo.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sgu.beo.AppEventBus;
import sgu.beo.event.CustomerCreatedEvent;
import sgu.beo.model.Customer;
import sgu.beo.service.CustomerService;
import sgu.beo.util.Result;

public class CreateNewCustomerModalController implements Initializable {
    @FXML
    private TextField nameField;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private TextField phoneField;

    @FXML
    private Label phoneErrorLabel;

    @FXML
    private TextField addressField;

    @FXML
    private Label addressErrorLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        createButton.setOnAction(event -> handleCreate());
    }

    private Stage stage;

    public void setData(Stage stage) {
        this.stage = stage;
    }

    private void handleCreate() {
        // Xóa thông báo lỗi cũ
        nameErrorLabel.setText("");
        phoneErrorLabel.setText("");
        emailErrorLabel.setText("");
        addressErrorLabel.setText("");

        // Lấy dữ liệu từ form
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        boolean isValid = true;

        // Kiểm tra tên
        if (name.isEmpty()) {
            nameErrorLabel.setText("Tên không được để trống");
            isValid = false;
        }

        // Kiểm tra số điện thoại
        if (phone.isEmpty()) {
            phoneErrorLabel.setText("Số điện thoại không được để trống");
            isValid = false;
        } else if (!phone.matches("\\d{10,11}")) {
            phoneErrorLabel.setText("Số điện thoại không hợp lệ");
            isValid = false;
        }

        // Có lỗi nhập liệu => dừng lại
        if (!isValid) {
            return;
        }

        // Tạo đối tượng Customer
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);

        // Gọi service
        CustomerService customerService = new CustomerService();
        Result<Customer> result = customerService.addCustomer(customer);

        if (result.isSuccess()) {
            AppEventBus.getInstance().post(new CustomerCreatedEvent(result.getData()));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Thêm khách hàng thành công!");
            alert.showAndWait();
            stage.close();
        } else {
            // Xử lý thông báo lỗi cụ thể từ service
            String message = result.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR, message);
            alert.showAndWait();
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
