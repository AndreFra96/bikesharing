/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.controllers;

import com.andreabrioschi.bikesharing.App;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author andreabrioschi
 */
public abstract class Controller {
    
    public void routing(String name){
        try {
            App.setRoot(name);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void info(String info){
        Alert a = new Alert(AlertType.INFORMATION,info,ButtonType.OK);
        a.setTitle("INFO");
        Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        a.showAndWait();
    }
    
    public void error(String info){
        Alert a = new Alert(AlertType.ERROR,info,ButtonType.OK);
        a.setTitle("ERROR");
        Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        a.showAndWait();
    }
    
    public boolean confirm(String info){
        Alert a = new Alert(AlertType.CONFIRMATION,info,ButtonType.CANCEL,ButtonType.OK);
        a.setTitle("CONFIRM");
        Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        Optional<ButtonType> result = a.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
}
