
package admin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.Scanner;
import javax.swing.JOptionPane;
import utils.UtilFuncs;

public class adminInventory {
    
    
    
    public static void addItem(String name , int qty , double price) {
        boolean addItem = true;
        try{
            Scanner scan = new Scanner(new File("database/inventory.txt"));
            while(scan.hasNextLine()) {
                String[] line = scan.nextLine().split(":");
                if (line[0].trim().toLowerCase().equals(name.trim().toLowerCase())){
                    addItem = false;
                    JOptionPane.showMessageDialog(null, "This item is already in the inventory");
                }
            }
        }catch(IOException e) {
            System.out.println(e);
        }
        if (addItem) {
            String line[] = new String[3];
            line[0] = name;
            line[1] = String.valueOf(qty);
            line[2] = String.valueOf(price);
            UtilFuncs.addALineToAFile("database/inventory.txt" , line);
        }

    }
    public static void deleteItem(String name) {
        int inventoryFileRows = UtilFuncs.getNumberOfRowsInFile("database/inventory.txt");
        String[][] arr = UtilFuncs.getFileRowsContent("database/inventory.txt", inventoryFileRows);
        
        UtilFuncs.emptyAFile("database/inventory.txt");
        
        try {
            FileWriter f = new FileWriter("database/inventory.txt" , true);
            for (int i = 0; i < arr.length; i++) {
                if (!arr[i][0].equals(name))
                    f.append(arr[i][0]+":"+arr[i][1]+":"+arr[i][2]+"\r\n");
            }
            f.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    public static void updateQty(String name , int qty) {
        int inventoryFileRows = UtilFuncs.getNumberOfRowsInFile("database/inventory.txt");
        String[][] arr = UtilFuncs.getFileRowsContent("database/inventory.txt", inventoryFileRows);
        
        UtilFuncs.emptyAFile("database/inventory.txt");
        
        try {
            FileWriter f = new FileWriter("database/inventory.txt" , true);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i][0].equals(name)){
                    arr[i][1] = String.valueOf((parseInt(arr[i][1]) + qty));
                    f.append(arr[i][0]+":"+arr[i][1]+":"+arr[i][2]+"\r\n");
                } else {
                    f.append(arr[i][0]+":"+arr[i][1]+":"+arr[i][2]+"\r\n");
                }  
            }
            f.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    public static void updatePrice(String name, double price){
        int inventoryFileRows = UtilFuncs.getNumberOfRowsInFile("database/inventory.txt");
        String[][] arr = UtilFuncs.getFileRowsContent("database/inventory.txt", inventoryFileRows);
        
        UtilFuncs.emptyAFile("database/inventory.txt");
        
        try {
            FileWriter f = new FileWriter("database/inventory.txt" , true);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i][0].equals(name)){
                    arr[i][2] = String.valueOf((Double.parseDouble(arr[i][2]) + price));
                    f.append(arr[i][0]+":"+arr[i][1]+":"+arr[i][2]+"\r\n");
                } else {
                    f.append(arr[i][0]+":"+arr[i][1]+":"+arr[i][2]+"\r\n");
                }  
            }
            f.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
