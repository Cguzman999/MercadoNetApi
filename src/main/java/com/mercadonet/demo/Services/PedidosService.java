/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Services;

// Importaciones necesarias para excepciones, modelos y repositorios
import com.mercadonet.demo.Common.CustomException;
import com.mercadonet.demo.Models.Pedidos;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mercadonet.demo.Repositories.PedidosRepository;
import org.springframework.http.HttpStatus;

/**
 *
 * @author HP
 */
/*Le indica a springboot que aqui tenemos un servicio*/
@Service

public class PedidosService {
     // Inyección de dependencia del repositorio de pedidos
    @Autowired
    private PedidosRepository objrepped;
    
    // Método para obtener la lista de todos los pedidos
    public List<Pedidos> traerpedidos(){
       return objrepped.findAll();  
    } 
    
    // Método para obtener un pedido específico basado en su nropedido
    public Pedidos traerpedido(String Nropedido){
        Pedidos infopedido = objrepped.findByNropedido(Nropedido); // Busca el pedido por su Nropedido
        if (infopedido != null){
        return infopedido; // Si el pedido existe, se devuelve
        }
        else{
        // Si no se encuentra, lanza una excepción personalizada
        throw new CustomException(HttpStatus.NOT_FOUND.value(), "El pedido no fue encontrado en la base de datos");

    }
  }

 // Método para crear un nuevo pedido
    public String crearpedido(Pedidos ped){
        // Verifica si ya existe un pedido con el mismo numero de pedido
        Pedidos infoped = objrepped.findByNropedido(ped.getNropedido());
        if(infoped == null){
            objrepped.save(ped); // Si no existe, guarda el nuevo 
            return "El pedido se guardo exitosamente"; // Mensaje de éxito
        }
        else{
            // Si ya existe, lanza una excepción personalizada
            throw new CustomException(HttpStatus.CONFLICT.value(), "El pedido ya existe en la base de datos");
        } 
    }
    // Método para actualizar un nuevo pedido
    public String actualizarpedido(Pedidos ped){
        // Verifica si ya existe un pedido con el mismo numero de pedido
        Pedidos infoped = objrepped.findByNropedido(ped.getNropedido());
        if(infoped != null){
           // Si el pedido existe, actualiza sus datos
            infoped.setNropedido(ped.getNropedido());
            infoped.setDescripcion(ped.getDescripcion());
            infoped.setFecha(ped.getFecha());
            infoped.setPrecio(ped.getPrecio());
            objrepped.save(infoped);  // Guarda los cambios
            return "el pedido se actualizo exitosamente";  // Mensaje de éxito
        }
        else{
            // Si ya existe, lanza una excepción personalizada
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "El pedido no fue encontrado para actualizar");
        } 
    }
    
    
    // Método para eliminar un pedido basado en su numero de pedido
    public String eliminarpedido(String Nropedido){
        // Busca el pedido por su nropedido y lo guarda en el objeto del modelo llamado infoped
        Pedidos infoped = objrepped.findByNropedido(Nropedido);
        if(infoped != null){
            objrepped.deleteById(infoped.getIdPedido()); // Si el pedido existe, lo elimina
            return "El pedido se elimino correctamente"; // Mensaje de éxito
        }
        else{
            // Si no se encuentra, lanza una excepción personalizada
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "El pedido no fue encontrado para eliminar");
        } 
    }

}
