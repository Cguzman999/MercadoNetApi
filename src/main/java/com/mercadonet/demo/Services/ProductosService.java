/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Services;

// Importaciones necesarias para excepciones, modelos y repositorios
import com.mercadonet.demo.Common.CustomException;
import com.mercadonet.demo.Models.Productos;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mercadonet.demo.Repositories.ProductosRepository;
import org.springframework.http.HttpStatus;

/**
 *
 * @author HP
 */

/*Le indica a springboot que aqui tenemos un servicio*/
@Service
public class ProductosService {
    
    // Inyección de dependencia del repositorio de usuario
    @Autowired
    private ProductosRepository objreppro;
    
    // Método para obtener la lista de todos los usuarios
    public List<Productos> traerproductos(){
       return objreppro.findAll();  
    } 
    
    // Método para obtener un usuario específico basado en su Identificacion
    public Productos traerproducto(String Nombre){
        Productos infoproducto = objreppro.findByNombre(Nombre); // Busca el cliente por su identificacion
        if (infoproducto != null){
        return infoproducto; // Si el cliente existe, se devuelve
        }
        else{
        // Si no se encuentra, lanza una excepción personalizada
        throw new CustomException(HttpStatus.NOT_FOUND.value(), "El producto no fue encontrado en la base de datos");

    }
  }

 // Método para crear un nuevo cliente
    public String crearproducto(Productos pro){
        // Verifica si ya existe un cliente con la misma identificacion
        Productos infopro = objreppro.findByNombre(pro.getNombre());
        if(infopro == null){
            objreppro.save(pro); // Si no existe, guarda el nuevo 
            return "El producto se guardo exitosamente"; // Mensaje de éxito
        }
        else{
            // Si ya existe, lanza una excepción personalizada
            throw new CustomException(HttpStatus.CONFLICT.value(), "El producto ya existe en la base de datos");
        } 
    }
    // Método para actualizar un nuevo producto
    public String actualizarproducto(Productos pro){
        // Verifica si ya existe un producto con el mismo nombre
        Productos infopro = objreppro.findByNombre(pro.getNombre());
        if(infopro != null){
           // Si el producto existe, actualiza sus datos
            infopro.setNombre(pro.getNombre());
            infopro.setDescripcion(pro.getDescripcion());
            infopro.setPrecio(pro.getPrecio());
            objreppro.save(infopro);  // Guarda los cambios
            return "el producto se actualizo exitosamente";  // Mensaje de éxito
        }
        else{
            // Si ya existe, lanza una excepción personalizada
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "El producto no fue encontrado para actualizar");
        } 
    }
    
    
    // Método para eliminar un producto basado en su Nombre
    public String eliminarproducto(String Nombre){
        // Busca el producto por su nombre y lo guarda en el objeto del modelo llamado infopro
        Productos infopro = objreppro.findByNombre(Nombre);
        if(infopro != null){
            objreppro.deleteById(infopro.getIdProducto()); // Si el cliente existe, lo elimina
            return "El Producto se elimino correctamente"; // Mensaje de éxito
        }
        else{
            // Si no se encuentra, lanza una excepción personalizada
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "El producto no fue encontrado para eliminar");
        } 
    }
    
    
}
