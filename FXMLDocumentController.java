/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpjavafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Window;

/**
 *
 * @author bassamkilani
 */
public class FXMLDocumentController extends Window implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private ChoiceBox firstCityCB, secondCityCB;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        firstCityCB.getItems().removeAll(firstCityCB.getItems());
        firstCityCB.getItems().addAll("Nablus", "Hebron", "Jerusalem", "Jenin", "Ramallah", "Tulkarem");
        firstCityCB.getSelectionModel().select("Nablus");
        
        secondCityCB.getItems().removeAll(secondCityCB.getItems());
        secondCityCB.getItems().addAll("Nablus", "Hebron", "Jerusalem", "Jenin", "Ramallah", "Tulkarem");
        secondCityCB.getSelectionModel().select("Nablus");
    }    
    
}
