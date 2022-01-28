/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

import java.time.LocalDate;
import java.util.Optional;

/**
 *
 * @author andreabrioschi
 */
public class AbbonamentoAnnuale extends Abbonamento{

    private static final double PREZZO = 36;
    
    public AbbonamentoAnnuale(final int id, final Cliente cliente, final LocalDate dataInizio, final int segnalazioni){
        super(id,cliente,dataInizio,segnalazioni);
    }
    
    @Override
    public Optional<LocalDate> getScadenza() {
        if(this.segnalazioni >= 3) return Optional.of(LocalDate.MIN);
        return Optional.of(this.getDataInizio().plusYears(1));
    }

    @Override
    public double getPrezzo() {
        return PREZZO;
    }
    
    @Override
    public String toString(){
        return "ANNUALE";
    }
}
