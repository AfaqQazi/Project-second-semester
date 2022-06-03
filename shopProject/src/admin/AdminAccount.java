/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

/**
 *
 * @author taimoor
 */
public class AdminAccount {
     public static void RegisterAdmin(String username ,String password)
    { 
        boolean usernamefound=false;
        try{
           Scanner scan =new Scanner (new File("database/admins.txt"));
           while(scan.hasNextLine())
           {    
              
               String[] line=scan.nextLine().split(":");
               System.out.println("GANDU " + line[0] + " " + line[1]);
//               
               String username1=line[0];
               String password2=line[1];
               if(username1.equals(username))
               {
                   usernamefound=true;
                   break;
               }
           }
        }catch(IOException e)
        {
            System.out.println("Error !"+e);
        }
        
        if(usernamefound)
           {
             JOptionPane.showMessageDialog(null, "User name already exist");
           } else {
           try{
            FileWriter write=new FileWriter("database/admins.txt",true);
            write.append(username+":"+password+"\r\n");
            write.close();
            
            
            }
            catch(IOException e){
                System.out.println("Error ! "+e);

            }
            }
        
       
     
}
      public static void LoginButton(String username,String password)
        {
            try
            {
                boolean bothFound = false;
                Scanner scan=new Scanner(new File("database/admins.txt"));
                
                while(scan.hasNextLine())
                {
                 String[] line= scan.nextLine().split(":");
                 
                 String username1=line[0];
                 String password1=line[1];
                 if(username1.equals(username)&&password1.equals(password))
                 {
                     bothFound= true;
                 }
               }
                
                if(!bothFound)
                {
                    JOptionPane.showMessageDialog(null,"Noob! enter correct password");
                } else {
                    
                    Admin.setUsername(username);
                    Admin.setPassword(password);
                    Admin.setIsLoggedIn(true);
                }
            }
            catch(IOException e)
            {
                System.out.println("Error !"+e);
            }
        }
      
      public static void show(JPanel AdminAccountform,JButton AdminAccountLogoutBtn)
      {
          if(Admin.isIsLoggedIn()==true)
          {
              AdminAccountform.setVisible(false);
              AdminAccountLogoutBtn.setVisible(true);
          }
          else{
              AdminAccountform.setVisible(true);
              AdminAccountLogoutBtn.setVisible(false);
          }
      }
}
