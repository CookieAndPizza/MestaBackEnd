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
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author harm
 */
public class Location implements Serializable {

    private int id;
    private String name;
    private long longitude;
    private long latitude;
    private String discription;
    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
    
    public void addImage(String path){
        images.add(path);
    }

    public Location(String name, long longitude, long latitude, String discription) {
        this.images = new ArrayList<>();
        this.id = -1;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.discription = discription;
    }

    public Location(int id, String name, long longitude, long latitude, String discription) {
        this.images = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.discription = discription;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("ID", this.id);
            object.put("Name", this.name);
            object.put("Longitude", this.longitude);
            object.put("Latitude", this.latitude);
            object.put("Description", this.discription);
            int i = 1;
            for(String s : this.images){
                object.put("image" + i, s);
                i++;
            }
        } catch (JSONException ex) {
            System.out.println(ex);
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }

        return object.toString();
    }
}
