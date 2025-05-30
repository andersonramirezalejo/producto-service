package com.example.Productos.service;

import com.example.Productos.model.Producto;
import com.example.Productos.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop Dell XPS");
        producto.setDescripcion("Potente laptop para trabajo y estudio");
        producto.setPrecio(2500000.00);
    }

    @Test
    @DisplayName("Debe guardar un producto exitosamente")
    void givenProductoObject_whenSaveProducto_thenReturnSavedProducto() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        Producto savedProducto = productoService.save(producto);

        assertNotNull(savedProducto);
        assertEquals(producto.getNombre(), savedProducto.getNombre());
        assertEquals(producto.getPrecio(), savedProducto.getPrecio());

        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el producto es nulo al intentar guardar")
    void givenNullProducto_whenSaveProducto_thenThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.save(null);
        });

        assertEquals("El producto no puede ser nulo.", exception.getMessage());

        verify(productoRepository, never()).save(any(Producto.class));
    }
}