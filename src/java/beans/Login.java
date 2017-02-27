/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author c0537794
 */
public class Login {
    private String username;
    private String password;
    private boolean loggedIn;

    public Login() {
        username = null;
        password = null;
        loggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public String login() {
        String passhash = DBUtils.hash(password);
        for (User u : new Users().getUsers()) {
            if(username.equals(u.getUserName()) &&
                    passhash.equals(u.getPassHash())) {
                loggedIn = true;
                return "index";
            }
        }
        loggedIn = false;
        return "index";
    }
   
}
