/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progettorubricaturingmod;

/**
 *
 * @author simdr
 */
public class Utente {

    private String username;
    private int hashPassword;

    public Utente(String username, int hashPassword) {
        this.username = username;
        this.hashPassword = hashPassword;
    }

    public String getUsername() {
        return username;
    }

    public int getHashPassword() {
        return hashPassword;
    }
    
    

}
