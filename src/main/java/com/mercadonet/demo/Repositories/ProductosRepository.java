/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mercadonet.demo.Repositories;

// Importaciones necesarias para el modelo productos y las funcionalidades de JPA
import com.mercadonet.demo.Models.Productos;
import org.springframework.data.jpa.repository.JpaRepository; // Interfaz base para la persistencia de datos
import org.springframework.data.jpa.repository.Query; // Para definir consultas personalizadas
import org.springframework.stereotype.Repository; // Anotación que indica que esta interfaz es un repositorio

/**
 *
 * @author HP
 */

@Repository // Anotación que indica que esta interfaz es un componente de acceso a datos en el contexto de Spring

public interface ProductosRepository extends JpaRepository<Productos, Integer> { // Extiende JpaRepository para operaciones CRUD

    @Query(value= "select * from productos where Nombre =?1",nativeQuery = true) // Consulta SQL nativa para buscar un producto por su nombre
    Productos traerproductounico(String Nombre); // Método que usa la consulta definida anteriormente
    
    Productos findByNombre(String Nombre);   // Método que aprovecha la convención de Spring Data JPA  
}
