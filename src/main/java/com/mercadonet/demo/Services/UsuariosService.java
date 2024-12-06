/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Services;

// Importaciones necesarias para excepciones, DTOs, modelos y repositorios
import com.mercadonet.demo.Common.CustomException;
import com.mercadonet.demo.Dto.DtoInfoLogin;
import com.mercadonet.demo.Dto.DtoLogin;
import com.mercadonet.demo.Models.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.mercadonet.demo.Repositories.UsuariosRepository;
import com.mercadonet.demo.Security.JwtTokenUtils;

/**
 *
 * @author HP
 */

// Anotación que indica que esta clase es un servicio de Spring
@Service
public class UsuariosService {
    
    // Inyección de dependencia del repositorio de administradores
    @Autowired
    UsuariosRepository objrep; 
    
    // Inyección de dependencia para la utilidad de generación de tokens JWT
    @Autowired
    JwtTokenUtils objtokutils;
    
        // Método para autenticar un usuario
    public DtoInfoLogin auth(DtoLogin log){
            
        // Busca a los usuarios por su nombre de usuario
        Usuarios infousuarios = objrep.findByUsuario(log.getUsuario());
        if(infousuarios != null){
            // Verifica si la contraseña es correcta
            if(!infousuarios.getContraseña().equals(log.getContraseña())){
                // Si la contraseña es incorrecta, lanza una excepción personalizada
                throw new CustomException(HttpStatus.CONFLICT.value(), "La contraseña es incorrecta");
            }
            else{
                // Si la contraseña es correcta, genera un token JWT
                String token = objtokutils.GenerateToken(log.getUsuario());
                DtoInfoLogin info = new DtoInfoLogin(); // Crea un nuevo objeto DTO para la información de inicio de sesión
                info.setToken(token); // Establece el token en el DTO
                info.setUsuariosinfo(log); // Establece la información del usuario en el DTO
                return info; // Devuelve el DTO con la información del usuario y el token
            }
        }
        else{
            // Si no se encuentra al usuario, lanza una excepción personalizada
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "Usuario no encontrado");
        }
    }
 }
    
