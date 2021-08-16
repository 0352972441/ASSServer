package com.example.androidserver;

import com.example.androidserver.models.User;

// Singleton
public class Account {
    private User user;
    private static Account account = null;
    private Account(){}

    public void setAccount(User user){
        this.user = user;
    }

    public User getAccount(){
        return user;
    }

    public static Account getAccountInstance(){
        if(account == null){
            account = new Account();
        }
        return account;
    }
}
