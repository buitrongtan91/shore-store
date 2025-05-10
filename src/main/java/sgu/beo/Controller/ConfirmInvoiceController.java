package sgu.beo.Controller;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sgu.beo.AppEventBus;
import sgu.beo.DTO.CartItemDTO;
import sgu.beo.event.ConfirmInvoiceEvent;
import sgu.beo.model.Customer;

public class ConfirmInvoiceController {
    @FXML
    private Label totalLabel;

    @FXML
    private Label promotionLabel;

    @FXML
    private Label finalAmountLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private TableView<CartItemDTO> cartTable;

    @FXML
    private TableColumn<CartItemDTO, String> nameColumn;
    @FXML
    private TableColumn<CartItemDTO, String> brandColumn;
    @FXML
    private TableColumn<CartItemDTO, String> sizeColumn;
    @FXML
    private TableColumn<CartItemDTO, Integer> quantityColumn;
    @FXML
    private TableColumn<CartItemDTO, Long> totalPriceColumn;

    @FXML
    private TableColumn<CartItemDTO, String> colorColumn;

    @FXML
    private TableColumn<CartItemDTO, Long> unitPriceColumn;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    private ObservableList<CartItemDTO> cartItems = FXCollections.observableArrayList();
    private Stage stage;
    private Customer customer;

    public void setData(List<CartItemDTO> items, Customer customer, String total, String promotion, String finalAmount,
            Stage stage) {
        this.customer = customer;
        if (items == null) {
            System.out.println("Cart items is null!");
            return;
        }
        cartItems.setAll(items);
        this.stage = stage;
        cartTable.setItems(cartItems);
        nameLabel.setText(customer.getName());
        phoneLabel.setText(customer.getPhone());
        emailLabel.setText(customer.getEmail() != null ? customer.getEmail() : "");
        addressLabel.setText(customer.getAddress() != null ? customer.getAddress() : "");
        totalLabel.setText(total);
        promotionLabel.setText(promotion);
        finalAmountLabel.setText(finalAmount);
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrand()));

        sizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSize()));

        quantityColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        totalPriceColumn.setCellValueFactory(
                cellData -> new SimpleLongProperty(cellData.getValue().getTotalPrice()).asObject());
        unitPriceColumn.setCellValueFactory(
                cellData -> new SimpleLongProperty(cellData.getValue().getProductVariant().getPrice()).asObject());
        colorColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getProductVariant().getColor()));

    }

    @FXML
    private void handleConfirm() {
        AppEventBus.getInstance().post(new ConfirmInvoiceEvent());
        this.stage.close();
    }

    @FXML
    private void handleCancel() {
        // // Reset customer fields or close the window
        // customerNameField.clear();
        // customerPhoneField.clear();
        // customerEmailField.clear();
        // customerAddressField.clear();
    }

}
