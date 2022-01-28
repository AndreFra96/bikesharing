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
import com.andreabrioschi.bikesharing.models.TipoMorsa;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 *
 * @author andreabrioschi
 */
public class BicicletteController extends Controller {

    @FXML
    private ChoiceBox<Rastrelliera> selezioneRastrelliera;

    @FXML
    private TableView<Bicicletta> tabellaBiciclette;

    @FXML
    private TableColumn<Bicicletta, Integer> id;

    @FXML
    private TableColumn<Bicicletta, String> tipo;

    @FXML
    private TableColumn<Bicicletta, String> azioni;

    @FXML
    private TableColumn<Bicicletta, Boolean> danneggiata;

    @FXML
    private Label descrizioneMorseElettriche;

    @FXML
    private Label descrizioneMorseClassiche;

    @FXML
    private Button exit;

    @FXML
    private Button nuovaBicicletta;

    @FXML
    void exitRouting(ActionEvent event) {
        routing("rastrelliere");
    }

    @FXML
    private void initialize() {
        AppSession session = AppSession.getInstance();

        //Configuro colonne tabella
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        azioni.setCellFactory(actionCellCallback());
        tipo.setCellFactory(typeCellCallback());
        danneggiata.setCellValueFactory(new PropertyValueFactory<>("danneggiata"));
        //Carico rastrelliere
        List<Rastrelliera> rastrelliere = DbFactory.rastrelliera().getAll();
        selezioneRastrelliera.setItems(FXCollections.<Rastrelliera>observableArrayList(rastrelliere));

        //Controllo se c'è una rastrelliera selezionata
        if (session.rastrellieraOK()) {
            Rastrelliera selected = session.getRastrellieraSelezionata();
            for (Rastrelliera ras : rastrelliere) {
                if (ras.equals(selected)) {
                    selezioneRastrelliera.setValue(ras);
                }
            }
        }
    }

    @FXML
    void handleNuovaBicicletta(ActionEvent event) {
        routing("inserimentoBicicletta");
    }

    @FXML
    void handleSort(ActionEvent event) {

    }

    @FXML
    void handleCambiaRastrelliera(ActionEvent event) {

        //carico le morse della rastrelliera selezionata
        caricaBicicletteRastrelliera();

        //salvo preferenza
        AppSession.getInstance().setRastrellieraSelezionata(selezioneRastrelliera.getValue());

        //aggiorno descrizione morse
        aggiornaDescrizioniDisponibilita();
    }

    private void caricaBicicletteRastrelliera() {
        List<Morsa> morse = selezioneRastrelliera.getValue().getMorse();
        List<Bicicletta> bicicletteRastrelliera = new ArrayList<>();
        for (Morsa m : morse) {
            if (!m.vuota()) {
                bicicletteRastrelliera.add(m.getBicicletta());
            }
        }
        ObservableList<Bicicletta> data = FXCollections.<Bicicletta>observableArrayList(bicicletteRastrelliera);
        tabellaBiciclette.getItems().clear();
        tabellaBiciclette.setItems(data);
    }

    private void aggiornaDescrizioniDisponibilita() {

        List<Morsa> morse = selezioneRastrelliera.getValue().getMorse();
        int totElettriche = 0;
        int totClassiche = 0;
        int vuoteElettriche = 0;
        int vuoteClassiche = 0;
        for (Morsa m : morse) {
            if (m.getTipoMorsa() == TipoMorsa.ELETTRICA) {
                totElettriche++;
                if (m.vuota()) {
                    vuoteElettriche++;
                }
            } else if (m.getTipoMorsa() == TipoMorsa.CLASSICA) {
                totClassiche++;
                if (m.vuota()) {
                    vuoteClassiche++;
                }
            }
        }
        descrizioneMorseClassiche.setText(vuoteClassiche + " morse classiche su " + totClassiche + " disponibili");
        descrizioneMorseElettriche.setText(vuoteElettriche + " morse elettriche su " + totElettriche + " disponibili");

    }

    private Callback<TableColumn<Bicicletta, String>, TableCell<Bicicletta, String>> actionCellCallback() {
        return (final TableColumn<Bicicletta, String> param) -> {
            final TableCell<Bicicletta, String> cell = new TableCell<Bicicletta, String>() {

                final Button elimina = new Button("Elimina");
                final Button sposta = new Button("Sposta");
                final Button ripara = new Button("Ripara");
                final HBox box = new HBox();

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        elimina.setOnAction(event -> {
                            //Elimino bicicletta dalla tabella
                            Bicicletta bicicletta = tabellaBiciclette.getItems().get(getIndex());
                            tabellaBiciclette.getItems().remove(getIndex());
                            //Sgancio bicicletta dalla rastrelliera
                            ObservableList<Rastrelliera> rastrelliere = selezioneRastrelliera.getItems();
                            
                            for (Rastrelliera r : rastrelliere) {
                                r.sgancia(bicicletta);
                            }
                            //Sgancio dalla morsa ed elimino la bicicletta dal database
                            DbFactory.morsa().sgancia(bicicletta);
                            DbFactory.bicicletta().delete(bicicletta);
                            //Aggiorno descrizione disponibilita
                            aggiornaDescrizioniDisponibilita();

                            System.out.println("Eliminata bicicletta " + bicicletta.getId());
                        });

                        sposta.setOnAction(event -> {
                            Bicicletta bicicletta = tabellaBiciclette.getItems().get(getIndex());
                            AppSession.getInstance().setBiciclettaSelezionata(bicicletta);
                            routing("spostamentoBicicletta");
                        });

                        ripara.setOnAction(event -> {
                            Bicicletta bicicletta = tabellaBiciclette.getItems().get(getIndex());
                            if (!bicicletta.getDanneggiata()) {
                                return;
                            }
                            //persistenza
                            DbFactory.bicicletta().ripara(bicicletta);
                            //modifico il valore nelle rastrelliere
                            ObservableList<Rastrelliera> rastrelliere = selezioneRastrelliera.getItems();
                            for (Rastrelliera r : rastrelliere) {
                                for (Morsa m : r.getMorse()) {
                                    if (!m.vuota() && m.getBicicletta().equals(bicicletta)) {
                                        m.getBicicletta().ripara();
                                    }
                                }
                            }
                            //modifico la tabella
                            caricaBicicletteRastrelliera();
                        });

                        if (!box.getChildren().contains(sposta)) {
                            box.getChildren().add(sposta);
                        }

                        if (!box.getChildren().contains(elimina)) {
                            box.getChildren().add(elimina);
                        }

                        if (!box.getChildren().contains(ripara)) {
                            box.getChildren().add(ripara);
                        }

                        setGraphic(box);
                        setText(null);
                    }
                }
            };
            return cell;
        };
    }
    

    private Callback<TableColumn<Bicicletta, String>, TableCell<Bicicletta, String>> typeCellCallback() {
        return (final TableColumn<Bicicletta, String> param) -> {
            final TableCell<Bicicletta, String> cell = new TableCell<Bicicletta, String>() {

                final Label tipoBicicletta = new Label();

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Bicicletta bicicletta = getTableView().getItems().get(getIndex());
                        tipoBicicletta.setText(bicicletta.getTipoBicicletta().toString());

                        setGraphic(tipoBicicletta);
                        setText(null);
                    }
                }
            };
            return cell;
        };
    }

}
