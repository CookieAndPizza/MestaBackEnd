/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author harm
 */
public class Location implements Serializable {

    private int id;
    private String name;
    private double longitude;
    private double latitude;
    private String description;
    private List<String> images;
    private List<Comment> comments;
    private List<String> tags;
    private List<String> likes;
    private Category category;

    public List<Comment> getComments() {
        return comments;
    }

    public List<String> getTags() {
        return tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String discription) {
        this.description = discription;
    }

    public void addImage(String path) {
        images.add(path);
    }
        
    public List<String> getImages(){
        return this.images;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }
    
    public void addLike(String fbID){
        this.likes.add(fbID);
    }

    public Location() {
    }

    public Location(String name, double longitude, double latitude, String discription) {
        this.comments = new ArrayList<>();
        this.images = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.id = -1;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = discription;
        this.category = Category.None;
    }

    public Location(int id, String name, double longitude, double latitude, String discription) {
        this.comments = new ArrayList<>();
        this.images = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = discription;
        this.category = Category.None;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("id", this.id);
            object.put("name", this.name);
            object.put("longitude", this.longitude);
            object.put("latitude", this.latitude);
            object.put("description", this.description);
            object.put("images", images);
            
            JSONArray commentArray = new JSONArray();
            for(Comment c : comments){
                commentArray.put(new JSONObject(c).put("facebookID", c.getfaceBookID()));
            }
            
            object.put("comments", commentArray);
            object.put("tags", tags);
            object.put("category", category.toString());
            object.put("likes", likes);
        } catch (JSONException ex) {
            System.out.println(ex);
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }

        return object.toString();
    }
    
    public enum Category {
        None,
        Nature,
        View,
        Urban,
        Architecture,
        Picnic,
        Technology
    }
}
