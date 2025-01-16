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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.xml.bind.ValidationException;
import model.Inventory;
import static model.Inventory.addPart;
import model.Part;
import model.Product;


/**
 * FXML Controller class
 *
 * @author kale
 */
public class AddProductScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField addProIDTxt;

    @FXML
    private TextField addProNameTxt;

    @FXML
    private TextField addProInvTxt;

    @FXML
    private TextField addProPriceTxt;

    @FXML
    private TextField addProMaxTxt;

    @FXML
    private TextField addProMinTxt;

    @FXML
    private TextField addProSearchTxt;

    @FXML
    private TableView<Part> addProAvailablePartsScreenTableView;

    @FXML
    private TableColumn<Part, Integer> addProPartIdAddCol;

    @FXML
    private TableColumn<Part, String> addProPartNameAddCol;

    @FXML
    private TableColumn<Part, Integer> addProInvAddCol;

    @FXML
    private TableColumn<Part, Double> addProPpUAddCol;

    @FXML
    private TableView<Part> addProAssociatedPartsTableView;

    @FXML
    private TableColumn<Part, Integer> addProPartIdDelCol;

    @FXML
    private TableColumn<Part, String> addProPartNameDelCol;

    @FXML
    private TableColumn<Part, Integer> addProInvDelCol;

    @FXML
    private TableColumn<Part, Double> addProPpUDelCol;
    
    private int newProductID = 1;
    
    private ObservableList<Part> addPart = FXCollections.observableArrayList();
    
    private void setDeleteTable() {
       addProAssociatedPartsTableView.setItems(addPart);
   }
    
    public int genProductID() {
        for(Product product : Inventory.getAllProducts()) {
            if(product.getId() >= newProductID) 
                newProductID = product.getId() + 1;
        }
        return newProductID;
    }

    @FXML
    void onActionAddProAdd(ActionEvent event) {
        Part part = addProAvailablePartsScreenTableView.getSelectionModel().getSelectedItem();
        addPart.add(part);
        setDeleteTable();
     }

    @FXML
    void onActionAddProDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Selected part will be deleted. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
            addPart.remove(addProAssociatedPartsTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onActionAddProSave(ActionEvent event) throws IOException {
        
        int id = newProductID;
        String name = addProNameTxt.getText();
        double price = Double.parseDouble(addProPriceTxt.getText());
        int stock = Integer.parseInt(addProInvTxt.getText());
        int min = Integer.parseInt(addProMinTxt.getText());
        int max = Integer.parseInt(addProMaxTxt.getText());
        
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setMin(min);
        product.setMax(max);
            
        for(Part part : addPart)
           product.addAssociatedPart(part);
         
         try {
             if(product.validation() == true) {
                 Inventory.addProduct(product);
             }
             addPart.clear();
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

    @FXML
    void onActionAddProSearch(ActionEvent event) {
        try {
            addProAvailablePartsScreenTableView.setItems(Inventory.lookupPartID(Integer.parseInt(addProSearchTxt.getText())));
        } catch(NumberFormatException e) {
            addProAvailablePartsScreenTableView.setItems(Inventory.lookupPartName(addProSearchTxt.getText()));
        }
    }

    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "All values will be cleared. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            addPart.clear();
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genProductID();
        setDeleteTable();
        
        addProIDTxt.setDisable(true);
        addProIDTxt.setText("Auto Gen - Disabled");
        
        addProAvailablePartsScreenTableView.setItems(Inventory.getAllParts());
        addProPartIdAddCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProPartNameAddCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProInvAddCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProPpUAddCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        addProPartIdDelCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProPartNameDelCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProInvDelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProPpUDelCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
       
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
}
