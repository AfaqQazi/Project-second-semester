
package user;
import utils.UtilFuncs;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Cart {
    public static void loadItems(DefaultTableModel model) {
       // clear items
        model.setRowCount(0);
        // read  Cart file
        try {
            String[] row;
            File cartFile = new File("database/cart.txt");
            Scanner scan = new Scanner(cartFile);
            while(scan.hasNextLine()) {
                row = new String[3];
                String line = scan.nextLine();
                String[] splitLine = line.split(":");
                row[0] = splitLine[0];
                row[1] = splitLine[1];
                double itemsXQty = Double.parseDouble(splitLine[2]) * parseInt(splitLine[1]);
                row[2] = String.valueOf(itemsXQty);
                if (parseInt(String.valueOf(row[1])) != 0) {
                    model.addRow(row);
                } 
            }
        }catch (IOException e) {
            System.out.println("Somethign  went wrong retrieving Cart");
        }
    }
    public static double getTotalPrice(DefaultTableModel model) {
        double totalPrice = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            double iPrice = Double.parseDouble(String.valueOf(model.getValueAt(i , 2)));
            totalPrice += (iPrice);
        }
        return totalPrice;
    }
    
    public static void clearCart(DefaultTableModel model) {
        // put items back into inventory
        int currentRowsInCart = UtilFuncs.getNumberOfRowsInFile("database/cart.txt");
        if (currentRowsInCart > 0 ) {
            putItemsBack(model , currentRowsInCart);
        }
        
        
        // clear the client cart tabel and cart file
        UtilFuncs.emptyAFile("database/cart.txt");
        model.setRowCount(0);
    }
    
    private static void putItemsBack(DefaultTableModel model , int rowsInCart) {
        String[][] cartClientArr = UtilFuncs.getFileRowsContent("database/cart.txt" , rowsInCart);
        int inventoryFileRows = UtilFuncs.getNumberOfRowsInFile("database/inventory.txt");
        String[][] inventoryArr = UtilFuncs.getFileRowsContent("database/inventory.txt", inventoryFileRows);
        
        for (int k = 0; k < cartClientArr.length; k++) {
            for (int j = 0; j < inventoryArr.length; j++) {
                if (inventoryArr[j][0].equals(cartClientArr[k][0])) {
                    int inventoryQty = parseInt(inventoryArr[j][1]);
                    int cartClientQty = parseInt(cartClientArr[k][1]);
                    int totalQty = inventoryQty + cartClientQty;
                    inventoryArr[j][1] = String.valueOf(totalQty);
                }            
            }
        }

        // empty inventoryAr
        UtilFuncs.emptyAFile("database/inventory.txt");
        // write inventoryArr to the inventory
        try {
            FileWriter inventoryFile = new FileWriter("database/inventory.txt" , true);
            for (int i = 0; i < inventoryArr.length; i++) {
                inventoryFile.append(inventoryArr[i][0]+":"+inventoryArr[i][1]+":"+inventoryArr[i][2]+"\r\n");
            }
            inventoryFile.close();
        } catch(IOException e) {
            System.out.println("Sometghing went wrong writing to the inventory file");
        }
        
    }
    
    public static boolean checkout(String totalPriceText) {
        boolean clearCart = false;
        // get total price
        double totalPrice = Double.parseDouble(totalPriceText.split(":")[1].trim());
        double input = -1;
        try {
          input = Double.parseDouble(JOptionPane.showInputDialog("Enter your bank balance:"));
          if (input >= 0) {
              if (input < totalPrice) {
                JOptionPane.showMessageDialog(null, "You Don't have enough balance to purchase these items");
              } else {
               JOptionPane.showMessageDialog(null, "Items Purchased Successfully");
               clearCart = true;
              }
          } else {
              JOptionPane.showMessageDialog(null, "Please enter a positive numeric value");
          }
        } catch(NullPointerException e) {
            System.out.println("do nothing");
        }catch(NumberFormatException  e) {
            JOptionPane.showMessageDialog(null, "Please enter a positive numeric value");
        } 
        return clearCart;
    }
    
    public static void clearCartOnly(DefaultTableModel model) {
        UtilFuncs.emptyAFile("database/cart.txt");
        
        model.setRowCount(0);
    }
}
