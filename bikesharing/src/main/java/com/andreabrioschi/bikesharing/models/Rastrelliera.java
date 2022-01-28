/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author andreabrioschi
 */
public class Rastrelliera {
    private final int id;
    private final double latitudine;
    private final double longitudine;
    private final String nome;
    private List<Morsa> morse;
    
    public Rastrelliera(int id, double latitudine, double longitudine, String nome, List<Morsa> morse){
        this.id = id;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.nome = nome;
        this.morse = morse;
    }
    
    public Rastrelliera(int id, double latitudine, double longitudine, String nome){
        this.id = id;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.nome = nome;
        this.morse = new ArrayList<>();
    }
    
    public int getId(){
        return this.id;
    }
    
    public double getLatitudine(){
        return this.latitudine;
    }
    
    public double getLongitudine(){
        return this.longitudine;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public List<Morsa> getMorse(){
        return Collections.unmodifiableList(this.morse);
    }
    
    public boolean haPosto(TipoBicicletta tipo){
        for(Morsa m : morse)
            if(m.vuota() && m.getTipoMorsa().equals(tipo.getTipoMorsa()))
                    return true;
           
        return false;
    }
    
    public Morsa ottieniMorsaPiena(TipoBicicletta tipo){
        Morsa free = null;
        
        for(Morsa m : morse){
            if(!m.vuota() && m.getBicicletta().getTipoBicicletta().equals(tipo) && !m.getBicicletta().getDanneggiata())
                return m;
        }
        
        return free;
    }
    
    public Morsa ottieniMorsaLibera(TipoBicicletta tipo){
        if(!haPosto(tipo)) throw new IllegalStateException("La Rastrelliera non ha posti disponibili");
        
        Morsa free = null;
        
        for(Morsa m : morse){
            if(m.vuota() && m.getTipoMorsa().equals(tipo.getTipoMorsa()))
                return m;
        }
        
        return free;
    }
    
    public void aggancia(Bicicletta b, Morsa m){
        if(morse.contains(m))
            morse.get(morse.indexOf(m)).aggancia(b);
    }
    
    public void sgancia(Morsa m){
        if(!morse.contains(m)) throw new IllegalArgumentException("Morsa non presente");
        morse.get(morse.indexOf(m)).sgancia();
    }
    
    public void sgancia(Bicicletta b){
        for(Morsa m : morse)
            if(!m.vuota() && m.getBicicletta().equals(b))
                sgancia(m);
    }
    
    public void aggiungiMorsa(Morsa m){
        if(!morse.contains(m))
            morse.add(m);
    }
    
    public boolean vuota(){
        for(Morsa m : getMorse()){
            if(!m.vuota()) return false;
        }
        return true;
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) 
            return false;
        
        if(!(obj instanceof Rastrelliera))
            return false;
        
        final Rastrelliera other = (Rastrelliera) obj;
        
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
        return this.nome;
    }
    
    public String display(){
        StringBuilder stringBuilder = new StringBuilder("Rastrelliera: "+id+", lat: "+latitudine+", lon: "+longitudine+", nome: "+nome);
        for(Morsa m : morse){
            stringBuilder.append(m.toString()+"\n");
        }
        return stringBuilder.toString();
    }
    
    
}
