/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.controllers;

import com.andreabrioschi.bikesharing.AppSession;
import com.andreabrioschi.bikesharing.database.DbFactory;
import com.andreabrioschi.bikesharing.database.RastrellieraDao;
import com.andreabrioschi.bikesharing.models.Morsa;
import com.andreabrioschi.bikesharing.models.Rastrelliera;
import com.andreabrioschi.bikesharing.models.TipoMorsa;
import com.andreabrioschi.bikesharing.models.Utente;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
public class RastrelliereController extends Controller {

    private RastrellieraDao rastrellieraDao;

    @FXML
    private TableView<Rastrelliera> tabellaRastrelliere;

    @FXML
    private TableColumn<Rastrelliera, Integer> id;

    @FXML
    private TableColumn<Rastrelliera, String> nome;

    @FXML
    private TableColumn<Rastrelliera, Long> latitudine;

    @FXML
    private TableColumn<Rastrelliera, Long> longitudine;

    @FXML
    private TableColumn<Rastrelliera, String> classiche;

    @FXML
    private TableColumn<Rastrelliera, String> elettriche;

    @FXML
    private TableColumn<Rastrelliera, String> azioni;

    @FXML
    private Label user;

    @FXML
    private Label errorMessage;

    @FXML
    private Button exit;

    @FXML
    private Button nuovaRastrelliera;

    @FXML
    private void initialize() {
        Utente u = AppSession.getInstance().getUtenteLogin();
        user.setText("ID Manutentore: " + u.getId());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        latitudine.setCellValueFactory(new PropertyValueFactory<>("latitudine"));
        longitudine.setCellValueFactory(new PropertyValueFactory<>("longitudine"));
        classiche.setCellFactory(morseClassicheCellCallback());
        elettriche.setCellFactory(morseElettricheCellCallback());
        azioni.setCellFactory(activityCellCallback());

        rastrellieraDao = new RastrellieraDao();
        List<Rastrelliera> rastrelliere = rastrellieraDao.getAll();

        tabellaRastrelliere.setItems(FXCollections.<Rastrelliera>observableArrayList(rastrelliere));
    }

    private Callback<TableColumn<Rastrelliera, String>, TableCell<Rastrelliera, String>> activityCellCallback() {
        return (final TableColumn<Rastrelliera, String> param) -> {
            final TableCell<Rastrelliera, String> cell = new TableCell<Rastrelliera, String>() {

                final Button biciclette = new Button("Biciclette");
                final Button elimina = new Button("Elimina");
                final HBox box = new HBox();

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        biciclette.setOnAction(event -> {
                            Rastrelliera rastrelliera = getTableView().getItems().get(getIndex());
                            System.out.println("Mostra biciclette rastrelliera " + rastrelliera.getId());

                            AppSession.getInstance().setRastrellieraSelezionata(rastrelliera);

                            routing("biciclette");

                        });

                        elimina.setOnAction(event -> {

                            Rastrelliera rastrelliera = getTableView().getItems().get(getIndex());

                            //Controllo che la rastrelliera non abbia biciclette collegate
                            if(!rastrelliera.vuota()){
                                 error("Rimuovere le biciclette dalla rastrelliera prima di eliminarla");
                                 return;
                            }
                            //Elimino la rastrelliera dalla tabella
                            getTableView().getItems().remove(getIndex());
                            //Elimino la rastrelliera dal database
                            DbFactory.rastrelliera().delete(rastrelliera);
                            System.out.println("Elimina rastrelliera " + rastrelliera.getId());
                        });

                        if (!box.getChildren().contains(biciclette)) {
                            box.getChildren().add(biciclette);
                        }

                        if (!box.getChildren().contains(elimina)) {
                            box.getChildren().add(elimina);
                        }

                        setGraphic(box);
                        setText(null);
                    }
                }
            };
            return cell;
        };
    }

    private Callback<TableColumn<Rastrelliera, String>, TableCell<Rastrelliera, String>> morseClassicheCellCallback() {
        return (final TableColumn<Rastrelliera, String> param) -> {
            final TableCell<Rastrelliera, String> cell = new TableCell<Rastrelliera, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        Rastrelliera actual = getTableView().getItems().get(getIndex());
                        int vuote = 0;
                        int totale = 0;
                        for (Morsa m : actual.getMorse()) {
                            if (m.getTipoMorsa() == TipoMorsa.CLASSICA) {
                                totale++;
                                if (m.vuota()) {
                                    vuote++;
                                }
                            }
                        }
                        setText(vuote + "/" + totale);
                    }
                }
            };
            return cell;
        };
    }

    private Callback<TableColumn<Rastrelliera, String>, TableCell<Rastrelliera, String>> morseElettricheCellCallback() {
        return (final TableColumn<Rastrelliera, String> param) -> {
            final TableCell<Rastrelliera, String> cell = new TableCell<Rastrelliera, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        Rastrelliera actual = getTableView().getItems().get(getIndex());
                        int vuote = 0;
                        int totale = 0;
                        for (Morsa m : actual.getMorse()) {
                            if (m.getTipoMorsa() == TipoMorsa.ELETTRICA) {
                                totale++;
                                if (m.vuota()) {
                                    vuote++;
                                }
                            }
                        }
                        setText(vuote + "/" + totale);
                    }
                }
            };
            return cell;
        };
    }

    @FXML
    void exitRouting(ActionEvent event){
        AppSession.getInstance().logout();
        routing("login");
    }

    @FXML
    void handleNuovaRastrelliera(ActionEvent event){
        routing("inserimentoRastrelliera");
    }

    @FXML
    void handleSort(ActionEvent event) {
        System.out.println(event);
    }

}
