/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.controllers;

import com.andreabrioschi.bikesharing.AppSession;
import com.andreabrioschi.bikesharing.database.DbFactory;
import com.andreabrioschi.bikesharing.models.Cliente;
import com.andreabrioschi.bikesharing.models.Utente;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
/**
 *
 * @author andreabrioschi
 */
public class RegistrationController extends Controller{
    
            
    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField mail;

    @FXML
    private PasswordField password;
    
    @FXML
    private DatePicker birthDate;
    
    @FXML
    private CheckBox studente;

    @FXML
    private Label errorMessage;

    @FXML
    private Button cancel;
    
    @FXML
    void cancelRouting(ActionEvent event){
        routing("login");
    }

    @FXML
    void registrationHandler(ActionEvent event){
        
        //Colleziono i dati inseriti
        String nome = name.getText();
        String cognome = surname.getText();
        String email = mail.getText();
        LocalDate data = birthDate.getValue();
        String pwd = password.getText();
        Boolean stud = studente.isSelected();
        
        System.out.printf("Registrazione:\nnome: %s,\ncognome: %s,\nmail: %s,\npassword: %s,\ndata di nascita: %s,\n",nome,cognome,email,pwd,data.toString());
        
        //Creo il nuovo cliente
        Utente c = new Cliente(1,pwd,email,nome,cognome,stud);
        
        //Aggiungo il cliente al database
        c = DbFactory.utente().add(c);
        
        //Imposto l'utente all'interno della sessione
        AppSession.getInstance().login(c);
        
        //Se è stato impostato correttamente l'utente all'interno della sessione
        Boolean registrationOK = AppSession.getInstance().loginOK();
        
        if(registrationOK){
            routing("payment");
        }else{
            error("errore registrazione");
        }

    }
}
