/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

import java.time.LocalDate;

/**
 *
 * @author andreabrioschi
 */
public class AbbonamentoSettimanale extends AbbonamentoOccasionale {

    private static final double PREZZO = 9;

    public AbbonamentoSettimanale(final int id, final Cliente cliente, final LocalDate dataInizio, final int segnalazioni) {
        super(id, cliente, dataInizio, segnalazioni);
    }

    @Override
    public double getPrezzo() {
        return PREZZO;
    }

    @Override
    public LocalDate aggiungiDurata(LocalDate date) {
        return date.plusDays(7);
    }

    @Override
    public String toString() {
        return "SETTIMANALE";
    }

}
