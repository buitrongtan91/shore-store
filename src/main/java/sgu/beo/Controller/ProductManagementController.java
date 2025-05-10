package sgu.beo.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sgu.beo.App;
import sgu.beo.AppEventBus;
import sgu.beo.event.ConfirmImportEvent;
import sgu.beo.event.CreatedProductVariant;
import sgu.beo.event.UpdatedProductEvent;
import sgu.beo.model.Employee;
import sgu.beo.model.Product;
import sgu.beo.service.ImportInvoiceService;
import sgu.beo.service.ProductService;

public class ProductManagementController implements Initializable {
    @FXML
    private Button createProductBtt;

    @FXML
    private VBox productVBox;
    private List<Product> products = ProductService.getAll();
    private Employee employee;

    public void setData(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        AppEventBus.getInstance().register(this);
        createProductBtt.setOnAction(event -> handleCreateBttClick());
        loadProducts();
    }

    private void loadProducts() {
        products = ProductService.getAll();
        productVBox.getChildren().clear();
        for (Product product : products) {
            try {
                FXMLLoader loader = App.getLoader("view/productCardManagement");
                Parent p = loader.load();
                ProductCardManagementController controller = loader.getController();
                controller.setProduct(product);
                productVBox.getChildren().add(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe
    public void onUpdatedProductEvent(UpdatedProductEvent event) {
        loadProducts();
    }

    @Subscribe
    public void onUpdatedProductEvent(CreatedProductVariant event) {
        loadProducts();
    }

    @Subscribe
    public void onConfirmImportEvent(ConfirmImportEvent event) {
        boolean rs = ImportInvoiceService.addImportInvoice(employee, event.getSupplier(), event.getItems());
        loadProducts();
    }

    private void handleCreateBttClick() {
        try {
            FXMLLoader loader = App.getLoader("view/createProductModal");
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Thêm vào giỏ hàng");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));

            CreateProductModalController controller = loader.getController();
            controller.setData(modalStage);

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
