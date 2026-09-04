package com.biblioteca.service;

import com.biblioteca.entity.Libro;
import com.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    public Libro obtenerPorId(Integer id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    @Transactional
    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    @Transactional
    public Libro actualizar(Integer id, Libro libroActualizado) {
        Libro libro = obtenerPorId(id);
        libro.setTitulo(libroActualizado.getTitulo());
        libro.setEditorial(libroActualizado.getEditorial());
        libro.setAutor(libroActualizado.getAutor());
        libro.setMateria(libroActualizado.getMateria());
        libro.setCantidad(libroActualizado.getCantidad());
        libro.setStockMinimo(libroActualizado.getStockMinimo());
        libro.setNumPag(libroActualizado.getNumPag());
        libro.setAnioEdicion(libroActualizado.getAnioEdicion());
        return libroRepository.save(libro);
    }

    @Transactional
    public void eliminar(Integer id) {
        libroRepository.deleteById(id);
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Libro> buscarPorAutor(Integer autorId) {
        return libroRepository.findByAutorId(autorId);
    }

    public List<Libro> buscarPorEditorial(Integer editorialId) {
        return libroRepository.findByEditorialId(editorialId);
    }

    public List<Libro> buscarPorMateria(Integer materiaId) {
        return libroRepository.findByMateriaId(materiaId);
    }

    public List<Libro> obtenerStockBajo() {
        return libroRepository.findByCantidadLessThan(5);
    }
}
