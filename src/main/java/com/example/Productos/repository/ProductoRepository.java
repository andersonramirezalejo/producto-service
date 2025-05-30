package com.example.Productos.repository;

import com.example.Productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    //JpaRepository proporciona los básicos.
}