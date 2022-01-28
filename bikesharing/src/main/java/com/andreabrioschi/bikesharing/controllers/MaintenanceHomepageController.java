/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *
 * @author andreabrioschi
 */
public class MaintenanceHomepageController extends Controller{
    
    
    @FXML
    private void logout(){
        //Database d = new Database();
        //d.run();
        routing("login");
    }
    
    @FXML
    void gestioneRastrelliereRouting(ActionEvent event){
        routing("rastrelliere");
    }
}
