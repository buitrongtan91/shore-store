package sgu.beo.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.IntegerStringConverter;
import sgu.beo.AppEventBus;
import sgu.beo.DTO.CartItemDTO;
import sgu.beo.event.ChangeQuantityInCart;
import sgu.beo.event.RemoveFromCartEvent;

public class CartItemController implements Initializable {
    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Label colorLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label brandLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Button deleteBtt;

    private CartItemDTO item;

    private boolean isUpdating = false;

    TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), 0, change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d*")) {
            return change;
        }
        return null; // bỏ thay đổi nếu không hợp lệ
    });

    public void setData(CartItemDTO item) {
        this.item = item;
        isUpdating = true;

        nameLabel.setText(item.getName());
        brandLabel.setText(item.getBrand());
        colorLabel.setText("Color: " + item.getProductVariant().getColor() + " | Size: " + item.getSize());
        priceLabel.setText(String.format("%,d VNĐ", item.getProductVariant().getPrice()));
        totalLabel.setText(String.format("%,d VNĐ", item.getTotalPrice()));
        imageView.setImage(new Image(
                getClass().getResource("/sgu/beo/product/" + item.getProductVariant().getImg_url()).toExternalForm()));
        quantitySpinner.getValueFactory().setValue(item.getQuantity());
        quantitySpinner.getEditor().setText(String.valueOf(item.getQuantity()));

        isUpdating = false;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        quantitySpinner.setEditable(true);
        quantitySpinner.getEditor().setTextFormatter(formatter);
        quantitySpinner.setPrefHeight(30);
        quantitySpinner.setMinHeight(30);
        quantitySpinner.setMaxHeight(30);

        TextField editor = quantitySpinner.getEditor();
        editor.setStyle("-fx-font-size: 12px;");
        editor.setPrefHeight(30);
        editor.setMinHeight(30);
        editor.setMaxHeight(30);

        quantitySpinner.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                try {
                    int value = Integer.parseInt(quantitySpinner.getEditor().getText());
                    quantitySpinner.getValueFactory().setValue(value);
                } catch (NumberFormatException e) {
                    quantitySpinner.getEditor().setText(quantitySpinner.getValue().toString()); // reset nếu sai
                }
            }
        });

        quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (!isUpdating && newValue != null) {
                ChangeQuantityInCart event = new ChangeQuantityInCart(item, newValue);
                AppEventBus.getInstance().post(event);
                System.out.println("zzzzzzzzzzzzzzzz");
            }
        });

        deleteBtt.setOnAction(event -> handleDeleteBttClick(item));
    }

    private void handleDeleteBttClick(CartItemDTO itemDTO) {
        RemoveFromCartEvent event = new RemoveFromCartEvent(itemDTO);
        AppEventBus.getInstance().post(event);
    }

}
