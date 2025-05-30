package com.example.Productos.repository;

import com.example.Productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link Producto}.
 * <p>
 * Proporciona operaciones CRUD y consultas sobre productos utilizando Spring Data JPA.
 * Los métodos básicos como guardar, buscar, actualizar y eliminar son heredados de {@link JpaRepository}.
 * </p>
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {}