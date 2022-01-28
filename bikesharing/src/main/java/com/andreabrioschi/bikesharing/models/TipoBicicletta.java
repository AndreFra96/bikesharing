package com.andreabrioschi.bikesharing.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andreabrioschi
 */
public abstract class TipoBicicletta {
    private final TipoMorsa morsa;
    
    public TipoBicicletta(final TipoMorsa morsa){
        this.morsa = morsa;
    }
    
    public TipoMorsa getTipoMorsa(){
        return this.morsa;
    }
    
    public abstract double costoNoleggio(Noleggio noleggio);
    
}
