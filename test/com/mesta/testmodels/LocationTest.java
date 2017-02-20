/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.testmodels;

import com.mesta.models.Location;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author harm
 */
public class LocationTest {

    static Location loc;

    public LocationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        loc = new Location(1, "testLoc", 10, 20, "testDescription");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getIdTest() {
        assertEquals(1, loc.getId());
    }
    
    @Test
    public void getNameTest(){
        assertEquals("testLoc", loc.getName());
    }
    
    @Test
    public void setNameTest(){
        String newName = "newName";
        loc.setName(newName);
        assertEquals(newName, loc.getName());
    }
    
    @Test
    public void getLongitudeTest(){
        assertEquals(10, loc.getLongitude());
    }
    
    @Test
    public void setLongitudeTest(){
        double longitude = 15;
        loc.setLongitude(longitude);
        assertEquals(longitude, loc.getLongitude());
    }
    
    @Test
    public void getLatitudeTest(){
        assertEquals(20, loc.getLatitude());
    }
    
    @Test
    public void setLatitudeTest(){
        double latitude = 25;
        loc.setLongitude(latitude);
        assertEquals(latitude, loc.getLatitude());
    }
    
    @Test
    public void getDescriptionTest(){
        assertEquals("testDescription", loc.getDescription());
    }
    
    @Test
    public void setDescriptionTest(){
        String description = "newDescription";
        loc.setDescription(description);
        assertEquals(description, loc.getDescription());
    }
}
