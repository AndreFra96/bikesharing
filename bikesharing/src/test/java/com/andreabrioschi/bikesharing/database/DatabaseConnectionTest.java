/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.andreabrioschi.bikesharing.database;

import java.sql.Connection;
import java.sql.ResultSet;
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
public class DatabaseConnectionTest {
    
    public DatabaseConnectionTest() {
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
     * Test of getInstance method, of class Database.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        Database first = Database.getInstance();
        Database second = Database.getInstance();
        assertNotNull(first);
        assertEquals(first, second);
    }

    /**
     * Test of executeQuery method, of class Database.
     */
    @Test
    public void testExecuteQuery() throws Exception {
        System.out.println("executeQuery");
        String query = "SELECT 1+1";
        Database instance = Database.getInstance();
        ResultSet result = instance.executeQuery(query);
        assertTrue(result.next());
        assertEquals(result.getInt("1+1"),2);
    }

    /**
     * Test of getConnection method, of class Database.
     */
    @Test
    public void testGetConnection() throws Exception {
        System.out.println("getConnection");
        Database instance = Database.getInstance();
        Connection result = instance.getConnection();
        assertTrue(result.isValid(0));
    }

    
}
