/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author c0537794
 */
@Named
@ApplicationScoped

public class Users {
    private List<User> users;
    private static User instance;

    public Users() {
        getUsersFromDB();
       // instance = this;   
    }

    private void getUsersFromDB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<User> getUsers() {
        return users;
    }

    public static User getInstance() {
        return instance;
    }
    
    public String getUsernameById(int id) {
       return null; 
    }
    
    public int getUserIdByUsername(String username){
        return -1;
    }
    
    public void addUser(String username, String password) {
        
    }




    
    
}
