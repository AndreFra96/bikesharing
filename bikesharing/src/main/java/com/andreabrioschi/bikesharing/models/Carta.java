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
public class Carta {
    private final String intestatario;
    private final String numero;
    private final LocalDate scadenza;
    private final int cvv;
    
    public Carta(final String intestatario, final String numero, final LocalDate scadenza, final int cvv){
        this.intestatario = intestatario;
        this.numero = numero;
        this.scadenza = scadenza;
        this.cvv = cvv;
    }
    
    public String getIntestatario(){
        return intestatario;
    }
    
    public String getNumero(){
        return numero;
    }
    
    public LocalDate getScadenza(){
        return scadenza;
    }
    
    public int getCvv(){
        return cvv;
    }
}
