package com.cokerj.collegecompanion.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.security.KeyStore;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String userName;
    private String userPassword;
    private Boolean loggedIn;


    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.loggedIn = false;
    }

    public void setLoggedIn(Boolean loggedIn) {this.loggedIn = loggedIn;}
    public boolean getLoggedIn(){return this.loggedIn;}
    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public boolean testLogin(String userName, String password){
        return this.getUserName().equals(userName) && this.getUserPassword().equals(password);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }

    public String getUserPassword(){
        return userPassword;
    }
}