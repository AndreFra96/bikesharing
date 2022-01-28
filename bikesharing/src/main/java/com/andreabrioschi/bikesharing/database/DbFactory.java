/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.database;

/**
 *
 * @author andreabrioschi
 */
public abstract class DbFactory {
    
    private static BiciclettaDao biciclettaDao = null;
    private static MorsaDao morsaDao = null;
    private static RastrellieraDao rastrellieraDao = null;
    private static UtenteDao utenteDao = null;
    private static NoleggioDao noleggioDao = null;
    private static Statistiche statistiche = null;

    
    public static BiciclettaDao bicicletta(){
        if(biciclettaDao == null)
            biciclettaDao = new BiciclettaDao();
        return biciclettaDao;
    }
    
    public static MorsaDao morsa(){
        if(morsaDao == null)
            morsaDao = new MorsaDao();
        return morsaDao;
    }
    
    public static RastrellieraDao rastrelliera(){
        if(rastrellieraDao == null)
            rastrellieraDao = new RastrellieraDao();
        return rastrellieraDao;
    }
    
    public static UtenteDao utente(){
        if(utenteDao == null)
            utenteDao = new UtenteDao();
        return utenteDao;
    }
    
    public static NoleggioDao noleggio(){
        if(noleggioDao == null)
            noleggioDao = new NoleggioDao();
        return noleggioDao;
    }
    
    public static Statistiche statistiche(){
        if(statistiche == null)
            statistiche = new Statistiche();
        return statistiche;
    }
    
}
