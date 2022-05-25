
package user;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JLabel;
import java.io.FileWriter;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Account {
    
    public static void clearErrors(JLabel label1 , JLabel label2 , JLabel label3 , JLabel registeredMsg) {
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        registeredMsg.setVisible(false);
    }
    public static void show(JPanel userForm , JButton userLogoutBtn) {
        if (User.isIsLoggedIn() == true) {
            userForm.setVisible(false);
            userLogoutBtn.setVisible(true);
        } else {
            userLogoutBtn.setVisible(false);
            userForm.setVisible(true);
        }
    }
    
      // e1 is "empty fields" error and e2 is "already reigstered" error and they are JLabels
    public static void registerUser(String username , String password , JLabel e1 ,JLabel e2 , JLabel registeredMsg) {
        boolean cont = checkRegistrationCreds(username , password , e1 , e2);
        if (cont == true) { // if Fields are not empty and user is not already registered
            try {
                FileWriter usersFile = new FileWriter("database/users.txt" , true);
                usersFile.append(username.trim() + ":" + password.trim() + "\r\n");
                usersFile.close();
                registeredMsg.setVisible(true);
            } catch (IOException e) {
                System.out.println("something went wrong registring user to database: " + e);
            }
        }
    }
    private static boolean checkRegistrationCreds(String username , String password,JLabel e1 ,JLabel e2) {
        if (username.isEmpty() || password.isEmpty()) {
            // make the empty error label visible
            e1.setVisible(true);
            return false;
        }
        
        // check if user is already registered
        try {
            File usersFile = new File("database/users.txt");
            Scanner sc = new Scanner(usersFile);
            while (sc.hasNextLine()) {
                String dbUsername = "";
                String line = sc.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ':') break;
                    dbUsername += line.charAt(i);
                }
                username = username.trim();
                if (username.equals(dbUsername)) {
                    // make the already registered error label visible
                    e2.setVisible(true);
                    return false;
                }
            }
        } catch(IOException e) {
            System.out.println("Something went wrong during fetchin users from database : " + e);
        }
        return true;
    }
    
    public static void loginUser(String username , String password , JLabel e1 , JLabel e2) {
        boolean cont = checkLoginCreds(username , password,e1,e2);
        if (cont) {
         // create a user object and set isLoggedIn to true.
          User.setUsername(username);
          User.setIsLoggedIn(true);
        }
    }
    private static boolean checkLoginCreds(String username , String password , JLabel e1 , JLabel e2) {
        if (username.isEmpty() || password.isEmpty()) {
            // make the empty error label visible
            e1.setVisible(true);
            return false;
        }
        
        // check if creds are valid
        boolean userExists = false;
        try {
            File usersFile = new File("database/users.txt");
            Scanner sc = new Scanner(usersFile);
            boolean copyPass;
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                copyPass = false;
                String dbUsername = "";
                String dbPassword = "";
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ':') {
                         copyPass = true;
                         continue;
                    }
                    if (copyPass == false) {
                        dbUsername += line.charAt(i);
                    } else {
                        dbPassword += line.charAt(i);
                    }
                }
                if (username.equals(dbUsername) && password.equals(dbPassword)) {
                    System.out.println("creating user object");
                    return true;
                }
            }
            // if user not found 
            if (userExists == false) {
                e2.setVisible(true);
                return false;
            }
        } catch(IOException e) {
            System.out.println("Error: " + e);
        }
        
        return true;
    }
    public static void logoutUser(){
        User.setIsLoggedIn(false);
    }
}
