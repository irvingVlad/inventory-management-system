package com.irvingvlad.inventory_system.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data                  // Lombok: genera getters, setters, toString, equals y hashCode automáticamente
@NoArgsConstructor     // Lombok: constructor vacío, JPA lo necesita internamente
@AllArgsConstructor    // Lombok: constructor con todos los campos, útil para tests
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // el id lo genera la base de datos (autoincrement)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;
}
