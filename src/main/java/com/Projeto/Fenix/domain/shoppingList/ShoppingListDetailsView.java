package com.Projeto.Fenix.domain.shoppingList;

public class ShoppingListDetailsView {

    String itemName;

    double itemQuantity;

    String itemImage;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public ShoppingListDetailsView() {
    }

    public ShoppingListDetailsView(String itemName, double itemQuantity, String itemImage) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemImage = itemImage;
    }
}
