/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.mercadonet.demo.Common.CustomException;
import org.springframework.stereotype.Component;

/**
 *
 * @author HP
 */

@Component
public class JwtBalancer {
    
    
    @Autowired
    JwtTokenUtils objtokenu;
    
 
    public boolean Authjwt(String Token){
        if(Token == null || Token.equals("")){
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token no puede ser nulo");
        }
        else{
            try {
                String RealToken = Token.substring(7);
                String Tokencheck = objtokenu.ValidateToken(RealToken);
                if(Tokencheck.equalsIgnoreCase("Valido")){
                    return true;
                }
                else{
                    throw  new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token no esta autorizado" + Tokencheck);
                }
            } catch (StringIndexOutOfBoundsException e) {
                throw  new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token tiene un formato incorrecto");
            }
        }
        
    }
    
}
