
package user;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Cart {
    
    public static void addToCart(DefaultTableModel model , JTable table , int userSelectedQty , int shopItemQty) {        
        
        FileWriter cartFile;
        String selectedItemName = model.getValueAt(table.getSelectedRow(), 0).toString();
        double selectedItemPrice = Double.parseDouble(model.getValueAt(table.getSelectedRow(), 2).toString()); 
        int row = table.getSelectedRow();
        
        // first update quantity in shopItemTable on client
        table.setValueAt(shopItemQty - userSelectedQty, row, 1);
        
        // then update the Inventory by copying the updated Shop(client) onto the inventory database file
        updateInventory();
        
        // update the cart
        updateCart(selectedItemName ,userSelectedQty, selectedItemPrice , model , table);
        
        // 
    }
    
    public static void updateInventory() {
    
    }
    
    public static void updateCart(String name , int qty , double price , DefaultTableModel model , JTable table) {
        File cartFile;
        boolean itemAlreadyExists = false;
        
        try {
           cartFile = new File("database/cart.txt");
           Scanner scan = new Scanner(cartFile);
           // check if item alredy exists in the cart
           while (scan.hasNextLine()) {
               String[] line = scan.nextLine().split(":");
               String currentItemName = line[0];
               if (currentItemName.equals(name)) {
                   itemAlreadyExists = true;
               }
           }
           
            // update item
            if (itemAlreadyExists) {
                updateAnItemInCart(name,qty,price);
            }
            // or append item
            else {
                writeAnItemToCart(name,qty,price);
            }
           
        } catch(IOException e) {
            System.out.println("Something went wrong updating cart items"); 
        }
    }
    
    private static void writeAnItemToCart(String name, int qty, double price) {
        try {
            System.out.println("Writing " + name);
            FileWriter myFile = new FileWriter("database/cart.txt" , true);
            myFile.append(name+":" + qty + ":" + price +"\r\n");
            myFile.close();
        } catch(IOException e) {
            System.out.println("Cannot write " + name + " to the cart file in database");
        }
    }
    
    private static void updateAnItemInCart(String name, int qty, double price) {
        // get the item row in cart file database
        // get it's quantity
        // add the selected Quantity to the cart quantity variable
        // delete the row
        // add it back with update quantity
    }
    
}
