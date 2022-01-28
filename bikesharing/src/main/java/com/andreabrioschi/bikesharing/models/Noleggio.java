/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 *  inv: bicicletta != null AND rastrelliera != null AND bicicletta != null 
 * @author andreabrioschi
 */
public class Noleggio implements Comparable<Noleggio> {

    private final int id;
    private final Bicicletta bicicletta;
    private final Rastrelliera rastrelliera;
    private final LocalDateTime dataInizio;
    private LocalDateTime dataFine = null;

    public Noleggio(final int id, final Bicicletta bicicletta, final Rastrelliera rastrelliera, final LocalDateTime dataInizio, final LocalDateTime dataFine) {
        this.id = id;
        this.bicicletta = bicicletta;
        this.rastrelliera = rastrelliera;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public Noleggio(final int id, final Bicicletta bicicletta, final Rastrelliera rastrelliera, final LocalDateTime dataInizio) {
        this.id = id;
        this.bicicletta = bicicletta;
        this.rastrelliera = rastrelliera;
        this.dataInizio = dataInizio;
    }

    public int getId() {
        return this.id;
    }

    public LocalDateTime getDataInizio() {
        return this.dataInizio;
    }

    public Bicicletta getBicicletta() {
        return this.bicicletta;
    }

    public Rastrelliera getRastrelliera() {
        return this.rastrelliera;
    }

    public LocalDateTime getDataFine() {
        return this.dataFine;
    }
    
    //@requires this.getDataFine() != null
    public long getDurata() {
        if (this.getDataFine() == null) {
            throw new IllegalStateException("Il noleggio non è ancora terminato");
        }
        return this.getDataInizio().until(getDataFine(), ChronoUnit.MINUTES);
    }

    public void setDataFine(LocalDateTime dataFine) {
        this.dataFine = dataFine;
    }

    public double getCosto() {
        return bicicletta.getTipoBicicletta().costoNoleggio(this);
    }
    
    public boolean terminato(){
        return this.dataFine != null;
    }

    @Override
    public int compareTo(Noleggio o) {
        if (o.getDataInizio().isEqual(dataInizio)) {
            return 0;
        } else if (o.getDataInizio().isAfter(dataInizio)) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Noleggio => ID: " + id + ", inizio: " + dataInizio + ", fine: " + dataFine + ", bicicletta: " + bicicletta;
    }
}
