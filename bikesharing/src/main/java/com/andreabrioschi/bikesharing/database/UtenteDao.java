/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.database;

import com.andreabrioschi.bikesharing.models.Abbonamento;
import com.andreabrioschi.bikesharing.models.AbbonamentoAnnuale;
import com.andreabrioschi.bikesharing.models.AbbonamentoGiornaliero;
import com.andreabrioschi.bikesharing.models.AbbonamentoSettimanale;
import com.andreabrioschi.bikesharing.models.Cliente;
import com.andreabrioschi.bikesharing.models.Manutentore;
import com.andreabrioschi.bikesharing.models.Noleggio;
import com.andreabrioschi.bikesharing.models.Utente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andreabrioschi
 */
public class UtenteDao implements Dao<Utente> {



    @Override
    public List<Utente> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Utente getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Utente add(Utente t) {
        PreparedStatement pstmt;
        Utente u = null;

        if (t instanceof Manutentore) {
            try {
                Manutentore m = (Manutentore) t;
                pstmt = Database.getInstance().getConnection().prepareStatement("INSERT INTO utente (password,manutentore,studente) VALUES (SHA1(?),1,0)", Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, m.getPassword());

                pstmt.execute();

                try ( ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        u = new Manutentore(generatedKeys.getInt(1), m.getPassword());
                        System.out.println("Generato: " + generatedKeys.getInt(1));
                    } else {
                        Logger.getLogger(BiciclettaDao.class.getName()).log(Level.SEVERE, null, new SQLException("Creazione manutentore fallita, nessun ID ottenuto."));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(UtenteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (t instanceof Cliente) {
            try {
                Cliente c = (Cliente) t;
                pstmt = Database.getInstance().getConnection().prepareStatement("INSERT INTO utente (email,nome,cognome,manutentore,password,studente) VALUES (?,?,?,0,SHA1(?),?)", Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, c.getEmail());
                pstmt.setString(2, c.getNome());
                pstmt.setString(3, c.getCognome());
                pstmt.setString(4, c.getPassword());
                pstmt.setBoolean(5, c.getStudente());

                pstmt.execute();

                try ( ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        u = new Cliente(generatedKeys.getInt(1), c.getPassword(), c.getEmail(), c.getNome(), c.getCognome(), c.getStudente());
                        System.out.println("Generato: " + generatedKeys.getInt(1));
                    } else {
                        Logger.getLogger(BiciclettaDao.class.getName()).log(Level.SEVERE, null, new SQLException("Creazione cliente fallita, nessun ID ottenuto."));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(UtenteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return u;
    }
    
    public Utente login(final int id, final String password) {
        Utente user = null;
        try {
            PreparedStatement pstmt;
            pstmt = Database.getInstance().getConnection().prepareStatement("SELECT * FROM utente WHERE id = ? AND password LIKE SHA1(?)");
            pstmt.setInt(1, id);
            pstmt.setString(2, password);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                Boolean manutentore = result.getBoolean("manutentore");
                if (manutentore) {
                    int manutentoreID = result.getInt("id");
                    String manutentorePassword = result.getString("password");
                    Manutentore m = new Manutentore(manutentoreID, manutentorePassword);
                    user = m;
                    break;
                } else {
                    int clienteID = result.getInt("id");
                    String clientePassword = result.getString("password");
                    String email = result.getString("email");
                    String nome = result.getString("nome");
                    String cognome = result.getString("cognome");
                    Boolean studente = result.getBoolean("studente");
                    //Estraggo i noleggi del cliente
                    Set<Noleggio> noleggi = DbFactory.noleggio().getByCustomerId(clienteID);
                    Cliente c = new Cliente(clienteID, clientePassword, email, nome, cognome, studente, noleggi);
                    user = c;
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }
    


    @Override
    public void delete(Utente u) {
        try {
            PreparedStatement pstmt;
            pstmt = Database.getInstance().getConnection().prepareStatement("DELETE FROM utente WHERE id = ?");
            pstmt.setInt(1, u.getId());
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Abbonamento addAbbonamentoCliente(Cliente c, Abbonamento a) {
        Abbonamento abb = null;

        try {
            PreparedStatement pstmt;
            pstmt = Database.getInstance().getConnection().prepareStatement("INSERT INTO abbonamento (data_inizio,tipoAbbonamento,utente) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setDate(1, java.sql.Date.valueOf(a.getDataInizio()));
            pstmt.setString(2, a.toString());
            pstmt.setInt(3, c.getId());
            pstmt.execute();
            try ( ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    if (a instanceof AbbonamentoAnnuale) {
                        abb = new AbbonamentoAnnuale(generatedKeys.getInt(1), c, a.getDataInizio(),0);
                    } else if (a instanceof AbbonamentoGiornaliero) {
                        abb = new AbbonamentoGiornaliero(generatedKeys.getInt(1), c, a.getDataInizio(),0);
                    } else if (a instanceof AbbonamentoSettimanale) {
                        abb = new AbbonamentoSettimanale(generatedKeys.getInt(1), c, a.getDataInizio(),0);
                    }
                    System.out.println("Generato: " + generatedKeys.getInt(1));
                } else {
                    Logger.getLogger(UtenteDao.class.getName()).log(Level.SEVERE, null, new SQLException("Inserimento abbonamento cliente fallita, nessun ID ottenuto."));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return abb;
    }

    public Abbonamento getAbbonamentoCliente(Cliente c) {
        Abbonamento abb = null;

        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("SELECT * FROM abbonamento WHERE utente = ?");
            pstmt.setInt(1, c.getId());
            ResultSet result = pstmt.executeQuery();
            while(result.next()){
                int id = result.getInt("id");
                LocalDate data_inizio = result.getDate("data_inizio").toLocalDate();
                String tipoAbbonamento = result.getString("tipoAbbonamento");
                int segnalazioni = result.getInt("segnalazioni");
                switch(tipoAbbonamento){
                    case "ANNUALE":
                        abb = new AbbonamentoAnnuale(id,c,data_inizio,segnalazioni);
                        break;
                    case "GIORNALIERO":
                        abb = new AbbonamentoGiornaliero(id,c,data_inizio,segnalazioni);
                        break;
                    case "SETTIMANALE":
                        abb = new AbbonamentoSettimanale(id,c,data_inizio,segnalazioni);
                        break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return abb;
    }
    
    public void deleteAbbonamentoCliente(Cliente cliente){
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("DELETE FROM abbonamento WHERE utente = ?");
            pstmt.setInt(1, cliente.getId());
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void segnala(Utente c){
        try {
            PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement("UPDATE abbonamento SET segnalazioni = segnalazioni + 1 WHERE utente = ?");
            pstmt.setInt(1, c.getId());
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    

}
