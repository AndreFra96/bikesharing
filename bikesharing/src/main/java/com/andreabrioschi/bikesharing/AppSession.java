/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing;

import com.andreabrioschi.bikesharing.models.Bicicletta;
import com.andreabrioschi.bikesharing.models.Rastrelliera;
import com.andreabrioschi.bikesharing.models.Utente;

/**
 *
 * @author andreabrioschi
 */
public class AppSession {
    private Utente utenteLogin = null;
    private Rastrelliera rastrellieraSelezionata = null;
    private Bicicletta biciclettaSelezionata = null;
    
    private static AppSession _instance = null;
    
    private AppSession(){}
    
    public static AppSession getInstance(){
        if(_instance == null)
           _instance = new AppSession();
        return _instance;
    }
    
    public Boolean rastrellieraOK(){
        return this.rastrellieraSelezionata != null;
    }
    
    public Rastrelliera getRastrellieraSelezionata(){
        return this.rastrellieraSelezionata;
    }
    
    public void setRastrellieraSelezionata(Rastrelliera r){
        this.rastrellieraSelezionata = r;
    }
    
    public Boolean biciclettaOK(){
        return this.biciclettaSelezionata != null;
    }
    
    public Bicicletta getBiciclettaSelezionata(){
        return this.biciclettaSelezionata;
    }
    
    public void setBiciclettaSelezionata(Bicicletta b){
        this.biciclettaSelezionata = b;
    }
    
    public Boolean loginOK(){
        return this.utenteLogin != null;
    }
    
    public void logout(){
        this.utenteLogin = null;
    }
    
    public void login(Utente u){
       this.utenteLogin = u;
    }
    
    public Utente getUtenteLogin(){
        return this.utenteLogin;
    }
    
}
