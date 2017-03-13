/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.resources;

import com.mesta.datacommunicators.DatabaseInfo;
import com.mesta.datacontrollers.AccountController;
import com.mesta.models.Account;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.CookieParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author harm
 */
@Path("account")
public class AccountResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Account
     */
    public AccountResource() {
    }

    /**
     * Retrieves representation of an instance of com.mesta.resources.Account
     * @return an instance of java.lang.String
     */
    @Path("/fblogin/{ID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("ID") String loginID) {
        Account account = null;
        try {
            account = AccountController.getController().accountGetter().login(loginID);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(AccountResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        NewCookie loginCookie = new NewCookie("fbLoginID", loginID);
        NewCookie tokenCookie = new NewCookie("token", account.getToken().getToken());
        return Response.ok(account.toString(), MediaType.APPLICATION_JSON).cookie(loginCookie).cookie(tokenCookie).build();
    }
    
    @Path("/fblogout")
    @POST
    public Response logout(@CookieParam("fbLoginID") Cookie fbLogin, @CookieParam("token") Cookie token){
        try{
            DatabaseInfo.DatabaseRepsonse succes = AccountController.getController().accountGetter().logout(fbLogin.getValue(), token.getValue());
            return Response.ok(String.valueOf(succes)).build();
        } catch (SQLException ex) {
            Logger.getLogger(LocationResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.serverError().build();
    }
}
