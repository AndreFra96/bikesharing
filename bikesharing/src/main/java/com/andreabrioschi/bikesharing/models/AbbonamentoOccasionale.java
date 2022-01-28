/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author andreabrioschi
 */
public abstract class AbbonamentoOccasionale extends Abbonamento {
    
    public AbbonamentoOccasionale(final int id, final Cliente cliente, final LocalDate dataInizio, final int segnalazioni){
        super(id,cliente,dataInizio,segnalazioni);
    }
 
    public abstract LocalDate aggiungiDurata(LocalDate date);
    
    @Override
    public Optional<LocalDate> getScadenza() {
        if(this.segnalazioni >= 3) return Optional.of(LocalDate.MIN);
        TreeSet<Noleggio> noleggi = new TreeSet(this.getCliente().getNoleggi());
        if(noleggi.isEmpty())
            return Optional.empty();
        noleggi = new TreeSet(noleggi);
        
        Noleggio first = noleggi.descendingIterator().next();
        
        return Optional.of(aggiungiDurata(first.getDataInizio().toLocalDate()));
    }   
}
