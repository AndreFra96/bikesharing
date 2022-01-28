/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.database;

import com.andreabrioschi.bikesharing.models.Bicicletta;
import com.andreabrioschi.bikesharing.models.BiciclettaClassica;
import com.andreabrioschi.bikesharing.models.BiciclettaElettrica;
import com.andreabrioschi.bikesharing.models.TipoBicicletta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andreabrioschi
 */
public class BiciclettaDao implements Dao<Bicicletta> {



    @Override
    public List<Bicicletta> getAll() {
        List<Bicicletta> biciclette = new ArrayList<>();
        try {
            ResultSet result = Database.getInstance().executeQuery("SELECT id,tipo,danneggiata FROM bicicletta ORDER BY id");
            while (result.next()) {
                int id = result.getInt("id");
                String tipo = result.getString("tipo");
                boolean danneggiata = result.getBoolean("danneggiata");

                switch (tipo) {
                    case "CLASSICA":
                        biciclette.add(new Bicicletta(id, new BiciclettaClassica(), danneggiata));
                        break;
                    case "ELETTRICA":
                        biciclette.add(new Bicicletta(id, new BiciclettaElettrica(false), danneggiata));
                        break;
                    case "ELETTRICA CON SEGGIOLINO":
                        biciclette.add(new Bicicletta(id, new BiciclettaElettrica(true), danneggiata));
                        break;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(BiciclettaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return biciclette;
    }

    @Override
    public Bicicletta getById(int id) {
        Bicicletta bicicletta = null;
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("SELECT id,tipo,danneggiata FROM bicicletta WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                int bikeid = result.getInt("id");
                String tipo = result.getString("tipo");
                boolean danneggiata = result.getBoolean("danneggiata");

                switch (tipo) {
                    case "CLASSICA":
                        bicicletta = new Bicicletta(id, new BiciclettaClassica(), danneggiata);
                        break;
                    case "ELETTRICA":
                        bicicletta = new Bicicletta(id, new BiciclettaElettrica(false), danneggiata);
                        break;
                    case "ELETTRICA CON SEGGIOLINO":
                        bicicletta = new Bicicletta(id, new BiciclettaElettrica(true), danneggiata);
                        break;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(BiciclettaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bicicletta;
    }

    @Override
    public Bicicletta add(Bicicletta b) {
        int id = 0;
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("INSERT INTO bicicletta (tipo,danneggiata) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            TipoBicicletta t = b.getTipoBicicletta();
            pstmt.setString(1, t.toString());
            pstmt.setBoolean(2, b.getDanneggiata());
            pstmt.execute();

            try ( ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    System.out.println("Generato: " + generatedKeys.getInt(1));
                } else {
                    Logger.getLogger(BiciclettaDao.class.getName()).log(Level.SEVERE, null, new SQLException("Creazione bicicletta fallita, nessun ID ottenuto."));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BiciclettaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Bicicletta(id, b.getTipoBicicletta(), b.getDanneggiata());
    }

    @Override
    public void delete(Bicicletta bicicletta) {
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("DELETE FROM bicicletta WHERE id = ?");
            pstmt.setInt(1, bicicletta.getId());
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BiciclettaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ripara(Bicicletta bicicletta) {
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("UPDATE bicicletta SET danneggiata = 0 WHERE id = ?");
            pstmt.setInt(1, bicicletta.getId());
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BiciclettaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void danneggia(Bicicletta bicicletta) {
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("UPDATE bicicletta SET danneggiata = 1 WHERE id = ?");
            pstmt.setInt(1, bicicletta.getId());
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BiciclettaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
