/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Controllers;

// Importaciones necesarias para manejar excepciones personalizadas, DTOs, modelos, seguridad y servicios
import com.mercadonet.demo.Common.CustomException;
import com.mercadonet.demo.Models.Clientes;
import com.mercadonet.demo.Security.JwtBalancer;
import com.mercadonet.demo.Services.ClientesService;
import java.util.List;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
// Anotación que indica que es un controlador que retorna directamente datos JSON o XML, en vez de vistas
@RestController

// Permite el acceso desde cualquier origen (CORS)
@CrossOrigin("*")

// Define la URL base del controlador
@RequestMapping("/clientes/")

public class ClientesController {

        // Inyección de dependencia del servicio que maneja la lógica de los clientes
     @Autowired
     private ClientesService objservcli;
     
         // Inyección de dependencia para manejar y verificar los tokens JWT
     @Autowired
     private JwtBalancer objbal;
     
     // Método GET para traer todos los clientes desde el endpoint /traerclientes
     /*localhost:8080/clientes/traerclientes*/
     @GetMapping("traerclientes")
     public ResponseEntity<?> traerclientes(@RequestHeader (value="Authorization")String Token){
         try {
            // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, trae la lista de clientes desde el servicio
                List<Clientes> clilist = objservcli.traerclientes();
              // Devuelve la lista de clientes con un código de respuesta 200 (OK)
              return ResponseEntity.ok(clilist);
            }else{
              // Si el token no es válido, lanza una excepción personalizada
                 throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token es invalido");
             }
         } catch (CustomException e) {
              // Captura la excepción y devuelve un error con el código correspondiente
             return ResponseEntity.status(e.getstatus()).body(e.toString());
         }
         
    }
     
     // Método GET para traer un cliente específico según la identificacion desde el endpoint /traercliente
    //ejemplo del endpoint localhost:8080/clientes/traercliente?Identificacion=1111
     @GetMapping("traercliente")
     public ResponseEntity<?> traercliente(@RequestParam String Identificacion, @RequestHeader(value = "Authorization")String Token){
        
          try {
             // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, trae el cliente correspondiente desde el servicio              
                Clientes cli = objservcli.traercliente(Identificacion);
              // Devuelve el cliente con un código de respuesta 200 (OK)
              return ResponseEntity.ok(cli);
            }else{
                 // Si el token no es válido, lanza una excepción personalizada
                 throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token es invalido");
             }
         } catch (CustomException e) {
             // Captura la excepción y devuelve un error con el código correspondiente
             return ResponseEntity.status(e.getstatus()).body(e.toString());
         }
      }
     
    // Método POST para crear un nuevo cliente desde el endpoint /crearcliente
    //ejemplo del endpoint localhost:8080/clientes/crearcliente
     @PostMapping("crearcliente")
     public ResponseEntity<?> crearcliente(@RequestBody Clientes cli, @RequestHeader (value = "Authorization")String Token){
          
         try {
             // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                 // Si el token es válido, crea el cliente utilizando el servicio
                 String resserv = objservcli.crearcliente(cli);
                 // Devuelve el resultado de la creación con un código de respuesta 200 (OK)
                 return ResponseEntity.ok(resserv);
            }else{
                // Si el token no es válido, lanza una excepción personalizada
                 throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token es invalido");
            
             }
         } catch (CustomException e) {
            // Captura la excepción y devuelve un error con el código correspondiente
             return ResponseEntity.status(e.getstatus()).body(e.toString());
         }
}
     
     // Método PUT para actualizar un cliente desde el endpoint /actualizarcliente
    //ejemplo del endpoint localhost:8080/clientes/actualizarcliente
     @PutMapping("actualizarcliente")
     public ResponseEntity<?> actualizarcliente(@RequestBody Clientes cli, @RequestHeader (value = "Authorization")String Token){
         try {
              // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, actualiza el cliente utilizando el servicio
                String actserv = objservcli.actualizarcliente(cli);
                // Devuelve el resultado de la actualizacion con un código de respuesta 200 (OK)
                return ResponseEntity.ok(actserv);
           }else{
                // Si el token no es válido, lanza una excepción personalizada
               throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token es invalido");
             }
         } catch (CustomException e) {
            // Captura la excepción y devuelve un error con el código correspondiente
             return ResponseEntity.status(e.getstatus()).body(e.toString());
         }
    } 

     
      // Método DELETE para eliminar un cliente según su identificacion desde el endpoint /eliminarcliente
    //ejemplo del endpoint localhost:8080/clientes/eliminarcliente?Identificacion=1111
      @DeleteMapping("eliminarcliente")
    public ResponseEntity<?> eliminar(@RequestParam String Identificacion, @RequestHeader(value = "Authorization") String Token) {
        try {
            // Verifica si el token es válido
            boolean response = objbal.Authjwt(Token);
            if (response) {
                // Si el token es válido, elimina el cliente utilizando el servicio
                String eliserv = objservcli.eliminarcliente(Identificacion);
                // Devuelve el resultado de la eliminación con un código de respuesta 200 (OK)
                return ResponseEntity.ok(eliserv);
            } else {
                // Si el token no es válido, lanza una excepción personalizada
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token es invalido");
            }
        } catch (CustomException e) {
            // Captura la excepción y devuelve un error con el código correspondiente
            return ResponseEntity.status(e.getstatus()).body(e.toString());
        }
    } 
   
}

