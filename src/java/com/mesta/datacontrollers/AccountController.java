/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacontrollers;

import com.mesta.datacommunicators.AccountGetter;

/**
 *
 * @author harm
 */
public class AccountController {

    private static AccountController controller;
    
    private AccountGetter accountGetter;
    
    public static AccountController getController() {
        if (controller == null) {
            controller = new AccountController();
        }
        return controller;
    }
    
    public AccountGetter accountGetter(){
        return accountGetter;
    }

}
