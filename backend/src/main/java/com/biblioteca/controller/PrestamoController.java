package com.biblioteca.controller;

import com.biblioteca.entity.Prestamo;
import com.biblioteca.service.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<List<Prestamo>> listar() {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    @GetMapping("/activos")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<List<Prestamo>> activos() {
        return ResponseEntity.ok(prestamoService.listarActivos());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<Prestamo> crear(@RequestBody Prestamo prestamo, @RequestParam Integer idUsuario) {
        return ResponseEntity.ok(prestamoService.crear(prestamo, idUsuario));
    }

    @PostMapping("/devolver/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<Prestamo> devolver(@PathVariable Integer id, @RequestParam Integer idUsuario) {
        return ResponseEntity.ok(prestamoService.devolver(id, idUsuario));
    }

    @GetMapping("/estudiante/{estudianteId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<List<Prestamo>> porEstudiante(@PathVariable Integer estudianteId) {
        return ResponseEntity.ok(prestamoService.listarPorEstudiante(estudianteId));
    }
}
