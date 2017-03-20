/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.testmodels;

import com.mesta.models.Comment;
import com.mesta.models.Location;
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
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        loc = new Location(1, "testLoc", 10, 20, "testDescription");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getIdTest() {
        assertEquals(1, loc.getId());
    }

    @Test
    public void getNameTest() {
        assertEquals("testLoc", loc.getName());
    }

    @Test
    public void setNameTest() {
        String newName = "newName";
        loc.setName(newName);
        assertEquals(newName, loc.getName());
    }

    @Test
    public void getLongitudeTest() {
        assertEquals(10, loc.getLongitude(), 0);
    }

    @Test
    public void setLongitudeTest() {
        double longitude = 15;
        loc.setLongitude(longitude);
        assertEquals(longitude, loc.getLongitude(), 0);
    }

    @Test
    public void getLatitudeTest() {
        assertEquals(20, loc.getLatitude(), 0);
    }

    @Test
    public void setLatitudeTest() {
        double latitude = 25;
        loc.setLatitude(latitude);
        assertEquals(latitude, loc.getLatitude(), 0);
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("testDescription", loc.getDescription());
    }

    @Test
    public void setDescriptionTest() {
        String description = "newDescription";
        loc.setDescription(description);
        assertEquals(description, loc.getDescription());
    }

    @Test
    public void addImageTest() {
        String image = "testimageurl";
        loc.addImage(image);
        int expected = 1;
        assertEquals(expected, loc.getImages().size());
    }
    
    @Test
    public void getCategoryTest(){
        Location.Category expected = Location.Category.None;
        assertEquals(expected, loc.getCategory());
    }
    
    @Test
    public void getTags(){
        String tag = "tag";
        loc.addTag(tag);
        int expected = 1;
        assertEquals(expected, loc.getTags().size());
    }
    
    @Test
    public void getComments(){
        Comment comment = new Comment("1", "thisisacomment", "thisisatitle");
        loc.addComment(comment);
        int expected = 1;
        assertEquals(expected, loc.getComments().size());
    }
}
