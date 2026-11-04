package com.irvingvlad.inventory_system.service;


import com.irvingvlad.inventory_system.model.Categoria;
import com.irvingvlad.inventory_system.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.irvingvlad.inventory_system.exception.RecursoNoEncontradoException;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con id: " + id));
    }

    public Categoria crear(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizar(Long id, Categoria datosActualizados) {
        Categoria categoriaExistente = buscarPorId(id);
        categoriaExistente.setNombre(datosActualizados.getNombre());
        categoriaExistente.setDescripcion(datosActualizados.getDescripcion());
        return categoriaRepository.save(categoriaExistente);
    }

    public void eliminar(Long id) {
        Categoria categoria = buscarPorId(id);
        categoriaRepository.delete(categoria);
    }
}
