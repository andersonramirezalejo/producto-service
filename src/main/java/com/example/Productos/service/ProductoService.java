package com.example.Productos.service;

import com.example.Productos.model.Producto;
import com.example.Productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de productos.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD sobre la entidad {@link Producto}.
 * </p>
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;


    /**
     * Obtiene la lista de todos los productos.
     *
     * @return Lista de productos.
     */
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
    

    /**
     * Busca un producto por su ID.
     *
     * @param id Identificador del producto.
     * @return Un {@link Optional} con el producto si existe, vacío si no.
     */
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }


    /**
     * Guarda un nuevo producto en la base de datos.
     *
     * @param producto Producto a guardar.
     * @return Producto guardado.
     * @throws IllegalArgumentException si el producto es nulo.
     */
    public Producto save(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        return productoRepository.save(producto);
    }


    /**
     * Elimina un producto por su ID.
     *
     * @param id Identificador del producto a eliminar.
     */
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }


    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id Identificador del producto a actualizar.
     * @param productoDetails Datos nuevos del producto.
     * @return Producto actualizado.
     * @throws RuntimeException si el producto no existe.
     */
    public Producto updateProducto(Long id, Producto productoDetails) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoDetails.getNombre());
            producto.setDescripcion(productoDetails.getDescripcion());
            producto.setPrecio(productoDetails.getPrecio());
            return productoRepository.save(producto);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + id));
    }
}