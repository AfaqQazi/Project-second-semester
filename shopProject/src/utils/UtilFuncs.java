
package utils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/*
    This Class is to make it easy to do file IO tasks that are repeated throughout the programme
*/

public class UtilFuncs {
    // need  to pass file path as parameter. returns number of rows in a txt file.
    public static int getNumberOfRowsInFile(String f) {
        int currentRows = 0;
        try {
            File myFile = new File(f);
            Scanner scanner = new Scanner(myFile);
            while(scanner.hasNextLine()) {
                scanner.nextLine();
                currentRows++;
            }
            return currentRows;
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "error: cannot get number of rows from file: " + f);
            return 0;
        }
    }
    
    // must have name:qty:price format
    public static String[][] getFileRowsContent(String f , int sizeOfArr) {
        String[][] arr = new String[sizeOfArr][3];
        try {
            File myFile = new File(f);
            Scanner scanner = new Scanner(myFile);
            for (int i = 0; scanner.hasNextLine(); i++) {
                String[] line = scanner.nextLine().split(":");
                String name =line[0];
                String qty = line[1];
                String price = line[2];
                arr[i][0] = name;
                arr[i][1] = qty;
                arr[i][2] = price;
            }
            return arr;
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null,"Cannot access file for reading: " + f);
            return arr;
        }
        
    }
    // neeed to pass a file path. empties a file
    public static void emptyAFile(String f) {
        try {
            FileWriter myFile = new FileWriter(f);
            myFile.write("");
            myFile.close();
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null , "Sometghing went wrong writing(emptying) to the file:" + f);
        }   
    }
}
