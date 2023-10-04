package com.testcontainer.example.testcontainermysteries;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionProvider {
    private final String url;
    private final String userName;
    private final String password;
    public DBConnectionProvider(String url,String userName,String password){
        this.url=url;
        this.userName=userName;
        this.password=password;
    }
    Connection getConnection()
    {
        try{
           return DriverManager.getConnection(url,userName,password);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

}
