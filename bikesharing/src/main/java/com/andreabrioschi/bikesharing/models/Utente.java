/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

import java.util.Objects;

/**
 *
 * @author andreabrioschi
 */
public abstract class Utente {
    private final int id;
    private final String password;

    public Utente(int id, String password) {
        Objects.requireNonNull(password);
        this.id = id;
        this.password = password;
    }
    

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utente other = (Utente) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
    
}
