package sgu.beo.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import sgu.beo.App;
import sgu.beo.CustomEvent;
import sgu.beo.model.Employee;

public class MenuController implements Initializable {
    @FXML
    private ImageView avatarImageView;

    @FXML
    private Label employeeEmailLabel;

    @FXML
    private Label employeeNameLabel;

    @FXML
    private StackPane mainContentPane;

    @FXML
    private Pane menuPane;

    @FXML
    private BorderPane rootPane;

    private Employee employee;

    public void setEmployee(Employee e) {
        this.employee = e;
        loadDefaultView();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        menuPane.addEventHandler(CustomEvent.CUSTOM_EVENT, event -> {
            String str = event.getMessage();

        });

        try {
            FXMLLoader loader = App.getLoader("view/salermenu");
            Parent salermenu = loader.load();
            menuPane.getChildren().setAll(salermenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDefaultView() {
        try {
            FXMLLoader loader = App.getLoader("view/productManagement");
            Parent feat = loader.load();
            ProductManagementController controller = loader.getController();
            controller.setData(employee); // truyền employee sau khi nó đã có
            mainContentPane.getChildren().add(feat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
