package com.andreabrioschi.bikesharing.controllers;

import com.andreabrioschi.bikesharing.App;
import com.andreabrioschi.bikesharing.AppMode;
import com.andreabrioschi.bikesharing.AppSession;
import com.andreabrioschi.bikesharing.database.DbFactory;
import com.andreabrioschi.bikesharing.models.Abbonamento;
import com.andreabrioschi.bikesharing.models.Cliente;
import com.andreabrioschi.bikesharing.models.Manutentore;
import com.andreabrioschi.bikesharing.models.Utente;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController extends Controller{

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Text errorMessage;

    @FXML
    private Label mode;

    @FXML
    private Label totem;

    @FXML
    private Button login;

    @FXML
    private void initialize() {
        mode.setText("Modalità " + App.mode);
        if (App.mode == AppMode.TOTEM) {
            if (!AppSession.getInstance().rastrellieraOK()) {
                throw new IllegalStateException("Impossibile avviare bikesharing in modalità totem poichè la rastrelliera non esiste");
            }
            totem.setText(AppSession.getInstance().getRastrellieraSelezionata().toString());
        }
        
    }

    @FXML
    void loginHandler(ActionEvent event){
        String utente = username.getText();
        String pwd = password.getText();

        Utente u = null;
        try {
            u = DbFactory.utente().login(Integer.parseInt(utente.trim()), pwd);
        } catch (NumberFormatException e) {
            error("Errore login, riprovare");
            return;
        }

        if (u == null) {
            error("Errore login, riprovare");
            return;
        }

        AppSession.getInstance().login(u);

        switch (App.mode) {
            case MAINTENANCE:
                if (!(u instanceof Manutentore)) {
                    error("In MAINTENANCE mode è possibile accedere solo con account manutentore");
                    break;
                }
                routing("rastrelliere");
                break;
            case TOTEM:
                if (!(u instanceof Cliente)) {
                    error("In TOTEM mode è possibile accedere solo con account cliente");
                    break;
                }
                Cliente cliente = (Cliente) u;
                Abbonamento a = DbFactory.utente().getAbbonamentoCliente(cliente);
                if (a == null) {
                    routing("payment");
                    break;
                }
                if(a.scaduto()){
                    info("L'abbonamento è scaduto, effettuare un nuovo abbonamento!");
                    DbFactory.utente().deleteAbbonamentoCliente(cliente);
                    routing("payment");
                    break;
                }
                routing("customerHomepage");
                break;
            case DEVELOPMENT:
                if (u instanceof Manutentore) {
                    routing("rastrelliere");
                } else {
                    routing("customerHomepage");
                }
                break;
        }

    }

    @FXML
    void registrationRouting(ActionEvent event){
        routing("registration");
    }

    @FXML
    void statsRouting(ActionEvent event){
        routing("statistiche");
    }
}
