package sgu.beo.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sgu.beo.AppEventBus;
import sgu.beo.DTO.CartItemDTO;
import sgu.beo.event.AddToCartEvent;
import sgu.beo.model.ProductVariant;

public class AddToCartModalController implements Initializable {
    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label brandLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label colorLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private Button cancelBtt;

    @FXML
    private Button addBtt;

    private String name;
    private String brand;
    private String category;
    private ProductVariant variant;
    private Stage stage;

    public void setData(String name, String brand, String category, ProductVariant variant, Stage stage) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.variant = variant;
        this.stage = stage;
        imageView.setImage(
                new Image(getClass().getResource("/sgu/beo/product/" + variant.getImg_url()).toExternalForm()));
        nameLabel.setText(name);
        brandLabel.setText(brand);
        categoryLabel.setText(category);
        colorLabel.setText(variant.getColor());
        priceLabel.setText(String.format("%,d VNĐ", variant.getPrice()));

        List<String> sizes = variant.getStocks().stream()
                .map(stock -> stock.getSize())
                .collect(Collectors.toList());
        sizeComboBox.getItems().addAll(sizes);
        sizeComboBox.getSelectionModel().selectFirst();
    }

    private void handleCancelBttClick() {
        this.stage.close();
    }

    private void handleAddBttClick() {
        String size = sizeComboBox.getValue();
        CartItemDTO item = new CartItemDTO(variant, name, brand, category, size, 1);
        AddToCartEvent event = new AddToCartEvent(item);
        AppEventBus.getInstance().post(event);
        this.stage.close();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cancelBtt.setOnAction(event -> handleCancelBttClick());
        addBtt.setOnAction(event -> handleAddBttClick());
    }
}
