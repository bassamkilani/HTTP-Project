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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

/**
 *
 * @author bassamkilani
 */
public class FXMLDocumentController extends Window implements Initializable {
    
    @FXML
    private Label noOfCases, firstNoCases, secondNoCases;
    
    @FXML
    private ChoiceBox firstCityCB, secondCityCB, cityCB1, cityCB2;
    
    @FXML
    private Pane loginPane;
    
    @FXML
        private TextField newCasesTF;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        loginPane.setManaged(false);
        loginPane.setVisible(false);
    }
    
    @FXML
    private void handleTFAction(ActionEvent event) {
        noOfCases.setText(newCasesTF.getText());
        firstNoCases.setText(newCasesTF.getText());
        secondNoCases.setText(newCasesTF.getText());

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        firstCityCB.getItems().removeAll(firstCityCB.getItems());
        firstCityCB.getItems().addAll("Nablus", "Hebron", "Jerusalem", "Jenin", "Ramallah", "Tulkarem");
//        firstCityCB.getSelectionModel().select("Nablus");
        
        secondCityCB.getItems().removeAll(secondCityCB.getItems());
        secondCityCB.getItems().addAll("Nablus", "Hebron", "Jerusalem", "Jenin", "Ramallah", "Tulkarem");
//        secondCityCB.getSelectionModel().select("Nablus");
        
        cityCB1.getItems().removeAll(cityCB1.getItems());
        cityCB1.getItems().addAll("Nablus", "Hebron", "Jerusalem", "Jenin", "Ramallah", "Tulkarem");
//        cityCB1.getSelectionModel().select("Nablus");
        
        cityCB2.getItems().removeAll(cityCB2.getItems());
        cityCB2.getItems().addAll("Nablus", "Hebron", "Jerusalem", "Jenin", "Ramallah", "Tulkarem");
//        cityCB2.getSelectionModel().select("Nablus");
    }    
    
}
