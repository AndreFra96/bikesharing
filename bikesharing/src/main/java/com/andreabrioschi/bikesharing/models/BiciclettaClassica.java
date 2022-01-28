/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

/**
 *
 * @author andreabrioschi
 */
public class BiciclettaClassica extends TipoBicicletta{
    private static final double PRICE = 0.25;
    private static final double INCREASING_RATIO = 1.2;
    public BiciclettaClassica(){
        super(TipoMorsa.CLASSICA);
    }

    @Override
    public double costoNoleggio(Noleggio noleggio) {
        long durata = noleggio.getDurata();
        if(durata <= 30) return 0;
        
        double tot = PRICE * (Math.ceil(durata/30) + INCREASING_RATIO);
        
        return tot;
    }
    
    public String toString(){
        return "CLASSICA";
    }
    
    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        return o instanceof BiciclettaClassica;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash * this.getClass().hashCode();
    }

    
}
