/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Controllers;

// Importaciones necesarias para manejar excepciones personalizadas, DTOs, modelos, seguridad y servicios
import com.mercadonet.demo.Common.CustomException;
import com.mercadonet.demo.Models.Pedidos;
import com.mercadonet.demo.Security.JwtBalancer;
import com.mercadonet.demo.Services.PedidosService;
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
@RequestMapping("/pedidos/")

public class PedidosController {
     // Inyección de dependencia del servicio que maneja la lógica de los pedidos
     @Autowired
     private PedidosService objservped;
     
         // Inyección de dependencia para manejar y verificar los tokens JWT
     @Autowired
     private JwtBalancer objbal;
     
     // Método GET para traer todos los pedidos desde el endpoint /traerpedidos
     /*localhost:8081/pedidos/traerpedidos*/
     @GetMapping("traerpedidos")
     public ResponseEntity<?> traerpedidos(@RequestHeader (value="Authorization")String Token){
         try {
            // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, trae la lista de pedidos desde el servicio
                List<Pedidos> pedlist = objservped.traerpedidos();
              // Devuelve la lista de pedidos con un código de respuesta 200 (OK)
              return ResponseEntity.ok(pedlist);
            }else{
              // Si el token no es válido, lanza una excepción personalizada
                 throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token es invalido");
             }
         } catch (CustomException e) {
              // Captura la excepción y devuelve un error con el código correspondiente
             return ResponseEntity.status(e.getstatus()).body(e.toString());
         }
         
    }
     
     // Método GET para traer un pedido específico según el numero de pedido desde el endpoint /traerpedido
    //ejemplo del endpoint localhost:8081/pedidos/traerpedido?Nropedido=001
     @GetMapping("traercliente")
     public ResponseEntity<?> traerpedido(@RequestParam String Nropedido, @RequestHeader(value = "Authorization")String Token){
        
          try {
             // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, trae el pedido correspondiente desde el servicio              
                Pedidos ped = objservped.traerpedido(Nropedido);
              // Devuelve el pedido con un código de respuesta 200 (OK)
              return ResponseEntity.ok(ped);
            }else{
                 // Si el token no es válido, lanza una excepción personalizada
                 throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "El token es invalido");
             }
         } catch (CustomException e) {
             // Captura la excepción y devuelve un error con el código correspondiente
             return ResponseEntity.status(e.getstatus()).body(e.toString());
         }
      }
     
    // Método POST para crear un nuevo pedido desde el endpoint /crearpedido
    //ejemplo del endpoint localhost:8081/pedidos/crearpedido
     @PostMapping("crearpedido")
     public ResponseEntity<?> crearpedido(@RequestBody Pedidos ped, @RequestHeader (value = "Authorization")String Token){
          
         try {
             // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                 // Si el token es válido, crea el pedido utilizando el servicio
                 String resserv = objservped.crearpedido(ped);
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
     
     // Método PUT para actualizar un pedido desde el endpoint /actualizarpedido
    //ejemplo del endpoint localhost:8081/pedidos/actualizarpedido
     @PutMapping("actualizarpedido")
     public ResponseEntity<?> actualizarpedido(@RequestBody Pedidos ped, @RequestHeader (value = "Authorization")String Token){
         try {
              // Verifica si el token es válido
             boolean response = objbal.Authjwt(Token);
             if(response){
                // Si el token es válido, actualiza el pedido utilizando el servicio
                String actserv = objservped.actualizarpedido(ped);
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

     
      // Método DELETE para eliminar un pedido según su numero de pedido desde el endpoint /eliminarpedido
    //ejemplo del endpoint localhost:8081/pedidos/eliminarpedido?Nropedido=001
      @DeleteMapping("eliminarpedido")
    public ResponseEntity<?> eliminar(@RequestParam String Nropedido, @RequestHeader(value = "Authorization") String Token) {
        try {
            // Verifica si el token es válido
            boolean response = objbal.Authjwt(Token);
            if (response) {
                // Si el token es válido, elimina el pedido utilizando el servicio
                String eliserv = objservped.eliminarpedido(Nropedido);
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
