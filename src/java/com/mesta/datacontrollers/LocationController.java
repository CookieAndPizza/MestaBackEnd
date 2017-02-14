/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacontrollers;

import com.mesta.datacommunicators.LocationSetter;

/**
 *
 * @author harm
 */
public class LocationController {
    
    private static LocationController controller;
    
    private LocationSetter locationSetter;
    
    private LocationController() {
        this.locationSetter = new LocationSetter();
    }
    
    public static LocationController getController(){
        if (controller == null){
            controller = new LocationController();
        }
        return controller;
    }
    
    public LocationSetter locationGetter(){
        return locationSetter;
    }
}
