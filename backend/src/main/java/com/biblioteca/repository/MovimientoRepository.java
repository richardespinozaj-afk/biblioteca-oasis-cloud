package com.biblioteca.repository;

import com.biblioteca.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
    List<Movimiento> findByLibroId(Integer libroId);
    List<Movimiento> findByTipo(Movimiento.Tipo tipo);
}
