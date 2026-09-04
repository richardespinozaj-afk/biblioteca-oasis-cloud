package com.biblioteca.repository;

import com.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByAutorId(Integer autorId);
    List<Libro> findByEditorialId(Integer editorialId);
    List<Libro> findByMateriaId(Integer materiaId);
    List<Libro> findByCantidadLessThan(Integer cantidad);
}
