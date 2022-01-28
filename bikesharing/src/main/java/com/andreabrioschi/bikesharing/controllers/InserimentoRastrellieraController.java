/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.controllers;

import com.andreabrioschi.bikesharing.database.DbFactory;
import com.andreabrioschi.bikesharing.database.MorsaDao;
import com.andreabrioschi.bikesharing.database.RastrellieraDao;
import com.andreabrioschi.bikesharing.models.Morsa;
import com.andreabrioschi.bikesharing.models.Rastrelliera;
import com.andreabrioschi.bikesharing.models.TipoMorsa;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author andreabrioschi
 */
public class InserimentoRastrellieraController extends Controller {

    @FXML
    private TextField nome;

    @FXML
    private TextField latitudine;

    @FXML
    private TextField longitudine;

    @FXML
    private TextField elettriche;

    @FXML
    private TextField classiche;

    @FXML
    private Button exit;

    @FXML
    private Label errorMessage;

    @FXML
    private Button confirm;

    @FXML
    void exitHandler(ActionEvent event) {
        routing("rastrelliere");
    }

    @FXML
    void confirmHandler(ActionEvent event) {
        //ottengo input utente
        double latitudineRastrelliera = 0.0;
        double longitudineRastrelliera = 0.0;

        try {
            latitudineRastrelliera = Double.parseDouble(latitudine.getText());
            longitudineRastrelliera = Double.parseDouble(longitudine.getText());
        } catch (NumberFormatException e) {
            error("Parametri non validi, riprovare");
        }

        int morseElettricheRastrelliera = Integer.parseInt(elettriche.getText());
        int morseClassicheRastrelliera = Integer.parseInt(classiche.getText());
        String nomeRastrelliera = nome.getText();

        //Aggiungo la rastrelliera
        Rastrelliera r = new Rastrelliera(1, latitudineRastrelliera, longitudineRastrelliera, nomeRastrelliera);
        r = DbFactory.rastrelliera().add(r);

        //Aggiungo le morse elettriche
        Morsa m = new Morsa(1, TipoMorsa.ELETTRICA);
        for (int i = 0; i < morseElettricheRastrelliera; i++) {
            DbFactory.morsa().add(m, r);
        }

        //Aggiungo le morse classiche
        m = new Morsa(1, TipoMorsa.CLASSICA);
        for (int i = 0; i < morseClassicheRastrelliera; i++) {
            DbFactory.morsa().add(m, r);
        }

        //Ritorno alla home rastrelliere
        routing("rastrelliere");

    }
}
