package com.irvingvlad.inventory_system.service;

import com.irvingvlad.inventory_system.model.Categoria;
import com.irvingvlad.inventory_system.model.Producto;
import com.irvingvlad.inventory_system.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.irvingvlad.inventory_system.exception.RecursoNoEncontradoException;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;

    @Autowired
    public ProductoService(ProductoRepository productoRepository, CategoriaService categoriaService) {
        this.productoRepository = productoRepository;
        this.categoriaService = categoriaService;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> listarConStockBajo() {
        return productoRepository.findAll().stream()
                .filter(p -> p.getCantidadStock() < p.getStockMinimo())
                .toList();
    }

    public Producto crear(Producto producto) {
        Categoria categoria = categoriaService.buscarPorId(producto.getCategoria().getId());
        producto.setCategoria(categoria);

        if (producto.getCantidadStock() < 0) {
            throw new IllegalArgumentException("La cantidad en stock no puede ser negativa");
        }

        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto datosActualizados) {
        Producto productoExistente = buscarPorId(id);

        Categoria categoria = categoriaService.buscarPorId(datosActualizados.getCategoria().getId());

        productoExistente.setNombre(datosActualizados.getNombre());
        productoExistente.setDescripcion(datosActualizados.getDescripcion());
        productoExistente.setPrecio(datosActualizados.getPrecio());
        productoExistente.setStockMinimo(datosActualizados.getStockMinimo());
        productoExistente.setCategoria(categoria);

        return productoRepository.save(productoExistente);
    }

    public Producto ajustarStock(Long id, int cantidad) {
        Producto producto = buscarPorId(id);
        int nuevoStock = producto.getCantidadStock() + cantidad;

        if (nuevoStock < 0) {
            throw new IllegalArgumentException(
                    "No hay suficiente stock. Stock actual: " + producto.getCantidadStock()
                            + ", se intentó descontar: " + Math.abs(cantidad));
        }

        producto.setCantidadStock(nuevoStock);
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        Producto producto = buscarPorId(id);
        productoRepository.delete(producto);
    }
}