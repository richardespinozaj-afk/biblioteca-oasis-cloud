package com.biblioteca.controller;

import com.biblioteca.entity.Movimiento;
import com.biblioteca.service.MovimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<List<Movimiento>> listar() {
        return ResponseEntity.ok(movimientoService.listar());
    }

    @GetMapping("/libro/{libroId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<List<Movimiento>> porLibro(@PathVariable Integer libroId) {
        return ResponseEntity.ok(movimientoService.listarPorLibro(libroId));
    }

    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<List<Movimiento>> porTipo(@PathVariable Movimiento.Tipo tipo) {
        return ResponseEntity.ok(movimientoService.listarPorTipo(tipo));
    }
}
