/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.database;

import com.andreabrioschi.bikesharing.models.Bicicletta;
import com.andreabrioschi.bikesharing.models.BiciclettaClassica;
import com.andreabrioschi.bikesharing.models.BiciclettaElettrica;
import com.andreabrioschi.bikesharing.models.Noleggio;
import com.andreabrioschi.bikesharing.models.Rastrelliera;
import com.andreabrioschi.bikesharing.models.Utente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andreabrioschi
 */
public class NoleggioDao implements Dao<Noleggio> {



    @Override
    public List<Noleggio> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Noleggio getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Noleggio add(Noleggio t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void delete(final Noleggio t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Noleggio add(Noleggio n, Utente u) {
        Noleggio response = null;
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("INSERT INTO noleggio (data_inizio,utente,bicicletta,rastrelliera) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(n.getDataInizio()));
            pstmt.setInt(2, u.getId());
            pstmt.setInt(3, n.getBicicletta().getId());
            pstmt.setInt(4, n.getRastrelliera().getId());
            pstmt.execute();
            try ( ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    response = new Noleggio(generatedKeys.getInt(1), n.getBicicletta(), n.getRastrelliera(), n.getDataInizio());
                    System.out.println("Generato: " + generatedKeys.getInt(1));
                } else {
                    Logger.getLogger(UtenteDao.class.getName()).log(Level.SEVERE, null, new SQLException("Inserimento abbonamento cliente fallita, nessun ID ottenuto."));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NoleggioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public Set<Noleggio> getByCustomerId(int customer) {
        Set<Noleggio> noleggi = new TreeSet<>();

        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("SELECT noleggio.id as id_noleggio,data_inizio,data_fine,bicicletta.id as id_bicicletta,bicicletta.danneggiata,bicicletta.tipo,rastrelliera FROM `noleggio` JOIN bicicletta ON bicicletta.id = noleggio.bicicletta AND noleggio.utente = ?");
            pstmt.setInt(1, customer);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                int id_noleggio = result.getInt("id_noleggio");
                LocalDateTime data_inizio = result.getTimestamp("data_inizio").toLocalDateTime();
                LocalDateTime data_fine = result.getTimestamp("data_fine") == null ? null : result.getTimestamp("data_fine").toLocalDateTime();
                int id_bicicletta = result.getInt("id_bicicletta");
                String tipo_bicicletta = result.getString("tipo");
                boolean danneggiata = result.getBoolean("danneggiata");
                int id_rastrelliera = result.getInt("rastrelliera");

                //Costruisco la bicicletta
                Bicicletta b = null;
                if (tipo_bicicletta.equals("ELETTRICA")) {
                    b = new Bicicletta(id_bicicletta, new BiciclettaElettrica(false),danneggiata);
                } else if (tipo_bicicletta.equals("CLASSICA")) {
                    b = new Bicicletta(id_bicicletta, new BiciclettaClassica(),danneggiata);
                } else if (tipo_bicicletta.equals("ELETTRICA CON SEGGIOLINO")) {
                    b = new Bicicletta(id_bicicletta, new BiciclettaElettrica(true),danneggiata);
                }

                //Costruisco la rastrelliera
                Rastrelliera rastrelliera = DbFactory.rastrelliera().getById(id_rastrelliera);

                //Costruisco il noleggio
                Noleggio n = new Noleggio(id_noleggio, b, rastrelliera, data_inizio, data_fine);

                //Aggiungo il noleggio alla lista
                noleggi.add(n);

            }
        } catch (SQLException ex) {
            Logger.getLogger(NoleggioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return noleggi;

    }

    public Noleggio termina(Noleggio n) {
        Noleggio result = null;
        try {
            LocalDateTime end = LocalDateTime.now();
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("UPDATE noleggio SET data_fine = ? WHERE id = ?");
            pstmt.setTimestamp(1, Timestamp.valueOf(end));
            pstmt.setInt(2, n.getId());
            pstmt.execute();
            result = new Noleggio(n.getId(), n.getBicicletta(), n.getRastrelliera(), n.getDataInizio(), end);
        } catch (SQLException ex) {
            Logger.getLogger(NoleggioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }



}
