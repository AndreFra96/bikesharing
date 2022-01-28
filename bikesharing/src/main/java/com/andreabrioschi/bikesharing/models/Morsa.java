/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

/**
 *
 * @author andreabrioschi
 */
public class Morsa {
    private final int id;
    private final TipoMorsa tipo;
    private Bicicletta bicicletta;
    
    public Morsa(final int id, final TipoMorsa tipo){
        this.id = id;
        this.tipo = tipo;
        this.bicicletta = null;
    }
    
    public int getId(){
        return this.id;
    }
    
    public TipoMorsa getTipoMorsa(){
        return this.tipo;
    }
    
    public Bicicletta getBicicletta(){
        return this.bicicletta;
    }
    
    public boolean vuota(){
        return this.bicicletta == null;
    }
    
    //@requires this.vuota() && this.compatibile(b.getTipoBicicletta())
    //@ensures this.bicicletta = b
    public void aggancia(Bicicletta b){
        if(!vuota()) throw new IllegalStateException("La morsa non è vuota");
        if(!compatibile(b.getTipoBicicletta())) throw new IllegalArgumentException("La bicicletta non è compatibile");
        this.bicicletta = b;
    }
    
    public void sgancia(){
        this.bicicletta = null;
    }
    
    public boolean compatibile(TipoBicicletta b){
        return b.getTipoMorsa() == this.tipo;
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) 
            return false;
        
        if(!(obj instanceof Morsa))
            return false;
        
        final Morsa other = (Morsa) obj;
        
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
        return "Morsa: "+this.id+", tipo: "+this.tipo+", bicicletta: "+this.bicicletta;
    }
    
}
