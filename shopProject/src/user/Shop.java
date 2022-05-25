

package user;
 
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Shop {
    public static void loadItems(DefaultTableModel model) {
        // clear items
        model.setRowCount(0);
        // read  inventory file
        try {
            int numOfItems = 0;
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
                model.addRow(row);
            }
        }catch (IOException e) {
            System.out.println("Somethign  went wrong retrieving inventory");
        }

    }
    

}


