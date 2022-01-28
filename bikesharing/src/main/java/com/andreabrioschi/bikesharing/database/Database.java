/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.database;


import com.andreabrioschi.bikesharing.models.Bicicletta;
import com.andreabrioschi.bikesharing.models.BiciclettaClassica;
import com.andreabrioschi.bikesharing.models.BiciclettaElettrica;
import com.andreabrioschi.bikesharing.models.Rastrelliera;
import com.andreabrioschi.bikesharing.models.TipoMorsa;
import com.andreabrioschi.bikesharing.models.Morsa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author andreabrioschi
 */
public class Database {
    
    private Database(){}
    
    private static Database _instance = null;
    
    public static Database getInstance(){
        if(_instance == null)
             _instance = new Database();
        return _instance;
    }
    
    public ResultSet executeQuery(String query) throws SQLException{
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(query);
        return result;
    }
    
    public Connection getConnection() throws SQLException{
        String connString = "jdbc:mysql://root@localhost:3306/bikesharing";
        return DriverManager.getConnection(connString);
    }
    
    static public ObservableList<Rastrelliera> mockRastrelliere(){
        Morsa m = new Morsa(1,TipoMorsa.ELETTRICA);
        Morsa m1 = new Morsa(2,TipoMorsa.ELETTRICA);        
        Morsa m2 = new Morsa(2,TipoMorsa.CLASSICA);

        Bicicletta bici = new Bicicletta(1,new BiciclettaElettrica(true),false);
        Bicicletta bici1 = new Bicicletta(2,new BiciclettaElettrica(false),false);
        Bicicletta bici2 = new Bicicletta(3,new BiciclettaClassica(),false);

        m.aggancia(bici);
        m1.aggancia(bici1);
        m2.aggancia(bici2);

        Rastrelliera r = new Rastrelliera(1,100,100,"PORTA VITTORIA",List.of(m));
        Rastrelliera r1 = new Rastrelliera(2,231323,4323213,"PIAZZALE LODI",List.of(m,m1));
        Rastrelliera r2 = new Rastrelliera(3,231323,4323213,"VIA RIPAMONTI",List.of(m,m1,m2));
        
        return FXCollections.<Rastrelliera>observableArrayList(List.of(r,r1,r2));
    }
}