/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.resources;

import com.mesta.datacontrollers.CommentController;
import com.mesta.datacontrollers.LocationController;
import com.mesta.models.Location;
import com.mesta.models.Comment;
import com.mesta.models.Token;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response getAll() {
        ArrayDeque locations = new ArrayDeque();
        Token token = new Token();
        try {
            locations = LocationController.getController().locationGetter().getAllLocations();
        } catch (SQLException ex) {
            Logger.getLogger(LocationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok(locations.toString(), MediaType.APPLICATION_JSON).build();
    }

    /**
     * POST method for updating or creating an instance of PlaceResource
     *
     * @param content representation for the resource
     */
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putLocation(Location loc) {
        boolean succes = false;
        try {
            succes = LocationController.getController().locationSetter().saveLocation(loc);
        } catch (SQLException ex) {
            Logger.getLogger(LocationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok(String.valueOf(succes)).build();
    }

    @GET
    @Path("{ID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("ID") int ID) {
        Location location = null;
        try {
            location = LocationController.getController().locationGetter().getOneLocation(ID);

            if (location == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

        } catch (SQLException ex) {
            Logger.getLogger(LocationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (location != null) {
            return Response.ok(location.toString(), MediaType.APPLICATION_JSON).build();
        }
        return null;
    }

    /**
     * POST method for updating or creating an instance of PlaceResource
     *
     * @param content representation for the resource
     */
    @GET
    @Path("/nearby/{Lat}/{Long}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response nearbyLocations(@PathParam("Lat") double lat, @PathParam("Long") double lon) {
        ArrayDeque locations = new ArrayDeque();
        try {
            locations = LocationController.getController().locationGetter().getNearbyLocations(lat, lon);
        } catch (SQLException ex) {
            Logger.getLogger(LocationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok(locations.toString(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/comment/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putComment(Comment com) {
        boolean succes = false;
        try {
            succes = CommentController.getController().commentSetter().saveComment(com);
            System.out.println(succes);
            
        } catch (SQLException ex) {
            Logger.getLogger(LocationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok(String.valueOf(succes)).build();
    }
}
