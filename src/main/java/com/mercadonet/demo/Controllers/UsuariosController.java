 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Controllers;

// Importaciones necesarias para manejo de excepciones, DTOs, modelos, seguridad y servicios
import com.mercadonet.demo.Common.CustomException;
import com.mercadonet.demo.Dto.DtoInfoLogin;
import com.mercadonet.demo.Dto.DtoLogin;
import com.mercadonet.demo.Models.Usuarios;
import com.mercadonet.demo.Security.JwtBalancer;
import com.mercadonet.demo.Services.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
// Anotación que indica que este controlador devuelve datos JSON o XML
@RestController

// Permite el acceso desde cualquier origen (CORS)
@CrossOrigin("*")

// Define la URL base del controlador
@RequestMapping("/Usuarios/")


public class UsuariosController {
    
     // Inyección de dependencia del servicio de administración
    @Autowired
    UsuariosService objserv;

    // Método POST para manejar el login del administrador en el endpoint /login
    //Recuerda que al ser un post tienes que pasarle un body de tipo "DtoLogin" a continuacion encontraras un ejemplo del body
//    {
//        "usuario": "Usuario1",
//        "contraseña": "1111",
//    }
    @PostMapping("login")
    public ResponseEntity<?> auth(@RequestBody DtoLogin dtolog){
        try {
            // Llama al servicio de autenticación pasando los datos de login
            DtoInfoLogin infoauth = objserv.auth(dtolog);
            
            // Si la autenticación es exitosa, devuelve los datos de login
            if(infoauth != null){
                return ResponseEntity.ok(infoauth);
            }
            else{
                // Si no se pudo generar el token correctamente, lanza una excepción personalizada
                throw new CustomException(HttpStatus.CONFLICT.value(), "El token no se genero correctamente");
            }
        } catch (CustomException e) {
            // Captura la excepción y devuelve un error con el código correspondiente
            return ResponseEntity.status(e.getstatus()).body(e.toString());
        }
    }
}
