/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kale
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> findPart = FXCollections.observableArrayList();
    private static ObservableList<Product> findProduct = FXCollections.observableArrayList();
    
    public static void addPart(Part part) {
        Inventory.getAllParts().add(part);
    }
    
    public static void addProduct(Product product) {
        allProducts.add(product);
    }
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    public static ObservableList<Part> getAllFoundParts() {
        return findPart;
    }
    
    public static ObservableList<Product> getAllFoundProducts() {
        return findProduct;
    }
    
   public static ObservableList<Part> lookupPartID(int partId) {
       if(!(Inventory.getAllFoundParts().isEmpty()))
           Inventory.getAllFoundParts().clear();
       for(Part part : Inventory.getAllParts()) {
           if(part.getId() == partId)
               Inventory.getAllFoundParts().add(part);
       }
        if(Inventory.getAllFoundParts().isEmpty())
            return Inventory.getAllParts();
        else
            return Inventory.getAllFoundParts();
   }
   
   public static ObservableList<Product> lookupProductID(int productId) {
      if(!(Inventory.getAllFoundProducts().isEmpty()))
           Inventory.getAllFoundProducts().clear();
       for(Product product : Inventory.getAllProducts()) {
           if(product.getId() == productId)
               Inventory.getAllFoundProducts().add(product);
       }
       if(Inventory.getAllFoundProducts().isEmpty())
           return Inventory.getAllProducts();
       else
           return Inventory.getAllFoundProducts();
   }
   
   public static ObservableList<Part> lookupPartName(String partName) {
       if(!(Inventory.getAllFoundParts().isEmpty()))
           Inventory.getAllFoundParts().clear();
       for(Part part : Inventory.getAllParts()) {
           if(part.getName().contains(partName))
               Inventory.getAllFoundParts().add(part);
       }
        if(Inventory.getAllFoundParts().isEmpty())
            return Inventory.getAllParts();
        else
            return Inventory.getAllFoundParts();
       }
   
   public static ObservableList<Product> lookupProductName(String productName) {
       if(!(Inventory.getAllFoundProducts().isEmpty()))
           Inventory.getAllFoundProducts().clear();
       for(Product product : Inventory.getAllProducts()) {
           if(product.getName().contains(productName))
               Inventory.getAllFoundProducts().add(product);
       }
       if(Inventory.getAllFoundProducts().isEmpty())
           return Inventory.getAllProducts();
       else
           return Inventory.getAllFoundProducts();
       }
   
   public static void updatePart(int id, Part selectedPart) {
       int index = -1;
       for(Part part : Inventory.getAllParts()) {
           index++;
           if(part.getId() == id)
               Inventory.getAllParts().set(index, selectedPart);
       }
   }
   
   public static void updateProduct(int id, Product selectedProduct) {
       int index = -1;
       for(Product product : Inventory.getAllProducts()) {
           index++;
           if(product.getId() == id)
               Inventory.getAllProducts().set(index, selectedProduct);
       }
   }
   
   public static void deletePart(Part selectedPart) {
       Inventory.getAllParts().remove(selectedPart);
    }
   
   
   public static void deleteProduct(Product selectedProduct) {
       Inventory.getAllProducts().remove(selectedProduct);
   }
   
}
