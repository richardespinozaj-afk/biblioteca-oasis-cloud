package com.biblioteca.controller;

import com.biblioteca.entity.Estudiante;
import com.biblioteca.service.EstudianteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<List<Estudiante>> listar() {
        return ResponseEntity.ok(estudianteService.listar());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<Estudiante> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(estudianteService.obtener(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<Estudiante> guardar(@RequestBody Estudiante e) {
        return ResponseEntity.ok(estudianteService.guardar(e));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
