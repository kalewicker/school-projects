/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.xml.bind.ValidationException;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author kale
 */
public class AddPartScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inhouseRBtn;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private Label machineIdOrCompanyNameLbl;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField pcTxt;

    @FXML
    private TextField machineIdOrCompanyNameTxt;
    
    private int newPartID = 1;
    
    private boolean invValid;
    
    ToggleGroup group = new ToggleGroup();
   
    
    @FXML
    void onActionInhousePart(ActionEvent event) {
        machineIdOrCompanyNameLbl.setText("Machine ID");
    }

    @FXML
    void onActionOutsourcedPart(ActionEvent event) {
        machineIdOrCompanyNameLbl.setText("Company Name");
    }
    
    public int genPartID() {
        for(Part part : Inventory.getAllParts()) {
            if(part.getId() >= newPartID)
                newPartID = part.getId() + 1;
        }
        return newPartID;
    }
    
    
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "All values will be cleared. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        
           int id = newPartID;
           String name = nameTxt.getText();
           double price = Double.parseDouble(pcTxt.getText());
           int stock = Integer.parseInt(invTxt.getText());
           int min = Integer.parseInt(minTxt.getText());
           int max = Integer.parseInt(maxTxt.getText());
           
          
           
           if(inhouseRBtn.isSelected()) {
               machineIdOrCompanyNameLbl.setText("Machine ID");
               InHouse part = new InHouse();
               part.setId(id);
               part.setName(name);
               part.setPrice(price);
               part.setStock(stock);
               part.setMin(min);
               part.setMax(max);
               int machineId = Integer.parseInt(machineIdOrCompanyNameTxt.getText());
               part.setMachineId(machineId);
               
               try {
                   if(part.validation() == true) {
                      Inventory.addPart(part);
                    }
                   stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                   scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                   stage.setScene(new Scene(scene));
                   stage.show();
                }
               catch (ValidationException e) {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setContentText(e.getMessage());
                  Optional<ButtonType> result = alert.showAndWait();
                }
            }
            if(outsourcedRBtn.isSelected()) {
               machineIdOrCompanyNameLbl.setText("Company Name");
               Outsourced part = new Outsourced();
               part.setId(id);
               part.setName(name);
               part.setPrice(price);
               part.setStock(stock);
               part.setMin(min);
               part.setMax(max); 
               String companyName = machineIdOrCompanyNameTxt.getText();
               part.setCompanyName(companyName);
                
                try {
                    if(part.validation() == true) {
                        Inventory.addPart(part);
                    }
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                catch (ValidationException e) {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setContentText(e.getMessage());
                   Optional<ButtonType> result = alert.showAndWait();
                }
            }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genPartID();
        inhouseRBtn.setToggleGroup(group);
        outsourcedRBtn.setToggleGroup(group);
        inhouseRBtn.setSelected(true);
        machineIdOrCompanyNameLbl.setText("Machine ID");
        idTxt.setDisable(true);
        idTxt.setText("Auto Gen - Disabled");
       
    } 
    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
}
