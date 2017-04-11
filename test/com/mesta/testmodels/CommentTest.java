/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.testmodels;

import com.mesta.models.Comment;
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
public class CommentTest {

    private Comment comment;

    public CommentTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.comment = new Comment();
        this.comment = new Comment("comment", "title");
        this.comment.setLocationID(1);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getCommentTest() {
        String expected = "comment";
        assertEquals(expected, comment.getComment());
    }

    @Test
    public void getFacebookIDTest() {
        String expected = "facebookid";
        assertEquals(expected, comment.getfaceBookID());
    }

    @Test
    public void getLocationIDTest() {
        String expected = "1";
        assertEquals(expected, comment.getLocationID());
    }

    @Test
    public void setCommentTest() {
        String expected = "newcomment";
        comment.setComment(expected);
        assertEquals(expected, comment.getComment());
    }

    @Test
    public void setFacebookIDTest() {
        String expected = "newID";
        comment.setfaceBookID(expected);
        assertEquals(expected, comment.getfaceBookID());
    }
    
    @Test
    public void toStringTest(){
        String expected = "facebookid; title; comment";
        assertEquals(expected, comment.toString());
    }
}
