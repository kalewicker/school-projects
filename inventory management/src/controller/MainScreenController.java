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
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author kale
 */
public class MainScreenController implements Initializable {
    Stage stage;
    Parent scene;
   
  @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TableColumn<Product, Integer> productID;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productsInventoryLevel;

    @FXML
    private TableColumn<Product, Double> productsCostPerUnit;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partsInventoryLevel;

    @FXML
    private TableColumn<Part, Double> partsCostPerUnit;

    @FXML
    private TextField productsSearchTxt;

    @FXML
    private TextField searchPartsTxt;
    
    public Part selectPart(int id) {
        for(Part part : Inventory.getAllParts()) {
            if(part.getId() == id)
                return part;
        }
        return null;
    }
    
    public Part selectFoundPart(int id) {
        for(Part part : Inventory.getAllFoundParts()) {
            if(part.getId() == id)
                return part;
        }
        return null;
    }
    
     public static Product selectProduct(int id) {
        for(Product product : Inventory.getAllProducts()) {
            if(product.getId() == id)
                return product;
        }
        return null;
    }
     
      public Product selectFoundProduct(int id) {
        for(Product product : Inventory.getAllFoundProducts()) {
            if(product.getId() == id)
                return product;
        }
        return null;
    }

    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Selected part will be deleted. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
            Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
        
        
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Selected part will be deleted. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
            Inventory.deleteProduct(productsTableView.getSelectionModel().getSelectedItem());

    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPartScreen.fxml"));
        loader.load();
        ModifyPartScreenController ADMController = loader.getController();
        ADMController.sendPart(partsTableView.getSelectionModel().getSelectedItem());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProductScreen.fxml"));
        loader.load();
        ModifyProductScreenController ADMController = loader.getController();
        ADMController.sendProduct(productsTableView.getSelectionModel().getSelectedItem());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionPartsSearch(ActionEvent event) {
        try {
            partsTableView.setItems(Inventory.lookupPartID(Integer.parseInt(searchPartsTxt.getText())));
        } catch(NumberFormatException e) {
            partsTableView.setItems(Inventory.lookupPartName(searchPartsTxt.getText()));
        }
    }

    @FXML
    void onActionProductsSearch(ActionEvent event) {
        try {
            productsTableView.setItems(Inventory.lookupProductID(Integer.parseInt(productsSearchTxt.getText())));
        } catch(NumberFormatException e) {
            productsTableView.setItems(Inventory.lookupProductName(productsSearchTxt.getText()));
        }
    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partsTableView.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productsTableView.setItems(Inventory.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
}
