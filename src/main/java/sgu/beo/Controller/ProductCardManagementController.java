package sgu.beo.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import javafx.scene.layout.Region;
import javafx.scene.control.Separator;
import sgu.beo.App;
import sgu.beo.model.Product;
import sgu.beo.model.ProductVariant;
import sgu.beo.model.Stock;

public class ProductCardManagementController implements Initializable {
    @FXML
    private Button createProductVariantBtt;

    @FXML
    private Button updateProductBtt;

    @FXML
    private Label nameLabel;

    @FXML
    private Label brandLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private FlowPane variantsFlowPane;
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
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

            HBox imageHBox = new HBox();
            imageHBox.setPrefHeight(100);
            imageHBox.setPrefWidth(100);
            imageHBox.setMinHeight(100);
            imageHBox.setMinWidth(100);
            imageHBox.setAlignment(Pos.CENTER);

            imageHBox.getChildren().add(imageView);

            Label colorLabel = new Label("Color: " + variant.getColor());
            Label priceLabel = new Label("Price: " + String.format("%,d VNĐ", variant.getPrice()));

            VBox colorPriceVbox = new VBox(10, colorLabel, priceLabel);

            HBox imageColorBox = new HBox(10, imageHBox, colorPriceVbox);
            variantBox.getChildren().add(imageColorBox);

            VBox stockBox = new VBox(3);
            for (Stock stock : variant.getStocks()) {
                Label stockLabel = new Label("Size: " + stock.getSize() + " | Số lượng: " +
                        stock.getQuantity_in_stock());
                stockBox.getChildren().add(stockLabel);
            }

            Button updateBtt = new Button("Sửa");
            Button importBtt = new Button("Nhập hàng");

            HBox bttHBox = new HBox();
            bttHBox.getChildren().addAll(updateBtt, importBtt);

            updateBtt.setOnAction(event -> handleUpdateProductVariant(variant));
            importBtt.setOnAction(
                    event -> handleImportBttClick(product.getName(), product.getBrand().getName(), variant));

            variantBox.getChildren().addAll(new Separator(), stockBox, bttHBox);
            variantsFlowPane.getChildren().add(variantBox);
        }
    }

    private void handleUpdateProductBttClick(Product product) {
        try {
            FXMLLoader loader = App.getLoader("view/updateProductModal");
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Thêm vào giỏ hàng");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));

            UpdateProductModalController controller = loader.getController();
            controller.setData(product, modalStage);

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCreateProductVariantBttClick(Product product) {
        try {
            FXMLLoader loader = App.getLoader("view/createProductVariant");
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Thêm vào giỏ hàng");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));

            CreateProductVariantModalController controller = loader.getController();
            controller.setData(product, modalStage);

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUpdateProductVariant(ProductVariant variant) {
        try {
            FXMLLoader loader = App.getLoader("view/updateProductVariant");
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Sửa biến thể sản phẩm");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));

            UpdateProductVariantController controller = loader.getController();
            controller.setData(variant, modalStage);

            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleImportBttClick(String name, String brand, ProductVariant variant) {
        try {
            FXMLLoader loader = App.getLoader("view/import");
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Sửa biến thể sản phẩm");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));

            ImportController controller = loader.getController();
            controller.setData(name, brand, variant, modalStage);

            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateProductBtt.setOnAction(event -> handleUpdateProductBttClick(product));
        createProductVariantBtt.setOnAction(event -> handleCreateProductVariantBttClick(product));
    }
}
