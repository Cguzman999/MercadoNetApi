/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Services;

// Importaciones necesarias para excepciones, modelos y repositorios
import com.mercadonet.demo.Common.CustomException;
import com.mercadonet.demo.Models.Despachos;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mercadonet.demo.Repositories.DespachosRepository;
import org.springframework.http.HttpStatus;

/**
 *
 * @author HP
 */
/*Le indica a springboot que aqui tenemos un servicio*/
@Service
public class DespachosService {
    
    // Inyección de dependencia del repositorio de usuario
    @Autowired
    private DespachosRepository objrepdes;
    
    // Método para obtener la lista de todos los despachos
    public List<Despachos> traerdespachos(){
       return objrepdes.findAll();  
    } 
    
    // Método para obtener un despacho específico basado en su Guia
    public Despachos traerdespacho(String Guia){
        Despachos infodespacho = objrepdes.findByGuia(Guia); // Busca el despacho por su guia
        if (infodespacho != null){
        return infodespacho; // Si el despacho existe, se devuelve
        }
        else{
        // Si no se encuentra, lanza una excepción personalizada
        throw new CustomException(HttpStatus.NOT_FOUND.value(), "El despacho no fue encontrado en la base de datos");

    }
  }

 // Método para crear un nuevo despacho
    public String creardespacho(Despachos des){
        // Verifica si ya existe un despacho con la misma Guia
        Despachos infodes = objrepdes.findByGuia(des.getGuia());
        if(infodes == null){
            objrepdes.save(des); // Si no existe, guarda el nuevo 
            return "El despacho se guardo exitosamente"; // Mensaje de éxito
        }
        else{
            // Si ya existe, lanza una excepción personalizada
            throw new CustomException(HttpStatus.CONFLICT.value(), "El despacho ya existe en la base de datos");
        } 
    }
    // Método para actualizar un nuevo despacho
    public String actualizardespacho(Despachos des){
        // Verifica si ya existe un despacho con la misma guia
        Despachos infodes = objrepdes.findByGuia(des.getGuia());
        if(infodes != null){
           // Si el despacho existe, actualiza sus datos
            infodes.setDestinatario(des.getDestinatario());
            infodes.setDireccion(des.getDireccion());
            infodes.setFecha(des.getFecha());
            infodes.setGuia(des.getGuia());
            objrepdes.save(infodes);  // Guarda los cambios
            return "el despacho se actualizo exitosamente";  // Mensaje de éxito
        }
        else{
            // Si ya existe, lanza una excepción personalizada
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "El despacho no fue encontrado para actualizar");
        } 
    }
    
    
    // Método para eliminar un despacho basado en su guia
    public String eliminardespacho(String Guia){
        // Busca el despacho por su guia y lo guarda en el objeto del modelo llamado infodes
        Despachos infodes = objrepdes.findByGuia(Guia);
        if(infodes != null){
            objrepdes.deleteById(infodes.getIdDespacho()); // Si el despachos existe, lo elimina
            return "El despacho se elimino correctamente"; // Mensaje de éxito
        }
        else{
            // Si no se encuentra, lanza una excepción personalizada
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "El despacho no fue encontrado para eliminar");
        } 
    }

}
