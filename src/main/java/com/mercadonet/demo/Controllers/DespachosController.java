/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Controllers;

// Importaciones necesarias para manejar excepciones personalizadas, DTOs, modelos, seguridad y servicios
import com.mercadonet.demo.Common.CustomException;
import com.mercadonet.demo.Models.Despachos;
import com.mercadonet.demo.Security.JwtBalancer;
import com.mercadonet.demo.Services.DespachosService;
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
@RequestMapping("/despachos/")

public class DespachosController {
    
 // Inyección de dependencia del servicio que maneja la lógica de los despachos
     @Autowired
     private DespachosService objservdes;
     
         // Inyección de dependencia para manejar y verificar los tokens JWT
     @Autowired
     private JwtBalancer objbal;
     
     // Método GET para traer todos los despachos desde el endpoint /traerdespachos
     /*localhost:8081/despachos/traerdespachos*/
     @GetMapping("traerdespachos")
     public ResponseEntity<?> traerdespachos(@RequestHeader (value="Authorization")String Token){
         try {
            // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, trae la lista de despachos desde el servicio
                List<Despachos> deslist = objservdes.traerdespachos();
              // Devuelve la lista de despachos con un código de respuesta 200 (OK)
              return ResponseEntity.ok(deslist);
            }else{
              // Si el token no es válido, lanza una excepción personalizada
                 throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token es invalido");
             }
         } catch (CustomException e) {
              // Captura la excepción y devuelve un error con el código correspondiente
             return ResponseEntity.status(e.getstatus()).body(e.toString());
         }
         
    }
     
     // Método GET para traer un despacho específico según la guia desde el endpoint /traerdespacho
    //ejemplo del endpoint localhost:8081/despachos/traerdespacho?Guia=G001
     @GetMapping("traerdespacho")
     public ResponseEntity<?> traerdespacho(@RequestParam String Guia, @RequestHeader(value = "Authorization")String Token){
        
          try {
             // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, trae el despacho correspondiente desde el servicio              
                Despachos des = objservdes.traerdespacho(Guia);
              // Devuelve el despacho con un código de respuesta 200 (OK)
              return ResponseEntity.ok(des);
            }else{
                 // Si el token no es válido, lanza una excepción personalizada
                 throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token es invalido");
             }
         } catch (CustomException e) {
             // Captura la excepción y devuelve un error con el código correspondiente
             return ResponseEntity.status(e.getstatus()).body(e.toString());
         }
      }
     
    // Método POST para crear un nuevo despacho desde el endpoint /creardespacho
    //ejemplo del endpoint localhost:8081/despachos/creardespacho
     @PostMapping("creardespacho")
     public ResponseEntity<?> creardespacho(@RequestBody Despachos des, @RequestHeader (value = "Authorization")String Token){
          
         try {
             // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                 // Si el token es válido, crea el despacho utilizando el servicio
                 String resserv = objservdes.creardespacho(des);
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
     
     // Método PUT para actualizar un despacho desde el endpoint /actualizardespacho
    //ejemplo del endpoint localhost:8081/despachos/actualizardespacho
     @PutMapping("actualizardespacho")
     public ResponseEntity<?> actualizardespacho(@RequestBody Despachos des, @RequestHeader (value = "Authorization")String Token){
         try {
              // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, actualiza el despacho utilizando el servicio
                String actserv = objservdes.actualizardespacho(des);
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

     
      // Método DELETE para eliminar un despacho según su guia desde el endpoint /eliminardespacho
    //ejemplo del endpoint localhost:8081/despachos/eliminardespacho?Guia=G001
      @DeleteMapping("eliminardespacho")
    public ResponseEntity<?> eliminar(@RequestParam String Guia, @RequestHeader(value = "Authorization") String Token) {
        try {
            // Verifica si el token es válido
            boolean response = objbal.Authjwt(Token);
            if (response) {
                // Si el token es válido, elimina el despacho utilizando el servicio
                String eliserv = objservdes.eliminardespacho(Guia);
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
