package sgu.beo.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sgu.beo.AppEventBus;
import sgu.beo.event.CreatedProductVariant;
import sgu.beo.model.ProductVariant;
import sgu.beo.service.ProductService;

public class UpdateProductVariantController implements Initializable {
    @FXML
    private ImageView imageView;

    @FXML
    private Button updateBtt;

    @FXML
    private TextField nameLabel;

    @FXML
    private TextField costLabel;

    @FXML
    private TextField priceLabel;

    @FXML
    private Button fileBtt;

    private ProductVariant productVariant;

    private Stage stage;

    private String image_url;

    public void setData(ProductVariant variant, Stage stage) {
        this.productVariant = variant;
        this.stage = stage;
        image_url = variant.getImg_url();

        nameLabel.setText(productVariant.getColor());
        costLabel.setText(String.valueOf(productVariant.getCost()));
        priceLabel.setText(String.valueOf(productVariant.getPrice()));
        imageView.setImage(
                new Image(getClass().getResource("/sgu/beo/product/" + productVariant.getImg_url()).toExternalForm()));

    }

    private void handleFileBttClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file để upload");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Ảnh", "*.png", "*.jpg", "*.jpeg"));
        Stage fileStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(fileStage);

        if (selectedFile != null) {
            try {
                // 1. Xác định thư mục đích trong resources
                String uploadDir = "src/main/resources/sgu/beo/product/";
                File destDir = new File(uploadDir);

                // Tạo thư mục nếu chưa tồn tại
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }

                // 2. Tạo tên file mới (tránh trùng lặp)
                String originalFileName = selectedFile.getName();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String newFileName = System.currentTimeMillis() + fileExtension;

                // 3. Sao chép file vào thư mục đích
                File destFile = new File(uploadDir + newFileName);
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Upload file thành công!");
                System.out.println("File gốc: " + originalFileName);
                System.out.println("File lưu trữ: " + newFileName);
                System.out.println("Đường dẫn: uploads/" + newFileName);
                image_url = newFileName;
                imageView.setImage(new Image(new FileInputStream(selectedFile)));
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Lỗi khi upload file: " + ex.getMessage());
            }
        }

    }

    private void handleUpdateBttClick() {

        String color = nameLabel.getText();
        String priceText = priceLabel.getText();
        String costText = costLabel.getText();

        if (color.isEmpty() || priceText.isEmpty() || costText.isEmpty()) {

            Alert alert = new Alert(AlertType.ERROR, "Vui lòng nhập đầy đủ thông tin!");
            alert.showAndWait();
            return;
        }

        long price = Long.parseLong(priceText);
        long cost = Long.parseLong(costText);

        ProductVariant variant = new ProductVariant();
        variant.setColor(color);
        variant.setCost(cost);
        variant.setPrice(price);
        variant.setStatus("test");
        variant.setImg_url(image_url);
        variant.setId(productVariant.getId());

        ProductService.updateProductVariant(variant);
        AppEventBus.getInstance().post(new CreatedProductVariant());

        stage.close();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        fileBtt.setOnAction(event -> handleFileBttClick());
        updateBtt.setOnAction(event -> handleUpdateBttClick());
    }

}
