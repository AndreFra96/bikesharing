/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.controllers;

import com.andreabrioschi.bikesharing.AppSession;
import com.andreabrioschi.bikesharing.common.PaymentHandler;
import com.andreabrioschi.bikesharing.database.DbFactory;
import com.andreabrioschi.bikesharing.models.Bicicletta;
import com.andreabrioschi.bikesharing.models.Cliente;
import com.andreabrioschi.bikesharing.models.Morsa;
import com.andreabrioschi.bikesharing.models.Noleggio;
import java.util.Optional;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author andreabrioschi
 */
public class CustomerHomepageController extends Controller{

    @FXML
    private Button logout;

    @FXML
    private Button ritira;

    @FXML
    private Button riconsegna;

    @FXML
    private Button segnala;

    @FXML
    private Label errorMessage;

    @FXML
    private Label noleggioAttivo;

    @FXML
    private void initialize() {
        AppSession session = AppSession.getInstance();
        if (!session.loginOK() || !session.rastrellieraOK() || !(session.getUtenteLogin() instanceof Cliente)) {
            //errore
        } else {
            Cliente c = (Cliente) session.getUtenteLogin();
            //Controllo se il cliente ha noleggi attivi
            Noleggio attivo = null;
            for (Noleggio n : c.getNoleggi()) {
                if (n.getDataFine() == null) {
                    attivo = n;
                }
            }
            if (attivo == null) {
                riconsegna.setDisable(true);
                segnala.setDisable(true);
            } else {
                noleggioAttivo.setText("NOLEGGIO ATTIVO: " + attivo.getBicicletta().toString());
                ritira.setDisable(true);
            }
        }
    }

    @FXML
    private void logout(ActionEvent event){
        AppSession.getInstance().logout();
        routing("login");
    }

    @FXML
    private void routeRitiraBicicletta(ActionEvent event){
        routing("ritiraBicicletta");
    }

    @FXML
    private void handleSimulaRiconsegna(ActionEvent event) {
        AppSession session = AppSession.getInstance();
        if (session.loginOK() && session.getUtenteLogin() instanceof Cliente) {
            Cliente c = (Cliente) session.getUtenteLogin();
            
            //Ottengo la bicicletta presente nell'ultimo noleggio del cliente
            Optional<Noleggio> noleggio = c.noleggioAttivo();
            if(noleggio.isEmpty()){
                error("Non risulta nessun noleggio attivo");
                return;
            }
            
            Noleggio last = noleggio.get();
            Bicicletta bici = last.getBicicletta();
            
            //Controllo se c'è posto nella rastrelliera
            if (!session.getRastrellieraSelezionata().haPosto(bici.getTipoBicicletta())) {
                error("La rastrelliera non ha posti disponibili");
                return;
            }
            
            //Ottengo morsa
            Morsa m = session.getRastrellieraSelezionata().ottieniMorsaLibera(bici.getTipoBicicletta());
            
            if(!confirm("Agganciare la bicicletta nella morsa numero "+m.getId()+"!\nEffettuare simulazione? ")){
                return;
            }

            //salvo in sessione
            m.aggancia(bici);
            
            //persistenza
            DbFactory.noleggio().termina(last);
            DbFactory.morsa().aggancia(bici, m);
            
            //Aggiorno i noleggi dell'utente in sessione
            Set<Noleggio> noleggi = DbFactory.noleggio().getByCustomerId(c.getId());
            c.setNoleggi(noleggi);
            AppSession.getInstance().login(c);
            
            //Effettuo il pagamento del noleggio
            Noleggio n = noleggi.iterator().next();
            PaymentHandler paymentHandler = new PaymentHandler();
            paymentHandler.payRent(c,n);
            
            //Se la durata del noleggio è di più di 2 ore effettuo segnalazione
            if(n.getDurata() > 120){
                DbFactory.utente().segnala(c);
            }
            
            //configurazione bottoni
            riconsegna.setDisable(true);
            segnala.setDisable(true);
            ritira.setDisable(false);

            //info               
            noleggioAttivo.setText("");
        }
    }

    @FXML
    private void handleSegnalaDanni(ActionEvent event) {
        AppSession session = AppSession.getInstance();
        if (session.loginOK() && session.getUtenteLogin() instanceof Cliente) {
            Cliente c = (Cliente) session.getUtenteLogin();
            //Ottengo la bicicletta presente nell'ultimo noleggio del cliente
            Noleggio last = c.getNoleggi().iterator().next();
            Bicicletta bici = last.getBicicletta();
            DbFactory.bicicletta().danneggia(bici);
            info("Segnalazione danni effettuata, riconsegnare la bicicletta");
        }
    }

}
