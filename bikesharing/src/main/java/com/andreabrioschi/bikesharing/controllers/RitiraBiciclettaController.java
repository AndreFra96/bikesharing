/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.controllers;

import com.andreabrioschi.bikesharing.AppSession;
import com.andreabrioschi.bikesharing.database.DbFactory;
import com.andreabrioschi.bikesharing.models.BiciclettaClassica;
import com.andreabrioschi.bikesharing.models.BiciclettaElettrica;
import com.andreabrioschi.bikesharing.models.Cliente;
import com.andreabrioschi.bikesharing.models.Morsa;
import com.andreabrioschi.bikesharing.models.Noleggio;
import com.andreabrioschi.bikesharing.models.Rastrelliera;
import com.andreabrioschi.bikesharing.models.TipoBicicletta;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
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
public class RitiraBiciclettaController extends Controller {

    private Timer timer;

    @FXML
    private ChoiceBox<TipoBicicletta> tipoBicicletta;

    @FXML
    private Label outputMorsa;

    @FXML
    private Button back;

    @FXML
    private Button bottoneRitira;

    @FXML
    private void initialize() {
        //Imposto tipi bicicletta
        TipoBicicletta classica = new BiciclettaClassica();
        TipoBicicletta elettrica = new BiciclettaElettrica(false);
        TipoBicicletta elettricaConSeggiolino = new BiciclettaElettrica(true);
        List<TipoBicicletta> tipiBicicletta = List.of(classica, elettrica, elettricaConSeggiolino);
        this.tipoBicicletta.setItems(FXCollections.<TipoBicicletta>observableArrayList(tipiBicicletta));
        this.tipoBicicletta.setValue(classica);
    }

    @FXML
    void handleBack(ActionEvent event) {
        stopTimer();
        routing("customerHomepage");
    }

    @FXML
    void handleSimulaRitiro(ActionEvent event) {
        
        AppSession session = AppSession.getInstance();
        
        Rastrelliera r = session.getRastrellieraSelezionata();
        
        Morsa morsa = r.ottieniMorsaPiena(tipoBicicletta.getValue());

        //Controllo parametri
        if (morsa == null) {
            info("non ci sono morse disponibili per il tipo di bicicletta selezionato");
            return;
        }
        Cliente c = (Cliente) session.getUtenteLogin();
        //Controllo che siano passati almeno 5 minuti dalla fine dell'ultimo noleggio
        Set<Noleggio> noleggi = DbFactory.noleggio().getByCustomerId(c.getId());
        if (noleggi.size() > 0) {
            Noleggio last = noleggi.iterator().next();
            if (!last.getDataFine().plusMinutes(5).isBefore(LocalDateTime.now())) {
                info("E' necessario far passare 5 minuti fra i noleggi");
                return;
            }
        }
        stopTimer();

        //Persistenza sgancio bicicletta
        DbFactory.morsa().sgancia(morsa.getBicicletta());
        //Persistenza noleggio
        Noleggio n = new Noleggio(1, morsa.getBicicletta(), session.getRastrellieraSelezionata(), LocalDateTime.now());
        DbFactory.noleggio().add(n, c);

        //Sgancio bicicletta sessione
        session.getRastrellieraSelezionata().sgancia(morsa.getBicicletta());

        //logout
        AppSession.getInstance().logout();
        routing("login");

    }

    @FXML
    void handleChangeTipoBicicletta(ActionEvent event) {
        Morsa selected = ottieniMorsa();

        if (selected == null) {
            outputMorsa.setText("NON DISPONIBILE");
        } else {
            outputMorsa.setText("MORSA NUMERO " + selected.getId());
        }

        resetTimer();
    }

    private Morsa ottieniMorsa() {
        Rastrelliera r = AppSession.getInstance().getRastrellieraSelezionata();
        Morsa selected = null;
        return r.ottieniMorsaPiena(tipoBicicletta.getValue());
    }

    private void startTimer(int durata) {
        timer = new Timer();
        timer.schedule(new TimeoutTask(durata), 0, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void resetTimer() {
        stopTimer();
        startTimer(60);
    }

    class TimeoutTask extends TimerTask {

        private int durata;

        public TimeoutTask(final int durata) {
            super();
            this.durata = durata;
        }

        @Override
        public void run() {
            if (durata > 0) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        bottoneRitira.setText("Simula ritiro (" + durata + ")");
                        System.out.println(durata);
                    }
                });
                durata--;
            } else {
                stopTimer();
                routing("customerHomepage");

            }
        }

    }

}
