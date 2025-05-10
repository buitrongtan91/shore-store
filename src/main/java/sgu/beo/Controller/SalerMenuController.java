package sgu.beo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sgu.beo.CustomEvent;

public class SalerMenuController {
    @FXML
    void handleButtonClick(ActionEvent event) {
        Button node = (Button) event.getSource();
        String data = node.getText();
        CustomEvent customEvent = new CustomEvent(data);
        node.fireEvent(customEvent);
    }
}
