/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.controllers;

import com.andreabrioschi.bikesharing.AppSession;
import com.andreabrioschi.bikesharing.common.PaymentHandler;
import com.andreabrioschi.bikesharing.database.DbFactory;
import com.andreabrioschi.bikesharing.models.Abbonamento;
import com.andreabrioschi.bikesharing.models.AbbonamentoAnnuale;
import com.andreabrioschi.bikesharing.models.AbbonamentoGiornaliero;
import com.andreabrioschi.bikesharing.models.AbbonamentoSettimanale;
import com.andreabrioschi.bikesharing.models.Carta;
import com.andreabrioschi.bikesharing.models.Cliente;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author andreabrioschi
 */
public class PaymentController extends Controller{

    private Cliente cliente;

    ObservableList<String> opzioniAbbonamento = FXCollections.observableArrayList("Annuale", "Mensile", "Biennale");

    @FXML
    private TextField intestatario;

    @FXML
    private TextField numero;

    @FXML
    private DatePicker scadenza;

    @FXML
    private TextField cvv;

    @FXML
    private Label errorMessage;

    @FXML
    private ChoiceBox<Abbonamento> abbonamento;

    @FXML
    private Button quitHandler;

    @FXML
    private Button confirmHandler;

    @FXML
    private void initialize(){
        AppSession session = AppSession.getInstance();
        if (!session.loginOK() || !(session.getUtenteLogin() instanceof Cliente)) {
            routing("registration");
            return;
        }

        Cliente c = (Cliente) session.getUtenteLogin();

        cliente = c;

        //Inizializzo select metodi di pagamento
        LocalDate now = LocalDate.now();
        Abbonamento def = new AbbonamentoAnnuale(1, c, now,0);
        List<Abbonamento> abbonamenti = List.of(def, new AbbonamentoGiornaliero(1, c, now,0), new AbbonamentoSettimanale(1, c, now,0));
        ObservableList<Abbonamento> options = FXCollections.observableArrayList(abbonamenti);
        abbonamento.setItems(options);
        abbonamento.setValue(def);
    }

    @FXML
    void confirmHandler(ActionEvent event){
        //Calcolo il totale dell'abbonamento selezionato
        double totale = abbonamento.getValue().getPrezzo();

        //Carico i dati della carta
        Carta c = new Carta(intestatario.getText(), numero.getText(), scadenza.getValue(), Integer.parseInt(cvv.getText()));

        //Effettuo il pagamento
        PaymentHandler paymentHandler = new PaymentHandler();
        if (!paymentHandler.pay(c, cliente, totale)) {
            error("Il pagamento non è andato a buon fine, riprovare");
            return;
        }
        
        //Salvo il nuovo abbonamento nel database
        Abbonamento a = DbFactory.utente().addAbbonamentoCliente(cliente, abbonamento.getValue());
        
        //Se la creazione dell'abbonamento fallisce restituisco un messaggio di errore
        if(a == null){
            error("Si è verificato un errore, contattare l'assistenza");
            return;
        }
        
        routing("customerHomepage");

    }

    @FXML
    void quitRouting(ActionEvent event){
        //Elimino l'utente dal database
        DbFactory.utente().delete(AppSession.getInstance().getUtenteLogin());
        //Elimino l'utente dalla sessione
        AppSession.getInstance().logout();
        //Ritorno alla schermata principale
        routing("registration");
    }

}
