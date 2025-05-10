package sgu.beo.Controller;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sgu.beo.AppEventBus;
import sgu.beo.DTO.CartItemDTO;
import sgu.beo.event.ConfirmImportEvent;
import sgu.beo.model.Employee;
import sgu.beo.model.Supplier;
import sgu.beo.service.SupplierService;

public class ConfirmImportController {
    @FXML
    private Label totalLabel;
    @FXML
    private Label supplierNameLabel;
    @FXML
    private Label supplierPhoneLabel;
    @FXML
    private Label supplierAddressLabel;

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
    private ComboBox<Supplier> supplierComboBox;
    @FXML
    private TextField filterSupplierField;

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;

    private ObservableList<CartItemDTO> cartItems = FXCollections.observableArrayList();
    private ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
    private Stage stage;
    private Supplier supplier;
    private List<CartItemDTO> items;

    public void setData(List<CartItemDTO> items, String total, Stage stage) {
        this.items = items;
        this.stage = stage;

        if (items != null) {
            cartItems.setAll(items);
            cartTable.setItems(cartItems);
        }

        totalLabel.setText(total);

        // Setup combobox
        FilteredList<Supplier> filteredSuppliers = new FilteredList<>(this.suppliers, p -> true);
        supplierComboBox.setItems(filteredSuppliers);

        // Filter logic
        filterSupplierField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredSuppliers.setPredicate(supplier -> {
                if (newVal == null || newVal.isEmpty())
                    return true;
                return supplier.getName().toLowerCase().contains(newVal.toLowerCase());
            });
        });

        // ComboBox selection listener
        supplierComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                supplierNameLabel.setText(newVal.getName());
                supplierPhoneLabel.setText(newVal.getPhone());
                supplierAddressLabel.setText(newVal.getAddress());
                supplier = newVal;
            }
        });
    }

    @FXML
    public void initialize() {
        this.suppliers.setAll(SupplierService.getSupplierList());

        // Giữ nguyên cách bind dữ liệu nhưng điều chỉnh trường dữ liệu
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrand()));

        sizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSize()));

        quantityColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        // Thay đổi ở đây: dùng getTotalCost() thay vì getTotalPrice()
        totalPriceColumn
                .setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getTotalCost()).asObject());

        // Thay đổi ở đây: dùng getCost() từ ProductVariant
        unitPriceColumn.setCellValueFactory(
                cellData -> new SimpleLongProperty(cellData.getValue().getProductVariant().getCost()).asObject());

        colorColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getProductVariant().getColor()));
    }

    @FXML
    private void handleConfirm() {
        AppEventBus.getInstance().post(new ConfirmImportEvent(items, supplier));
        stage.close();
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }
}