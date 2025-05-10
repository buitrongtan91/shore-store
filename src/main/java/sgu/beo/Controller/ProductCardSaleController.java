package sgu.beo.Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Separator;
import sgu.beo.App;
import sgu.beo.model.Product;
import sgu.beo.model.ProductVariant;
import sgu.beo.model.Stock;

public class ProductCardSaleController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label brandLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private FlowPane variantsFlowPane;

    public void setProduct(Product product) {
        nameLabel.setText(product.getName());
        brandLabel.setText(product.getBrand().getName());
        categoryLabel.setText(product.getCategory().getName());

        for (ProductVariant variant : product.getProductVariants()) {
            VBox variantBox = new VBox(5);
            variantBox.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5;");

            ImageView imageView = new ImageView(
                    new Image(getClass().getResource("/sgu/beo/product/" + variant.getImg_url()).toExternalForm()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);

            Label colorLabel = new Label("Color: " + variant.getColor());
            Label priceLabel = new Label("Price: " + String.format("%,d VNĐ", variant.getPrice()));

            VBox colorPriceVbox = new VBox(10, colorLabel, priceLabel);

            HBox imageColorBox = new HBox(10, imageView, colorPriceVbox);
            variantBox.getChildren().add(imageColorBox);

            VBox stockBox = new VBox(3);
            for (Stock stock : variant.getStocks()) {
                Label stockLabel = new Label("Size: " + stock.getSize() + " | Số lượng: " +
                        stock.getQuantity_in_stock());
                stockBox.getChildren().add(stockLabel);
            }

            Button addBtt = new Button("Add");
            addBtt.setOnAction(event -> handleAddBttClick(product.getName(), product.getBrand().getName(),
                    product.getCategory().getName(), variant));

            variantBox.getChildren().addAll(new Separator(), stockBox, addBtt);
            variantsFlowPane.getChildren().add(variantBox);
        }
    }

    private void handleAddBttClick(String name, String brand, String category, ProductVariant productVariant) {
        try {
            FXMLLoader loader = App.getLoader("view/addToCartModal");
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Thêm vào giỏ hàng");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));

            AddToCartModalController controller = loader.getController();
            controller.setData(name, brand, category, productVariant, modalStage);

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
