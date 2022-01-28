/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.andreabrioschi.bikesharing.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class ModelsTest {

    public ModelsTest() {
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
     * Test of getCosto method, of class Noleggio.
     */
    @Test
    public void testCostoNoleggio() {
        System.out.println("getCosto");

        Rastrelliera r = new Rastrelliera(1, 1.1, 1.1, "test");

        Bicicletta biciElettrica = new Bicicletta(1, new BiciclettaElettrica(false), false);
        Bicicletta biciClassica = new Bicicletta(1, new BiciclettaClassica(), false);

        LocalDateTime start = LocalDateTime.of(2022, 1, 18, 15, 30);

        //Costo bici elettrica
        Noleggio n0elettrica = new Noleggio(1, biciElettrica, r, start, start);
        Noleggio n60elettrica = new Noleggio(1, biciElettrica, r, start, LocalDateTime.of(2022, 1, 18, 16, 30));
        Noleggio n98elettrica = new Noleggio(1, biciElettrica, r, start, LocalDateTime.of(2022, 1, 18, 17, 8));


        assertTrue(n0elettrica.getCosto() == 0.7);
        assertTrue(n60elettrica.getCosto() == 1.7);
        assertTrue(n98elettrica.getCosto() == 2.2);

        //Costo bici classica
        Noleggio n0classica = new Noleggio(1, biciClassica, r, start, start);
        Noleggio n60classica = new Noleggio(1, biciClassica, r, start, LocalDateTime.of(2022, 1, 18, 16, 30));
        Noleggio n98classica = new Noleggio(1, biciClassica, r, start, LocalDateTime.of(2022, 1, 18, 17, 8));
        assertTrue(n0classica.getCosto() == 0);
        assertTrue(n60classica.getCosto() == 0.8);
        assertTrue(n98classica.getCosto() == 1.05);

    }

    public void testAbbonamentoScaduto() {
        System.out.println("scaduto");
        LocalDate start = LocalDate.now();
        //Creo cliente
        Cliente cliente = new Cliente(1, "pwd", "mail", "nome", "cognome", true);
        //Abbonamento annuale
        Abbonamento annualeValido = new AbbonamentoAnnuale(1, cliente, start, 0);
        Abbonamento annualeScaduto = new AbbonamentoAnnuale(1, cliente, start.minusYears(2), 0);
        assertFalse(annualeValido.scaduto());
        assertTrue(annualeScaduto.scaduto());
        //Abbonamento settimanale inattivo
        Abbonamento settimanaleInattivo = new AbbonamentoSettimanale(1, cliente, start, 0);
        assertFalse(settimanaleInattivo.scaduto());
        //Abbonamento giornaliero inattivo
        Abbonamento giornalieroInattivo = new AbbonamentoGiornaliero(1, cliente, start, 0);
        assertFalse(giornalieroInattivo.scaduto());
        //Aggiungo noleggio che attiva abbonamenti occasionali
        Rastrelliera r = new Rastrelliera(1, 1.1, 1.1, "test");
        Bicicletta biciElettrica = new Bicicletta(1, new BiciclettaElettrica(false), false);
        LocalDateTime dataNoleggio = LocalDateTime.now().minusDays(8);
        Noleggio noleggio = new Noleggio(1, biciElettrica, r, dataNoleggio, dataNoleggio);
        cliente.addNoleggio(noleggio);
        //Abbonamento settimanale scaduto
        Abbonamento settimanaleScaduto = new AbbonamentoSettimanale(1, cliente, start, 0);
        assertTrue(settimanaleScaduto.scaduto());
        //Abbonamento giornaliero scaduto
        Abbonamento giornalieroScaduto = new AbbonamentoGiornaliero(1, cliente, start, 0);
        assertTrue(giornalieroScaduto.scaduto());

    }

    @Test
    public void testCostoAbbonamento() {
        System.out.println("getPrice");
        LocalDate start = LocalDate.now();
        Cliente cliente = new Cliente(1, "pwd", "mail", "nome", "cognome", true);
        //Abbonamento annuale
        Abbonamento annuale = new AbbonamentoAnnuale(1, cliente, start, 0);
        //Abbonamento settimanale
        Abbonamento settimanale = new AbbonamentoSettimanale(1, cliente, start, 0);
        //Abbonamento giornaliero
        Abbonamento giornaliero = new AbbonamentoGiornaliero(1, cliente, start, 0);

        assertEquals(annuale.getPrezzo(), 36);
        assertEquals(settimanale.getPrezzo(), 9);
        assertEquals(giornaliero.getPrezzo(), 4.5);

    }

    @Test
    public void testPostoDisponibile() {
        System.out.println("haPosto");
        Rastrelliera rastrelliera = new Rastrelliera(1, 1.1, 1.1, "test");

        assertFalse(rastrelliera.haPosto(new BiciclettaElettrica(true)));
        assertFalse(rastrelliera.haPosto(new BiciclettaClassica()));

        rastrelliera.aggiungiMorsa(new Morsa(1, TipoMorsa.CLASSICA));
        rastrelliera.aggiungiMorsa(new Morsa(2, TipoMorsa.ELETTRICA));
        
        assertTrue(rastrelliera.haPosto(new BiciclettaElettrica(true)));
        assertTrue(rastrelliera.haPosto(new BiciclettaClassica()));
    }

}
