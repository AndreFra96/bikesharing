/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.controllers;

import com.andreabrioschi.bikesharing.AppSession;
import com.andreabrioschi.bikesharing.database.BiciclettaDao;
import com.andreabrioschi.bikesharing.database.DbFactory;
import com.andreabrioschi.bikesharing.database.MorsaDao;
import com.andreabrioschi.bikesharing.database.RastrellieraDao;
import com.andreabrioschi.bikesharing.models.Bicicletta;
import com.andreabrioschi.bikesharing.models.BiciclettaClassica;
import com.andreabrioschi.bikesharing.models.BiciclettaElettrica;
import com.andreabrioschi.bikesharing.models.Morsa;
import com.andreabrioschi.bikesharing.models.Rastrelliera;
import com.andreabrioschi.bikesharing.models.TipoBicicletta;
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
public class InserimentoBiciclettaController extends Controller {

    private RastrellieraDao rastrellieraDao;

    @FXML
    private ChoiceBox<Rastrelliera> rastrelliera;

    @FXML
    private ChoiceBox<TipoBicicletta> tipoBicicletta;

    @FXML
    private Label errorMessage;

    @FXML
    private Button exit;

    @FXML
    private Button confirm;

    @FXML
    void confirmHandler(ActionEvent event) {
        Rastrelliera r = rastrelliera.getValue();
        TipoBicicletta t = tipoBicicletta.getValue();

        //Se non ho morse disponibili restituisco messaggio di errore
        if (!r.haPosto(t)) {
             error("Nessuna morsa di tipo " + t.getTipoMorsa() + " libera");
            return;
        }
        
        Morsa destinazione = r.ottieniMorsaLibera(t);

        //Aggiungo la bicicletta
        Bicicletta created = DbFactory.bicicletta().add(new Bicicletta(1, t, false));

        //Aggancio la bicicletta alla morsa
        DbFactory.morsa().aggancia(created, destinazione);

        //Ritorno alla lista biciclette
        routing("biciclette");

    }

    @FXML
    void exitHandler(ActionEvent event) {
        routing("biciclette");
    }

    @FXML
    private void initialize() {
        //Inizializzo daos
        rastrellieraDao = new RastrellieraDao();
        //Scarico dati rastrelliere
        List<Rastrelliera> listaRastrelliere = rastrellieraDao.getAll();
        this.rastrelliera.setItems(FXCollections.<Rastrelliera>observableArrayList(listaRastrelliere));
        //Seleziono rastrelliera
        this.rastrelliera.setValue(AppSession.getInstance().getRastrellieraSelezionata());
        //Imposto tipi bicicletta
        TipoBicicletta classica = new BiciclettaClassica();
        TipoBicicletta elettrica = new BiciclettaElettrica(false);
        TipoBicicletta elettricaConSeggiolino = new BiciclettaElettrica(true);
        List<TipoBicicletta> tipiBicicletta = List.of(classica, elettrica, elettricaConSeggiolino);
        this.tipoBicicletta.setItems(FXCollections.<TipoBicicletta>observableArrayList(tipiBicicletta));
        this.tipoBicicletta.setValue(classica);
    }
}
