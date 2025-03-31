/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Controllers;

// Importaciones necesarias para manejar excepciones personalizadas, DTOs, modelos, seguridad y servicios
import com.mercadonet.demo.Common.CustomException;
import com.mercadonet.demo.Models.Productos;
import com.mercadonet.demo.Security.JwtBalancer;
import com.mercadonet.demo.Services.ProductosService;
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
@RequestMapping("/productos/")

public class ProductosController {
    
     // Inyección de dependencia del servicio que maneja la lógica de los productos
     @Autowired
     private ProductosService objservpro;
     
         // Inyección de dependencia para manejar y verificar los tokens JWT
     @Autowired
     private JwtBalancer objbal;
     
     // Método GET para traer todos los productos desde el endpoint /traerproductos
     /*localhost:8081/productos/traerproductos*/
     @GetMapping("traerproductos")
     public ResponseEntity<?> traerproductos(@RequestHeader (value="Authorization")String Token){
         try {
            // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, trae la lista de productos desde el servicio
                List<Productos> prolist = objservpro.traerproductos();
              // Devuelve la lista de productos con un código de respuesta 200 (OK)
              return ResponseEntity.ok(prolist);
            }else{
              // Si el token no es válido, lanza una excepción personalizada
                 throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token es invalido");
             }
         } catch (CustomException e) {
              // Captura la excepción y devuelve un error con el código correspondiente
             return ResponseEntity.status(e.getstatus()).body(e.toString());
         }
         
    }
     
     // Método GET para traer un producto específico según el nombre desde el endpoint /traerproducto
    //ejemplo del endpoint localhost:8081/productos/traerproducto?Nombre=xxxx
     @GetMapping("traerproducto")
     public ResponseEntity<?> traerproducto(@RequestParam String Nombre, @RequestHeader(value = "Authorization")String Token){
        
          try {
             // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, trae el producto correspondiente desde el servicio              
                Productos pro = objservpro.traerproducto(Nombre);
              // Devuelve el producto con un código de respuesta 200 (OK)
              return ResponseEntity.ok(pro);
            }else{
                 // Si el token no es válido, lanza una excepción personalizada
                 throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token es invalido");
             }
         } catch (CustomException e) {
             // Captura la excepción y devuelve un error con el código correspondiente
             return ResponseEntity.status(e.getstatus()).body(e.toString());
         }
      }
     
    // Método POST para crear un nuevo cliente desde el endpoint /crearproducto
    //ejemplo del endpoint localhost:8081/productos/crearproducto
     @PostMapping("crearproducto")
     public ResponseEntity<?> crearproducto(@RequestBody Productos pro, @RequestHeader (value = "Authorization")String Token){
          
         try {
             // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                 // Si el token es válido, crea el producto utilizando el servicio
                 String resserv = objservpro.crearproducto(pro);
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
     
     // Método PUT para actualizar un producto desde el endpoint /actualizarproducto
    //ejemplo del endpoint localhost:8081/productos/actualizarproducto
     @PutMapping("actualizarproducto")
     public ResponseEntity<?> actualizarproducto(@RequestBody Productos pro, @RequestHeader (value = "Authorization")String Token){
         try {
              // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, actualiza el producto utilizando el servicio
                String actserv = objservpro.actualizarproducto(pro);
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

     
      // Método DELETE para eliminar un producto según su nombre desde el endpoint /eliminarproducto
    //ejemplo del endpoint localhost:8081/productos/eliminarproducto?Nombre=xxxx
      @DeleteMapping("eliminarproducto")
    public ResponseEntity<?> eliminar(@RequestParam String Nombre, @RequestHeader(value = "Authorization") String Token) {
        try {
            // Verifica si el token es válido
            boolean response = objbal.Authjwt(Token);
            if (response) {
                // Si el token es válido, elimina el producto utilizando el servicio
                String eliserv = objservpro.eliminarproducto(Nombre);
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
