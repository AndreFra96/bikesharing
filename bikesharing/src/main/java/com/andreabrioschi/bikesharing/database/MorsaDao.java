/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.database;

import com.andreabrioschi.bikesharing.models.Bicicletta;
import com.andreabrioschi.bikesharing.models.Morsa;
import com.andreabrioschi.bikesharing.models.Rastrelliera;
import com.andreabrioschi.bikesharing.models.TipoMorsa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andreabrioschi
 */
public class MorsaDao implements Dao<Morsa> {

    @Override
    public List<Morsa> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Morsa getById(int id) {
        Morsa m = null;
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("SELECT id,tipo,bicicletta FROM morsa WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                int id_morsa = result.getInt("id");
                int id_bicicletta = result.getInt("bicicletta");
                TipoMorsa tipo = TipoMorsa.valueOf(result.getString("tipo"));

                Bicicletta b = DbFactory.bicicletta().getById(id_bicicletta);

                m = new Morsa(id_morsa, tipo);
                
                if (b != null) {
                    m.aggancia(b);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MorsaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

    @Override
    public Morsa add(Morsa t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Morsa morsa) {
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("DELETE FROM morsa WHERE id = ?");
            pstmt.setInt(1, morsa.getId());
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MorsaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Morsa add(Morsa m, Rastrelliera r) {
        int id = 0;
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("INSERT INTO morsa (tipo,rastrelliera) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, m.getTipoMorsa().name());
            pstmt.setInt(2, r.getId());
            pstmt.execute();

            try ( ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    System.out.println("Generato: " + generatedKeys.getInt(1));
                } else {
                    Logger.getLogger(MorsaDao.class.getName()).log(Level.SEVERE, null, new SQLException("Creazione morsa fallita, nessun ID ottenuto."));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MorsaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Morsa(id, m.getTipoMorsa());
    }

    public void aggancia(Bicicletta b, Morsa m) {
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("UPDATE morsa SET bicicletta = ? WHERE id = ?");
            pstmt.setInt(1, b.getId());
            pstmt.setInt(2, m.getId());
            pstmt.execute();
        } catch (SQLException e) {
            Logger.getLogger(MorsaDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void sgancia(Bicicletta b) {
        try {
            System.out.println("sgancio: " + b);
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("UPDATE morsa SET bicicletta = NULL WHERE bicicletta = ?");
            pstmt.setInt(1, b.getId());
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MorsaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteByRastrellieraId(int rastrelliera) {
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("DELETE FROM morsa WHERE rastrelliera = ?");
            pstmt.setInt(1, rastrelliera);
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MorsaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
