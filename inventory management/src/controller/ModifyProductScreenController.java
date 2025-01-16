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
import model.Part;
import model.Product;


/**
 * FXML Controller class
 *
 * @author kale
 */
public class ModifyProductScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField modProIDTxt;

    @FXML
    private TextField modProNameTxt;

    @FXML
    private TextField modProInvTxt;

    @FXML
    private TextField modProPriceTxt;

    @FXML
    private TextField modProMaxTxt;

    @FXML
    private TextField modProMinTxt;

    @FXML
    private TextField modProSearchTxt;

    @FXML
    private TableView<Part> modProAvailablePartsScreenTableView;

    @FXML
    private TableColumn<Part, Integer> modProPartIdAddCol;

    @FXML
    private TableColumn<Part, String> modProPartNameAddCol;

    @FXML
    private TableColumn<Part, Integer> modProInvAddCol;

    @FXML
    private TableColumn<Part, Double> modProPpUAddCol;

    @FXML
    private TableView<Part> modProAssociatedPartsTableView;

    @FXML
    private TableColumn<Part, Integer> modProPartIdDelCol;

    @FXML
    private TableColumn<Part, String> modProPartNameDelCol;

    @FXML
    private TableColumn<Part, Integer> modProInvDelCol;

    @FXML
    private TableColumn<Part, Integer> modProPpUDelCol;
    
    private ObservableList<Part> addPart = FXCollections.observableArrayList();
    
    private void setDeleteTable() {
        modProAssociatedPartsTableView.setItems(addPart);
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
    void onActionModProAdd(ActionEvent event) {
        Part part = modProAvailablePartsScreenTableView.getSelectionModel().getSelectedItem();
        addPart.add(part);
        setDeleteTable();
    }

    @FXML
    void onActionModProDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Selected part will be deleted. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
            addPart.remove(modProAssociatedPartsTableView.getSelectionModel().getSelectedItem());
    }
    
    public void sendProduct(Product product) {
        modProIDTxt.setText(String.valueOf(product.getId()));
        modProNameTxt.setText(product.getName());
        modProInvTxt.setText(String.valueOf(product.getStock()));
        modProPriceTxt.setText(String.valueOf(product.getPrice()));
        modProMaxTxt.setText(String.valueOf(product.getMax()));
        modProMinTxt.setText(String.valueOf(product.getMin()));
        addPart.addAll(product.getAllAssociatedParts());   
    }

    @FXML
    void onActionModProSave(ActionEvent event) throws IOException {
          
        int id = Integer.parseInt(modProIDTxt.getText());
        String name = modProNameTxt.getText();
        double price = Double.parseDouble(modProPriceTxt.getText());
        int stock = Integer.parseInt(modProInvTxt.getText());
        int min = Integer.parseInt(modProMinTxt.getText());
        int max = Integer.parseInt(modProMaxTxt.getText());
        
        Product modProduct = new Product();
        modProduct.setId(id);
        modProduct.setName(name);
        modProduct.setPrice(price);
        modProduct.setStock(stock);
        modProduct.setMin(min);
        modProduct.setMax(max);
            
        for(Part part : addPart)
            modProduct.addAssociatedPart(part);
            
        try {
            if(modProduct.validation() == true) {
               Inventory.updateProduct(id, modProduct);
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
    void onActionModProSearch(ActionEvent event) {
        try {
            modProAvailablePartsScreenTableView.setItems(Inventory.lookupPartID(Integer.parseInt(modProSearchTxt.getText())));
        } catch(NumberFormatException e) {
            modProAvailablePartsScreenTableView.setItems(Inventory.lookupPartName(modProSearchTxt.getText()));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modProIDTxt.setDisable(true);
        setDeleteTable();
       
        modProAvailablePartsScreenTableView.setItems(Inventory.getAllParts());
        modProPartIdAddCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProPartNameAddCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProInvAddCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProPpUAddCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        modProPartIdDelCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProPartNameDelCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProInvDelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProPpUDelCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
}
