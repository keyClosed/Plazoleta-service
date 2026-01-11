package com.plaza.plazoleta_service.infrastructure.out.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id") // FK hacia restaurante
    private RestauranteEntity restaurante;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public RestauranteEntity getRestaurante() { return restaurante; }
    public void setRestaurante(RestauranteEntity restaurante) { this.restaurante = restaurante; }
}
