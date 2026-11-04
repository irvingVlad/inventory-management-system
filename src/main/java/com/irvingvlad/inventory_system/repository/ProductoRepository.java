package com.irvingvlad.inventory_system.repository;

import com.irvingvlad.inventory_system.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Método de consulta derivado: Spring Data JPA genera el SQL solo por el nombre del método
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Productos cuyo stock actual está por debajo del mínimo — esto alimenta la alerta de reabastecimiento
    List<Producto> findByCantidadStockLessThan(Integer cantidadStock);
}
