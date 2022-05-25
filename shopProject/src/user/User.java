
package user;

public class User {
    private static String username;
    private static boolean isLoggedIn = false;

    public static String getUsername() {
        return username;
    }

    public static boolean isIsLoggedIn() {
        return isLoggedIn;
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    public static void setUsername(String username) {
        User.username = username;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        User.isLoggedIn = isLoggedIn;
    }
    
    
}