/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.controllers;


import com.andreabrioschi.bikesharing.AppSession;
import com.andreabrioschi.bikesharing.database.DbFactory;
import com.andreabrioschi.bikesharing.models.Bicicletta;
import com.andreabrioschi.bikesharing.models.Morsa;
import com.andreabrioschi.bikesharing.models.Rastrelliera;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

/**
 *
 * @author andreabrioschi
 */
public class SpostamentoBiciclettaController extends Controller{

    @FXML
    private ChoiceBox<Bicicletta> bicicletta;

    @FXML
    private ChoiceBox<Rastrelliera> rastrelliera;

    @FXML
    private Label errorMessage;

    @FXML
    private Button exit;

    @FXML
    private Button confirm;
    
    @FXML
    private void initialize(){
        AppSession session = AppSession.getInstance();
        List<Rastrelliera> rastrelliere = DbFactory.rastrelliera().getAll();
        List<Bicicletta> biciclette = DbFactory.bicicletta().getAll();
        bicicletta.setItems(FXCollections.<Bicicletta>observableArrayList(biciclette));
        rastrelliera.setItems(FXCollections.<Rastrelliera>observableArrayList(rastrelliere));
        bicicletta.setValue(session.getBiciclettaSelezionata());
        rastrelliera.setValue(session.getRastrellieraSelezionata());
    }

    @FXML
    void confirmHandler(ActionEvent event){
        Bicicletta b = bicicletta.getValue();
        Rastrelliera r = rastrelliera.getValue();
        //controllo se la rastrelliera ha morse libere compatibili
        if(!r.haPosto(b.getTipoBicicletta())){
            error("La rastrelliera non ha morse vuote compatibili");
            return;
        }
        //ottengo una morsa compatibile
        Morsa m = r.ottieniMorsaLibera(b.getTipoBicicletta());

        //se ho trovato una morsa compatibile sgancio la bicicletta dalla morsa precedente e la aggancio alla nuova morsa
        DbFactory.morsa().sgancia(b);
        DbFactory.morsa().aggancia(b, m);
        
        routing("biciclette");
    }

    @FXML
    void exitHandler(ActionEvent event){
        routing("biciclette");
    }

}
