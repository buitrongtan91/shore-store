package sgu.beo.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sgu.beo.model.Employee;
import sgu.beo.service.AuthService;
import sgu.beo.App;

public class LoginController implements Initializable {

    @FXML
    private TextField emailTxtFld;

    @FXML
    private PasswordField pwdTxtFld;

    @FXML
    private CheckBox showPwdCheckBox;

    @FXML
    private TextField showPwdTxtFld;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPwdTxtFld.textProperty().bindBidirectional(pwdTxtFld.textProperty());
    }

    @FXML
    void handleLgnBttClick(ActionEvent event) {
        String email = emailTxtFld.getText();
        String pwd = pwdTxtFld.getText();

        Employee employee = AuthService.login(email, pwd);

        if (employee != null) {
            try {
                FXMLLoader loader = App.getLoader("view/menu");
                Parent root = loader.load();
                MenuController controller = loader.getController();
                controller.setEmployee(employee);
                App.setRoot(root);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    @FXML
    void handleCheckBxClick(ActionEvent event) {
        if (showPwdCheckBox.isSelected()) {
            pwdTxtFld.setVisible(false);
        } else {
            pwdTxtFld.setVisible(true);
        }
    }
}
