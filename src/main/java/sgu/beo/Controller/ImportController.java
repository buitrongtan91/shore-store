package sgu.beo.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sgu.beo.App;
import sgu.beo.Controller.ImportController.SizeRow;
import sgu.beo.DTO.CartItemDTO;
import sgu.beo.model.Product;
import sgu.beo.model.ProductVariant;

public class ImportController implements Initializable {
    @FXML
    private Label nameLabel;

    @FXML
    private Label brandLabel;

    @FXML
    private Label colorLabel;

    @FXML
    private Label costLabel;

    @FXML
    private TableView<SizeRow> tableView;

    @FXML
    private TableColumn<SizeRow, String> sizeColumn;

    @FXML
    private TableColumn<SizeRow, Number> quantityColumn;

    @FXML
    private TableColumn<SizeRow, Boolean> checkColumn;

    @FXML
    private Button importBtt;

    private ProductVariant variant;
    private String name;
    private String brand;
    private Stage stage;
    private ObservableList<SizeRow> data = FXCollections.observableArrayList();

    public void setData(String name, String brand, ProductVariant variant, Stage stage) {
        this.variant = variant;
        this.name = name;
        this.brand = brand;
        this.stage = stage;

        nameLabel.setText(name);
        brandLabel.setText(brand);
        colorLabel.setText(variant.getColor());
        costLabel.setText(String.valueOf(variant.getCost()));

    }

    public class SizeRow {
        private final StringProperty size;
        private final IntegerProperty quantity;
        private final BooleanProperty selected;

        public SizeRow(String size) {
            this.size = new SimpleStringProperty(size);
            this.quantity = new SimpleIntegerProperty(0);
            this.selected = new SimpleBooleanProperty(false);
        }

        public String getSize() {
            return size.get();
        }

        public StringProperty sizeProperty() {
            return size;
        }

        public int getQuantity() {
            return quantity.get();
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        public IntegerProperty quantityProperty() {
            return quantity;
        }

        public boolean isSelected() {
            return selected.get();
        }

        public BooleanProperty selectedProperty() {
            return selected;
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Cột Size (String)
        sizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());

        // Cột Checkbox
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkColumn));

        // Cột Quantity (chỉ cho nhập nếu checkbox được chọn)
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        quantityColumn.setCellFactory(col -> new TableCell<SizeRow, Number>() {
            private final TextField textField = new TextField();
            private SizeRow currentRow = null; // Theo dõi hàng hiện tại

            {
                textField.textProperty().addListener((obs, oldVal, newVal) -> {
                    if (!newVal.matches("\\d*")) {
                        textField.setText(oldVal);
                    }
                });

                textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused) {
                        SizeRow row = getTableRow().getItem();
                        if (row != null) {
                            try {
                                int val = Integer.parseInt(textField.getText());
                                row.setQuantity(val);
                            } catch (NumberFormatException e) {
                                textField.setText(String.valueOf(row.getQuantity()));
                            }
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);

                // Gỡ listener khỏi hàng cũ
                if (currentRow != null) {
                    currentRow.selectedProperty().removeListener(rowSelectedListener);
                    currentRow = null;
                }

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    currentRow = getTableRow().getItem();

                    textField.setText(item != null ? String.valueOf(item.intValue()) : "0");
                    textField.setDisable(!currentRow.isSelected());

                    // Thêm listener vào hàng mới
                    currentRow.selectedProperty().addListener(rowSelectedListener);
                    setGraphic(textField);
                }
            }

            private final ChangeListener<Boolean> rowSelectedListener = (obs, oldVal, newVal) -> textField
                    .setDisable(!newVal);
        });

        for (int i = 35; i <= 45; i++) {
            data.add(new SizeRow(String.valueOf(i)));
        }

        tableView.setEditable(true);
        tableView.setItems(data);

        importBtt.setOnAction(event -> handleImportBttClick());
    }

    private void handleImportBttClick() {
        List<CartItemDTO> items = new ArrayList<>();
        data.stream().filter(d -> d.isSelected() && d.getQuantity() > 0).forEach(d -> {
            items.add(new CartItemDTO(variant, name, brand, null, d.getSize(), d.getQuantity()));
        });

        long total = 0;

        for (CartItemDTO cartItemDTO : items) {
            total = total + cartItemDTO.getTotalPrice();
        }

        try {
            FXMLLoader loader = App.getLoader("view/confirmImport");
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Thêm vào giỏ hàng");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));

            ConfirmImportController controller = loader.getController();
            controller.setData(items, String.valueOf(total), modalStage);

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
