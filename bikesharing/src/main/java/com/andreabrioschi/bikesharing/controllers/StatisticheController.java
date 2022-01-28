/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.controllers;

import com.andreabrioschi.bikesharing.database.DbFactory;
import com.andreabrioschi.bikesharing.database.Statistiche;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author andreabrioschi
 */
public class StatisticheController extends Controller{

    @FXML
    private Label biciPiuUsata;

    @FXML
    private Label rastPiuUsata;

    @FXML
    private Label mediaBici;

    @FXML
    private Label mediaBiciElettriche;

    @FXML
    private Label mediaBiciClassiche;

    @FXML
    private Label fasciaOraria;
    
    @FXML
    private Label percentualeDanneggiate;

    @FXML
    private void initialize() {
        Statistiche stats = DbFactory.statistiche();
        biciPiuUsata.setText(stats.biciPiuUsata());
        rastPiuUsata.setText(stats.rastrellieraPiuUsata());
        mediaBici.setText(String.valueOf(stats.mediaBici()));
        mediaBiciElettriche.setText(String.valueOf(stats.mediaBiciElettriche()));
        mediaBiciClassiche.setText(String.valueOf(stats.mediaBiciClassiche()));
        fasciaOraria.setText(stats.fasciaOraria());
        percentualeDanneggiate.setText(String.valueOf(stats.percentualeDanneggiate())+"%");
    }
    
    @FXML
    private void backRouting(ActionEvent event){
        routing("login");
    }

}
