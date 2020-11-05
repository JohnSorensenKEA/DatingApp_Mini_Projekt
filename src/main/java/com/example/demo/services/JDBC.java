package com.example.demo.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    private Connection connection;

    public boolean setConnction(){
        boolean bres = false;
        String url = "jdbc:mysql://94.18.243.144:3306/clm1?serverTimezone=UTC";
        try{
            connection = DriverManager.getConnection(url,"clm_server","clm_server");
            bres = true;
        }
        catch (SQLException e){
            System.out.println("No connection to sever="+e.getMessage());
        }
        return bres;
    }
}
