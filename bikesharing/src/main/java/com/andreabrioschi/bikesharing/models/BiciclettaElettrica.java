
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

import com.andreabrioschi.bikesharing.models.Noleggio;
import com.andreabrioschi.bikesharing.models.TipoMorsa;

/**
 *
 * @author andreabrioschi
 */
public class BiciclettaElettrica extends TipoBicicletta {

    private static final double PRICE = 0.5;
    private static final double INCREASING_RATIO = 1.4;

    private final Boolean seggiolino;

    public BiciclettaElettrica(final Boolean seggiolino) {
        super(TipoMorsa.ELETTRICA);
        this.seggiolino = seggiolino;
    }

    public Boolean seggiolino() {
        return this.seggiolino;
    }

    @Override
    public double costoNoleggio(Noleggio noleggio) {
        long durata = noleggio.getDurata();
        System.out.println("durata:"+durata);

        double tot = PRICE * (Math.ceil(durata / 30) + INCREASING_RATIO);

        return tot;
    }

    public String toString() {
        if (seggiolino) {
            return "ELETTRICA CON SEGGIOLINO";
        }
        return "ELETTRICA";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof BiciclettaElettrica)) {
            return false;
        }
        BiciclettaElettrica bici = (BiciclettaElettrica) o;
        return this.seggiolino == bici.seggiolino;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int hash = 1;
        hash = PRIME * hash + this.getClass().hashCode();
        hash = PRIME * hash + this.seggiolino.hashCode();
        return hash;
    }

}
