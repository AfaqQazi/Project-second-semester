/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 *
 * @author taimoor
 */
public class Admin {
    private static  String username;
    private static  String password;
    private static boolean isLoggedIn=false;

    
    
    public static void setUsername(String username) {
        Admin.username = username;
    }

    public static void setPassword(String password) {
        Admin.password = password;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        Admin.isLoggedIn = isLoggedIn;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static boolean isIsLoggedIn() {
        return isLoggedIn;
    }

   
    
    
    
   
}
