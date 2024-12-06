/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Services;

// Importaciones necesarias para excepciones, modelos y repositorios
import com.mercadonet.demo.Common.CustomException;
import com.mercadonet.demo.Models.Clientes;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mercadonet.demo.Repositories.ClientesRepository;
import org.springframework.http.HttpStatus;

/**
 *
 * @author HP
 */

/*Le indica a springboot que aqui tenemos un servicio*/
@Service
public class ClientesService {
    
    
    // Inyección de dependencia del repositorio de usuario
    @Autowired
    private ClientesRepository objrepcli;
    
    // Método para obtener la lista de todos los usuarios
    public List<Clientes> traerclientes(){
       return objrepcli.findAll();  
    } 
    
    // Método para obtener un usuario específico basado en su Identificacion
    public Clientes traercliente(String Identificacion){
        Clientes infocliente = objrepcli.findByIdentificacion(Identificacion); // Busca el cliente por su identificacion
        if (infocliente != null){
        return infocliente; // Si el cliente existe, se devuelve
        }
        else{
        // Si no se encuentra, lanza una excepción personalizada
        throw new CustomException(HttpStatus.NOT_FOUND.value(), "El cliente no fue encontrado en la base de datos");

    }
  }

 // Método para crear un nuevo cliente
    public String crearcliente(Clientes cli){
        // Verifica si ya existe un cliente con la misma identificacion
        Clientes infocli = objrepcli.findByIdentificacion(cli.getIdentificacion());
        if(infocli == null){
            objrepcli.save(cli); // Si no existe, guarda el nuevo 
            return "El cliente se guardo exitosamente"; // Mensaje de éxito
        }
        else{
            // Si ya existe, lanza una excepción personalizada
            throw new CustomException(HttpStatus.CONFLICT.value(), "El cliente ya existe en la base de datos");
        } 
    }
    // Método para actualizar un nuevo cliente
    public String actualizarcliente(Clientes cli){
        // Verifica si ya existe un cliente con la misma identificacion
        Clientes infocli = objrepcli.findByIdentificacion(cli.getIdentificacion());
        if(infocli != null){
           // Si el autor existe, actualiza sus datos
            infocli.setNombre(cli.getNombre());
            infocli.setApellido(cli.getApellido());
            infocli.setDireccion(cli.getDireccion());
            infocli.setTelefono(cli.getTelefono());
            infocli.setEmail(cli.getEmail());
            objrepcli.save(infocli);  // Guarda los cambios
            return "el cliente se actualizo exitosamente";  // Mensaje de éxito
        }
        else{
            // Si ya existe, lanza una excepción personalizada
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "El cliente no fue encontrado para actualizar");
        } 
    }
    
    
    // Método para eliminar un cliente basado en su identificacion
    public String eliminarcliente(String Identificacion){
        // Busca el cliente por su identificacion y lo guarda en el objeto del modelo llamado infocli
        Clientes infocli = objrepcli.findByIdentificacion(Identificacion);
        if(infocli != null){
            objrepcli.deleteById(infocli.getIdCliente()); // Si el cliente existe, lo elimina
            return "El cliente se elimino correctamente"; // Mensaje de éxito
        }
        else{
            // Si no se encuentra, lanza una excepción personalizada
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "El cliente no fue encontrado para eliminar");
        } 
    }

}