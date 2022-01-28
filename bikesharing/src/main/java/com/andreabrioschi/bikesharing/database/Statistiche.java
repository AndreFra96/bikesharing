/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andreabrioschi
 */
public class Statistiche {

    public String biciPiuUsata() {
        String bici = "";
        try {
            ResultSet result = Database.getInstance().executeQuery("SELECT count(*) as noleggi,bicicletta.tipo FROM `noleggio` JOIN bicicletta ON bicicletta.id = noleggio.bicicletta GROUP BY bicicletta.tipo ORDER BY noleggi DESC LIMIT 1");
            while (result.next()) {
                String noleggi = String.valueOf(result.getInt("noleggi"));
                String tipo = result.getString("tipo");
                bici = tipo + " (" + noleggi + " noleggi)";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistiche.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bici;
    }

    public String rastrellieraPiuUsata() {
        String rastrelliera = "";

        try {
            ResultSet result = Database.getInstance().executeQuery("SELECT count(*) as noleggi,rastrelliera.nome FROM noleggio JOIN rastrelliera ON rastrelliera.id = noleggio.rastrelliera GROUP BY rastrelliera.id ORDER BY noleggi DESC LIMIT 1");
            while (result.next()) {
                String noleggi = String.valueOf(result.getInt("noleggi"));
                String nome = result.getString("nome");
                rastrelliera = nome + " (" + noleggi + " noleggi)";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistiche.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rastrelliera;
    }

    public int mediaBici() {
        int tot = 0;
        int giorni = 0;

        try {
            ResultSet result = Database.getInstance().executeQuery("SELECT count(*) as count FROM noleggio GROUP BY CAST(data_inizio as DATE)");
            while (result.next()) {
                tot += result.getInt("count");
                giorni++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistiche.class.getName()).log(Level.SEVERE, null, ex);
        }
        return giorni > 0 ? tot / giorni : 0;
    }

    public int mediaBiciElettriche() {
        int tot = 0;
        int giorni = 0;

        try {
            ResultSet result = Database.getInstance().executeQuery("SELECT count(*) as count FROM noleggio JOIN bicicletta ON bicicletta.id = noleggio.bicicletta AND (bicicletta.tipo = \"ELETTRICA\" OR bicicletta.tipo = \"ELETTRICA CON SEGGIOLINO\") GROUP BY CAST(data_inizio as DATE)");
            while (result.next()) {
                tot += result.getInt("count");
                giorni++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistiche.class.getName()).log(Level.SEVERE, null, ex);
        }
        return giorni > 0 ? tot / giorni : 0;
    }

    public int mediaBiciClassiche() {
        int tot = 0;
        int giorni = 0;

        try {
            ResultSet result = Database.getInstance().executeQuery("SELECT count(*) as count FROM noleggio JOIN bicicletta ON bicicletta.id = noleggio.bicicletta AND bicicletta.tipo = \"CLASSICA\" GROUP BY CAST(data_inizio as DATE)");
            while (result.next()) { 
                tot += result.getInt("count");
                giorni++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistiche.class.getName()).log(Level.SEVERE, null, ex);
        }
        return giorni > 0 ? tot / giorni : 0;
    }

    public String fasciaOraria() {
        String fascia = "";
        try {
            ResultSet result = Database.getInstance().executeQuery("SELECT HOUR(data_inizio) as inizio, (HOUR(data_inizio) + 1) as fine FROM noleggio GROUP BY inizio ORDER BY COUNT(*) DESC LIMIT 1");
            while (result.next()) { 
                String inizio = String.valueOf(result.getInt("inizio"));
                String fine = String.valueOf(result.getInt("fine"));
                fascia = inizio + ":00 - "+fine+":00";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistiche.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fascia;
    }
    
    public double percentualeDanneggiate(){
        double perc = 0;
        try {
            ResultSet result = Database.getInstance().executeQuery("SELECT ((SELECT count(*) FROM `bicicletta` WHERE danneggiata = 1) / (SELECT count(*) FROM bicicletta)) * 100  as perc");
            while (result.next()) { 
                perc = result.getDouble("perc");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistiche.class.getName()).log(Level.SEVERE, null, ex);
        }
        return perc;
    }
}
