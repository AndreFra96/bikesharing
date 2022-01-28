/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.database;

import com.andreabrioschi.bikesharing.models.Bicicletta;
import com.andreabrioschi.bikesharing.models.BiciclettaClassica;
import com.andreabrioschi.bikesharing.models.BiciclettaElettrica;
import com.andreabrioschi.bikesharing.models.Morsa;
import com.andreabrioschi.bikesharing.models.Rastrelliera;
import com.andreabrioschi.bikesharing.models.TipoMorsa;
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
public class RastrellieraDao implements Dao<Rastrelliera> {



    @Override
    public List<Rastrelliera> getAll() {
        List<Rastrelliera> rastrelliere = new ArrayList<>();
        List<Morsa> morse = new ArrayList<>();

        try {
            ResultSet result = Database.getInstance().executeQuery("SELECT rastrelliera.id as rastrelliera,nome,latitudine,longitudine,morsa.id as morsa,morsa.tipo as tipoMorsa,bicicletta,bicicletta.tipo as tipoBicicletta,bicicletta.danneggiata FROM rastrelliera LEFT JOIN morsa ON morsa.rastrelliera = rastrelliera.id LEFT JOIN bicicletta on bicicletta.id = morsa.bicicletta");
            while (result.next()) {
                int rastrelliera = result.getInt("rastrelliera");
                String nome = result.getString("nome");
                double latitudine = result.getDouble("latitudine");
                double longitudine = result.getDouble("longitudine");
                int morsa = result.getInt("morsa");
                String tipoMorsa = result.getString("tipoMorsa");
                int bicicletta = result.getInt("bicicletta");
                String tipoBicicletta = result.getString("tipoBicicletta");
                boolean danneggiata = result.getBoolean("danneggiata");

                //Creo morsa
                if (morsa != 0 && !tipoMorsa.equals("")) {

                    Morsa m = null;

                    switch (tipoMorsa) {
                        case "CLASSICA":
                            m = new Morsa(morsa, TipoMorsa.CLASSICA);
                            break;
                        case "ELETTRICA":
                            m = new Morsa(morsa, TipoMorsa.ELETTRICA);
                            break;
                    }

                    if (m != null && !morse.contains(m)) {
                        morse.add(m);
                    }

                }

                if (bicicletta != 0) {

                    //Creo bicicletta con tipo
                    Bicicletta b = null;

                    switch (tipoBicicletta) {
                        case "CLASSICA":
                            b = new Bicicletta(bicicletta, new BiciclettaClassica(),danneggiata);
                            break;
                        case "ELETTRICA":
                            b = new Bicicletta(bicicletta, new BiciclettaElettrica(false),danneggiata);
                            break;
                        case "ELETTRICA CON SEGGIOLINO":
                            b = new Bicicletta(bicicletta, new BiciclettaElettrica(true),danneggiata);
                            break;
                    }

                    //Aggancio bicicletta alla morsa
                    if (b != null) {
                        for (Morsa m : morse) {
                            if (m.getId() == morsa && m.vuota()) {
                                m.aggancia(b);
                            }
                        }
                    }

                }

                //Creo rastrelliera
                if (rastrelliera != 0) {
                    Rastrelliera r = new Rastrelliera(rastrelliera, latitudine, longitudine, nome);
                    if (!rastrelliere.contains(r)) {
                        rastrelliere.add(r);
                    }
                }

                //Aggancio morsa alla rastrelliera
                if (morsa != 0) {
                    for (Rastrelliera r : rastrelliere) {
                        if (r.getId() == rastrelliera) {
                            for (Morsa m : morse) {
                                if (m.getId() == morsa) {
                                    r.aggiungiMorsa(m);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RastrellieraDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rastrelliere;
    }

    @Override
    public Rastrelliera getById(int id) {
        Rastrelliera r = null;
        List<Morsa> morse = new ArrayList<>();

        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("SELECT rastrelliera.id as rastrelliera,nome,latitudine,longitudine,morsa.id as morsa,morsa.tipo as tipoMorsa,bicicletta,bicicletta.tipo as tipoBicicletta, bicicletta.danneggiata FROM rastrelliera LEFT JOIN morsa ON morsa.rastrelliera = rastrelliera.id LEFT JOIN bicicletta on bicicletta.id = morsa.bicicletta WHERE rastrelliera.id = ?");
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                int rastrelliera = result.getInt("rastrelliera");
                String nome = result.getString("nome");
                double latitudine = result.getDouble("latitudine");
                double longitudine = result.getDouble("longitudine");
                int morsa = result.getInt("morsa");
                String tipoMorsa = result.getString("tipoMorsa");
                int bicicletta = result.getInt("bicicletta");
                String tipoBicicletta = result.getString("tipoBicicletta");
                boolean danneggiata = result.getBoolean("danneggiata");

                //Creo morsa
                if (morsa != 0 && !tipoMorsa.equals("")) {

                    Morsa m = null;

                    switch (tipoMorsa) {
                        case "CLASSICA":
                            m = new Morsa(morsa, TipoMorsa.CLASSICA);
                            break;
                        case "ELETTRICA":
                            m = new Morsa(morsa, TipoMorsa.ELETTRICA);
                            break;
                    }

                    if (m != null && !morse.contains(m)) {
                        morse.add(m);
                    }

                }

                if (bicicletta != 0) {

                    //Creo bicicletta con tipo
                    Bicicletta b = null;

                    switch (tipoBicicletta) {
                        case "CLASSICA":
                            b = new Bicicletta(bicicletta, new BiciclettaClassica(),danneggiata);
                            break;
                        case "ELETTRICA":
                            b = new Bicicletta(bicicletta, new BiciclettaElettrica(false),danneggiata);
                            break;
                        case "ELETTRICA CON SEGGIOLINO":
                            b = new Bicicletta(bicicletta, new BiciclettaElettrica(true),danneggiata);
                            break;
                    }

                    //Aggancio bicicletta alla morsa
                    if (b != null) {
                        for (Morsa m : morse) {
                            if (m.getId() == morsa && m.vuota()) {
                                m.aggancia(b);
                            }
                        }
                    }

                }

                //Creo rastrelliera
                if (rastrelliera != 0 && r == null) {
                    r = new Rastrelliera(rastrelliera, latitudine, longitudine, nome);
                }

                //Aggancio morsa alla rastrelliera
                if (morsa != 0) {
                    if (r.getId() == rastrelliera) {
                        for (Morsa m : morse) {
                            if (m.getId() == morsa) {
                                r.aggiungiMorsa(m);
                            }
                        }
                    }
                }
     
            }
        } catch (SQLException ex) {
            Logger.getLogger(RastrellieraDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(r.display());
        return r;
    }

    @Override
    public Rastrelliera add(Rastrelliera t) {
        int id = 0;

        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("INSERT INTO rastrelliera (nome,latitudine,longitudine) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, t.getNome());
            pstmt.setDouble(2, t.getLatitudine());
            pstmt.setDouble(3, t.getLongitudine());
            pstmt.execute();

            try ( ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    System.out.println("Generato: " + generatedKeys.getInt(1));
                } else {
                    Logger.getLogger(BiciclettaDao.class.getName()).log(Level.SEVERE, null, new SQLException("Creazione rastrelliera fallita, nessun ID ottenuto."));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RastrellieraDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new Rastrelliera(id, t.getLatitudine(), t.getLongitudine(), t.getNome());
    }
    
    @Override
    public void delete(Rastrelliera rastrelliera) {
        try {
            //controllo se ci sono biciclette attaccate alla rastrelliera
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("SELECT count(*) as biciclette FROM morsa WHERE rastrelliera = ? AND bicicletta IS NOT NULL");
            pstmt.setInt(1, rastrelliera.getId());
            ResultSet result = pstmt.executeQuery();
            int bicicletteCollegate = 0;
            while (result.next()) {
                bicicletteCollegate = result.getInt("biciclette");
            }
            if (bicicletteCollegate > 0) {
                throw new IllegalArgumentException("Rimuovere le biciclette dalla rastrelliera prima di eliminarla");
            }

            //Elimino le morse della rastrelliera 
            DbFactory.morsa().deleteByRastrellieraId(rastrelliera.getId());

            //Elimino la rastrelliera
            pstmt = Database.getInstance().getConnection().prepareStatement("DELETE FROM rastrelliera WHERE id = ?");
            pstmt.setInt(1, rastrelliera.getId());
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RastrellieraDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
