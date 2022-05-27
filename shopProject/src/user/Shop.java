

package user;
 
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import utils.UtilFuncs;

public class Shop {
    public static void loadItems(DefaultTableModel model) {
        // clear items
        model.setRowCount(0);
        // read  inventory file
        try {
            String[] row;
            File inventoryFile = new File("database/inventory.txt");
            Scanner scan = new Scanner(inventoryFile);
            while(scan.hasNextLine()) {
                row = new String[3];
                String line = scan.nextLine();
                String[] splitLine = line.split(":");
                row[0] = splitLine[0];
                row[1] = splitLine[1];
                row[2] = splitLine[2];
                if (parseInt(String.valueOf(row[1])) != 0) {
                    model.addRow(row);
                } 
            }
        }catch (IOException e) {
            System.out.println("Somethign  went wrong retrieving inventory");
        }

    }
    public static void addToCart(DefaultTableModel model , JTable table , int userSelectedQty , int shopItemQty) {        
        
        String selectedItemName = model.getValueAt(table.getSelectedRow(), 0).toString();
        double selectedItemPrice = Double.parseDouble(model.getValueAt(table.getSelectedRow(), 2).toString()); 
        int row = table.getSelectedRow();
        
        // first update quantity in shopItemTable on client
        table.setValueAt(shopItemQty - userSelectedQty, row, 1);
        // then update the Inventory by copying the updated Shop(client) onto the inventory database file
        updateInventory(model  , table);
        // update the cart
        updateCart(selectedItemName ,userSelectedQty, selectedItemPrice , model , table);
    }
    
    public static void updateInventory(DefaultTableModel model , JTable table) {
        UtilFuncs.emptyAFile("database/inventory.txt");
        
        try {
            FileWriter inventoryFile = new FileWriter("database/inventory.txt" , true);
            for (int i = 0; i < table.getRowCount(); i++) {
                String name = model.getValueAt(i, 0).toString();
                int qty = parseInt(model.getValueAt(i, 1).toString());
                double price = Double.parseDouble(model.getValueAt(i, 2).toString());
                inventoryFile.append(name+":"+qty+":"+price+"\r\n");
            }
            inventoryFile.close();
        } catch(IOException e) {
            System.out.println("Cannot Write to inventory");
        }
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
            myFile.append(name+ ":" + qty + ":" + price + "\r\n");
            myFile.close();
        } catch(IOException e) {
            System.out.println("Cannot write " + name + " to the cart file in database");
        }
    }
    
    private static void updateAnItemInCart(String name, int qty, double price) {
        File cartFile;
        try {
            cartFile = new File("database/cart.txt");
            Scanner scan = new Scanner(cartFile);
            
            int SelectedItemQtyInCart = 0;
            int selectedItemRowInCart = 0;
            int itemUpdateQty = 0;
            int i = 0;
            
            while (scan.hasNextLine()) {
                String[] line = scan.nextLine().split(":");
                if (name.equals(line[0])) {
                    SelectedItemQtyInCart = parseInt(line[1]);
                    selectedItemRowInCart = i;
                }
                i++;
            }
            itemUpdateQty = qty + SelectedItemQtyInCart;
 
            String[][] clientCart = new String[i][3];     
            int z = 0;
            
            
            // update the quantity in clientCart array and then append whole array to cart file
            Scanner scan2 = new Scanner(cartFile);
            while(scan2.hasNextLine()) {
                String[] line = scan2.nextLine().split(":");
                clientCart[z][0] = line[0];
                clientCart[z][2] = line[2];
                if (line[0].equals(name)) {
                    line[1] = String.valueOf(itemUpdateQty);
                }
                clientCart[z][1] = line[1];
                z++;
            }
            
            // delete the cart file
            UtilFuncs.emptyAFile("database/cart.txt");
            
            // append the clientCart Array on it
            try {
                FileWriter cartFileWriter = new FileWriter("database/cart.txt" , true);
                for (int k = 0; k < clientCart.length; k++) {
                    cartFileWriter.append(clientCart[k][0]+":"+clientCart[k][1]+":"+clientCart[k][2]+"\r\n");
                }
                cartFileWriter.close();
            } catch(IOException e) {
                System.out.println("something went wrong updating item quantity in cart file");
            }
        } catch(IOException e) {
            System.out.println("Cannot read from cart");
        }
    }

}


