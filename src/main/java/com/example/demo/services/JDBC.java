package com.example.demo.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    private Connection connection;

    public boolean setConnction(){
        boolean bres = false;
        String url = "";
        try{
            connection = DriverManager.getConnection(url,"","");
            bres = true;
        }
        catch (SQLException e){
            System.out.println("No connection to sever="+e.getMessage());
        }
        return bres;
    }
}
