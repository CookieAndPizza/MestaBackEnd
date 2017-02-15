/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.resources;

import com.mesta.datacontrollers.LocationController;
import com.mesta.models.Location;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author harm
 */
@Path("/location")
public class LocationResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PlaceResource
     */
    public LocationResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.mesta.resources.PlaceResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        Stack locations = new Stack();
        try {
            locations = LocationController.getController().locationGetter().getAllLocations();
        } catch (SQLException ex) {
            Logger.getLogger(LocationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return locations.toString();
    }

    /**
     * POST method for updating or creating an instance of PlaceResource
     *
     * @param content representation for the resource
     */
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean putLocation(Location loc) {
        boolean succes = false;
        try {
            System.out.println(loc.toString());
            succes = LocationController.getController().locationSetter().saveLocation(loc);
        } catch (SQLException ex) {
            Logger.getLogger(LocationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
    
    /**
     * POST method for updating or creating an instance of PlaceResource
     *
     * @param content representation for the resource
     */
    @GET
    @Path("/nearby")
    @Consumes(MediaType.APPLICATION_JSON)
    public Stack nearbyLocations() {
        Stack locations = new Stack();
        try {
            locations = LocationController.getController().locationGetter().getNearbyLocations();
        } catch (SQLException ex) {
            Logger.getLogger(LocationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return locations;
    }
}
