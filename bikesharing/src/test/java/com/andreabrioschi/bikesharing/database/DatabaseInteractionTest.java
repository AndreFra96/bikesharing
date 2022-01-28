/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.andreabrioschi.bikesharing.database;

import com.andreabrioschi.bikesharing.models.Bicicletta;
import com.andreabrioschi.bikesharing.models.BiciclettaElettrica;
import com.andreabrioschi.bikesharing.models.Cliente;
import com.andreabrioschi.bikesharing.models.Manutentore;
import com.andreabrioschi.bikesharing.models.Morsa;
import com.andreabrioschi.bikesharing.models.Utente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author andreabrioschi
 */
public class DatabaseInteractionTest {
    
    Utente createdUser = null;

    public DatabaseInteractionTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of login method, of class UtenteDao.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        UtenteDao instance = new UtenteDao();
        Utente manutentore = instance.login(1, "testPwd");
        Utente cliente = instance.login(2, "testPwd");
        Utente invalido = instance.login(1, "errore");

        assertNotNull(manutentore);
        assertNotNull(cliente);
        assertNull(invalido);

        assertTrue(manutentore instanceof Manutentore);
        assertTrue(cliente instanceof Cliente);
    }
    
    @Test 
    public void testRegistrazione(){
        System.out.println("registrazione");
        UtenteDao instance = new UtenteDao();
        Cliente cliente = new Cliente(1,"pwd","mail","nome","cognome",true);
        Utente result = instance.add(cliente);
        assertNotNull(result);
        
        this.createdUser = result;
    }
    
    @Test 
    public void testEliminaUtente(){
        UtenteDao instance = new UtenteDao();
        instance.delete(this.createdUser);
        assertNull(instance.login(this.createdUser.getId(),"pwd"));
    }
    
    @Test
    public void testSgancia(){
        System.out.println("sgancia");
        MorsaDao instance = new MorsaDao();
        instance.sgancia(new Bicicletta(95,new BiciclettaElettrica(false),false));
        Morsa m = instance.getById(55);
        assertNull(m.getBicicletta());
    }
    
    @Test
    public void testAggancia(){
        System.out.println("sgancia");
        MorsaDao instance = new MorsaDao();
        Morsa m = instance.getById(55);
        instance.aggancia(new Bicicletta(95,new BiciclettaElettrica(false),false),m);
        m = instance.getById(55);
        assertNotNull(m.getBicicletta());
    }


}
