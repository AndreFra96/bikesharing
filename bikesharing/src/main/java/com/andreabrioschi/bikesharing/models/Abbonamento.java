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
public abstract class Abbonamento {
    private int id;
    private final Cliente cliente;
    private final LocalDate dataInizio;
    protected final int segnalazioni;
    
    public Abbonamento(final int id, final Cliente cliente, final LocalDate dataInizio, final int segnalazioni){
         this.id = id;
         this.cliente = cliente;
         this.dataInizio = dataInizio;
         this.segnalazioni = segnalazioni;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getSegnalazioni(){
        return this.segnalazioni;
    }
    
    public Cliente getCliente(){
        return this.cliente;
    }
    
    public LocalDate getDataInizio(){
        return this.dataInizio;
    }
    
    public boolean scaduto(){
        if(getScadenza().isEmpty()) return false;
        return !(getScadenza().get().isAfter(LocalDate.now()));
    }
    
    public abstract double getPrezzo();
    public abstract Optional<LocalDate> getScadenza();


}
