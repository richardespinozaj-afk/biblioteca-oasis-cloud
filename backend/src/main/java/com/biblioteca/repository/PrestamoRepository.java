package com.biblioteca.repository;

import com.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    List<Prestamo> findByEstudianteId(Integer estudianteId);
    List<Prestamo> findByLibroId(Integer libroId);
    List<Prestamo> findByEstado(Prestamo.Estado estado);
}
