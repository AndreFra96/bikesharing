/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author andreabrioschi
 */
public class Cliente extends Utente {

    private final String email;
    private final String nome;
    private final String cognome;
    private final Boolean studente;
    private TreeSet<Noleggio> noleggi;

    public Cliente(int id, String password, String email, String nome, String cognome, Boolean studente) {
        super(id, password);
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.studente = studente;
        this.noleggi = new TreeSet<>();
    }

    public Cliente(int id, String password, String email, String nome, String cognome) {
        super(id, password);
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.studente = false;
        this.noleggi = new TreeSet<>();
    }

    public Cliente(int id, String password, String email, String nome, String cognome, Boolean studente, Set<Noleggio> noleggi) {
        super(id, password);
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.studente = studente;
        this.noleggi = new TreeSet<>(noleggi);
    }

    public Cliente(int id, String password, String email, String nome, String cognome, Set<Noleggio> noleggi) {
        super(id, password);
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.studente = false;
        this.noleggi = new TreeSet<>(noleggi);
    }

    public String getEmail() {
        return this.email;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public Boolean getStudente() {
        return this.studente;
    }

    public Set<Noleggio> getNoleggi() {
        return this.noleggi;
    }
    
    public void addNoleggio(Noleggio n){
        this.noleggi.add(n);
    }
    
    public Optional<Noleggio> noleggioAttivo(){
        if(!this.noleggi.iterator().hasNext()) return Optional.empty();
        Noleggio last = this.noleggi.iterator().next();
        if(last.terminato()) return Optional.empty();
        return Optional.of(last);
    }
    
    public void setNoleggi(Set<Noleggio> noleggi){
        this.noleggi = new TreeSet(noleggi);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Cliente:\n");
        stringBuilder.append("id: " + this.getId());
        stringBuilder.append("\nemail: " + this.getEmail());
        stringBuilder.append("\nnome: " + this.getNome());
        stringBuilder.append("\ncognome: " + this.getCognome());
        stringBuilder.append("\nstudente: " + this.getStudente());
        for(Noleggio n : noleggi)
            stringBuilder.append("\n"+n);
        return stringBuilder.toString();
    }

}
