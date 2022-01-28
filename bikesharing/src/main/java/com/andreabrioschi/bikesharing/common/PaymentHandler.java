/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.common;

import com.andreabrioschi.bikesharing.models.BiciclettaClassica;
import com.andreabrioschi.bikesharing.models.Carta;
import com.andreabrioschi.bikesharing.models.Cliente;
import com.andreabrioschi.bikesharing.models.Noleggio;
import com.andreabrioschi.bikesharing.models.Utente;

/**
 * Le istanze di questa classe rappresentano dei connettori ad un servizio di
 * pagamento.
 * 
 * Le informazioni sui parametri della carta di credito vengono memorizzati dal 
 * servizio di pagamento, in questo modo se è già stato effettuato un pagamento 
 * per uno specifico utente è possibile effettuarne un altro senza specificare 
 * la carta da utilizzare
 * 
 * ATTUALMENTE NON VIENE UTILIZZATO NESSUN SERVIZIO DI PAGAMENTO, I METODI DI 
 * PAGAMENTO RESTITUISCONO SEMPRE TRUE 
 *
 * @author andreabrioschi
 */
public class PaymentHandler {

    /**
     * Memorizza le informazioni di pagamento predefinite di uno specifico
     * utente
     *
     * @param carta carta di credito dell'utente
     * @param utente utente
     */
    private void setUserCard(Carta carta, Utente utente) {
        System.out.println("Morifica carta del cliente: "+utente+" => "+carta);
        //-->Chiamata al servizio di pagamento
    }

    /**
     * Effettua un pagamento utilizzando la carta fornita in input
     *
     * @param carta carta di credito
     * @param utente utente
     * @param totale totale pagamento
     * @return true se il pagamento va a buon fine, false altrimenti
     */
    public boolean pay(Carta carta, Utente utente, double totale) {
        System.out.println(utente+" ha pagato "+totale);
        setUserCard(carta,utente);
        //-->Chiamata al servizio di pagamento
        return true;
    }

    /**
     * Effettua un pagamento utilizzando la stessa carta che è stata utilizzata
     * dall'utente nell'ultimo pagamento
     *
     * @param utente
     * @param totale
     * @return
     */
    public boolean pay(Utente utente, double totale) {
        System.out.println(utente+" ha pagato "+totale);
        //-->Chiamata al servizio di pagamento
        return true;
    }
    
    /**
     * Effettua il pagamento di un noleggio utilizzando la stessa carta che è stata utilizzata
     * dall'utente nell'ultimo pagamento
     *
     * @param utente
     * @param n
     * @return
     */
    public boolean payRent(Utente utente, Noleggio n) {
        
        //Pagano solo i clienti
        if(!(utente instanceof Cliente)) return true;
        Cliente c = (Cliente) utente;
        
        //Gli studenti non pagano le bici classiche
        if(c.getStudente() && n.getBicicletta().getTipoBicicletta() instanceof BiciclettaClassica) return true;
        this.pay(utente,n.getCosto());
        
        return true;
    }

}
