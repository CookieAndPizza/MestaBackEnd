/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.models;

import java.io.Serializable;

/**
 *
 * @author harm
 */
public class Location implements Serializable{

    private int id;
    private String name;
    private long longitude;
    private long latitude;
    private String discription;

    public Location(String name, long longitude, long latitude, String discription) {
        this.id = -1;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.discription = discription;
    }

    public Location(int id, String name, long longitude, long latitude, String discription) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.discription = discription;
    }

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
}
