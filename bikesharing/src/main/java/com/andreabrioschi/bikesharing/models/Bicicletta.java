/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

/**
 * @author andreabrioschi
 */
public class Bicicletta {
    private final int id;
    private final TipoBicicletta tipo;
    private boolean danneggiata;
    
    public Bicicletta(final int id, final TipoBicicletta tipo, final boolean danneggiata){
        this.id = id;
        this.tipo = tipo;
        this.danneggiata = danneggiata;
    }
    
    public int getId(){
        return this.id;
    }
    
    public TipoBicicletta getTipoBicicletta(){
        return this.tipo;
    }
    
    public boolean getDanneggiata(){
        return this.danneggiata;
    }
    
    public void ripara(){
        this.danneggiata = false;
    }
    
    public void danneggia(){
        this.danneggiata = true;
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) 
            return false;
        
        if(!(obj instanceof Bicicletta))
            return false;
        
        final Bicicletta other = (Bicicletta) obj;
        
        if (this.id != other.id) 
            return false;
        
        return true;
    }
    
    @Override
    public int hashCode(){
        return 31 + this.id;
    }
    
    @Override 
    public String toString(){
        return id+" - "+tipo;
    }
    
}
