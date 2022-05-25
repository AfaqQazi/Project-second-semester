/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

/**
 *
 * @author taimoor
 */
public class Admin {
    
     private static String username;
    private static boolean isLoggedIn = false;

    public static String getUsername() {
        return username;
    }

    public static boolean isIsLoggedIn() {
        return isLoggedIn;
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    public static void setUsername(String username) {
        Admin.username = username;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        Admin.isLoggedIn = isLoggedIn;
    }
    
}
