package model;

/**
 * Author: Christopher Carter
 * Email: ccar491@wgu.edu
 * Student ID: 001356206
 */

import javafx.collections.*;

public class Inventory {
    private static int partID = 0;
    private static int productID = 0;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    public static void addPart (Part addNewPart) {
        allParts.add(addNewPart);
    }

    public static void addProduct(Product addNewProduct) {
        allProducts.add(addNewProduct);
    }

    public static int getNewPartID() {
        return ++partID;
    }

    public static int getNewProductID(){
        return ++productID;
    }

    // Search part by its Part ID
    public static Part partQuery(int partID){
        Part foundPart = null;

        for (Part part : allParts) {
            if (part.getId() == partID) {
                foundPart = part;
            }
        }
        return foundPart;
    }

    // Search part by its Part Name / Description
    public static ObservableList<Part> partQuery(String partName) {
        ObservableList<Part> foundPart = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                foundPart.add(part);
            }
        }
        return foundPart;
    }

    // Modifies part selected that is currently in the list of parts
    public static void modifyPart (int index, Part partSelected) {
        allParts.set(index, partSelected);
    }

    // Removes part from inventory management system
    public static boolean removePart (Part partSelected) {
        if (allParts.contains(partSelected)) {
            allParts.remove(partSelected);
            return true;
        }
        else {
            return false;
        }
    }

    // Search part by its Product ID
    public static Product productQuery(int productID){
        Product foundProduct = null;

        for (Product product : allProducts) {
            if (product.getId() == productID) {
                foundProduct = product;
            }
        }
        return foundProduct;
    }

    // Search part by its Product Name / Description
    public static ObservableList<Product> productQuery(String productName) {
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                foundProduct.add(product);
            }
        }
        return foundProduct;
    }

    // Modifies product selected that is currently in the list of products
    public static void modifyProduct (int index, Product productSelected) {
        allProducts.set(index, productSelected);
    }

    // Removes product from inventory management system
    public static boolean removeProduct (Product productSelected) {
        if (allProducts.contains(productSelected)) {
            allProducts.remove(productSelected);
            return true;
        }
        else {
            return false;
        }
    }
}
