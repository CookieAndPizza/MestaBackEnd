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
import org.json.JSONObject;

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
     *
     * @return an instance of java.lang.String
     */
    @Path("/fblogin")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@CookieParam("fbLoginID") String loginID, @CookieParam("AccessToken") String accessToken) {
        Account account = null; 
        try {
            account = AccountController.getController().accountGetter().login(loginID, accessToken);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(AccountResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(AccountResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (account == null) {
            return Response.ok("{\"success\":\"false\"}").build();
        }
        
        NewCookie tokenCookie = new NewCookie("token", account.getToken().getToken(), "/", ".the-mesta.com", "Comment", -1, true);
        NewCookie facebookID = new NewCookie("fbLoginID", account.getExternalID(), "/", ".the-mesta.com", "Comment", -1, true);
        return Response.ok("{\"success\":\"true\"}", MediaType.APPLICATION_JSON).cookie(tokenCookie).cookie(facebookID).build();
    }

    @Path("/fblogout")
    @POST
    public Response logout(@CookieParam("ID") Cookie loginID, @CookieParam("AccessToken") Cookie token) {
        try {
            DatabaseInfo.DatabaseRepsonse succes = AccountController.getController().accountGetter().logout(loginID.getValue(), token.getValue());
            return Response.ok(String.valueOf(succes)).build();
        } catch (SQLException ex) {
            Logger.getLogger(AccountResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(AccountResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.serverError().build();
    }
}
