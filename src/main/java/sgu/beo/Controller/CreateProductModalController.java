package sgu.beo.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import sgu.beo.AppEventBus;
import sgu.beo.event.UpdatedProductEvent;
import sgu.beo.model.Brand;
import sgu.beo.model.Category;
import sgu.beo.model.Product;
import sgu.beo.service.BrandService;
import sgu.beo.service.CategoryService;
import sgu.beo.service.ProductService;

public class CreateProductModalController implements Initializable {
    @FXML
    private Button updateBtt;

    @FXML
    private Button cancelBtt;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> brandComboBox;

    @FXML
    private ComboBox<String> categoryComboBox;
    private Stage stage;
    private List<Brand> brandList = BrandService.getAll();
    private List<Category> categoryList = CategoryService.getAll();

    public void setData(Stage stage) {

        this.stage = stage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        List<String> brands = brandList.stream().map(b -> b.getName()).collect(Collectors.toList());
        List<String> categorys = categoryList.stream().map(c -> c.getName()).collect(Collectors.toList());
        brandComboBox.getItems().addAll(brands);
        categoryComboBox.getItems().addAll(categorys);
        brandComboBox.getSelectionModel().selectFirst();
        categoryComboBox.getSelectionModel().selectFirst();

        updateBtt.setOnAction(event -> handleUpdateBttClick());
    }

    private void handleUpdateBttClick() {
        String name = nameField.getText();
        String selectedBrandName = brandComboBox.getValue();
        String selectedCategoryName = categoryComboBox.getValue();

        if (name.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Tên không được để trống!");
            alert.showAndWait();
            return;
        }

        // Tìm brand và category theo tên
        Brand selectedBrand = brandList.stream()
                .filter(b -> b.getName().equals(selectedBrandName))
                .findFirst()
                .orElse(null);

        Category selectedCategory = categoryList.stream()
                .filter(c -> c.getName().equals(selectedCategoryName))
                .findFirst()
                .orElse(null);

        if (selectedBrand == null || selectedCategory == null) {
            System.out.println("Brand hoặc Category không hợp lệ.");
            return;
        }

        Product product = new Product();

        product.setName(name);
        product.setBrand(selectedBrand);
        product.setCategory(selectedCategory);

        boolean success = ProductService.createProduct(product);
        if (success) {
            AppEventBus.getInstance().post(new UpdatedProductEvent());
            Alert alert = new Alert(AlertType.INFORMATION, "Cập nhật thành công!");
            alert.showAndWait();
            stage.close();
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Cập nhật thất bại!");
            alert.showAndWait();
        }
    }

    private void handleCancelBttClick() {
        stage.close();
    }

}
