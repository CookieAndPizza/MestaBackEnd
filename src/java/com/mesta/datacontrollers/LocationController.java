/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacontrollers;

import com.mesta.datacommunicators.CategoryGetter;
import com.mesta.datacommunicators.LocationGetter;
import com.mesta.datacommunicators.LocationSetter;

/**
 *
 * @author harm
 */
public class LocationController {
    
    private static LocationController controller;
    
    private final LocationSetter locationSetter;
    private final LocationGetter locationGetter;
    private final CategoryGetter categoryGetter;
    
    private LocationController() {
        this.locationSetter = new LocationSetter();
        this.locationGetter = new LocationGetter();
        this.categoryGetter = new CategoryGetter();
    }
    
    public static LocationController getController(){
        if (controller == null){
            controller = new LocationController();
        }
        return controller;
    }
    
    public LocationSetter locationSetter(){
        return locationSetter;
    }
    
    public LocationGetter locationGetter(){
        return this.locationGetter;
    }
    
    public CategoryGetter categoryGetter(){
        return this.categoryGetter;
    }
}
