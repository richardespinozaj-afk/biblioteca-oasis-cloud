package com.biblioteca.service;

import com.biblioteca.entity.Movimiento;
import com.biblioteca.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;
    public MovimientoService(MovimientoRepository movimientoRepository) { this.movimientoRepository = movimientoRepository; }
    public List<Movimiento> listar() { return movimientoRepository.findAll(); }
    public Movimiento obtener(Integer id) { return movimientoRepository.findById(id).orElseThrow(() -> new RuntimeException("Movimiento no encontrado")); }
    public List<Movimiento> listarPorLibro(Integer libroId) { return movimientoRepository.findByLibroId(libroId); }
    public List<Movimiento> listarPorTipo(Movimiento.Tipo tipo) { return movimientoRepository.findByTipo(tipo); }
}
