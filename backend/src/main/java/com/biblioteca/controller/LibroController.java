package com.biblioteca.controller;

import com.biblioteca.entity.Libro;
import com.biblioteca.service.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> listar() {
        return ResponseEntity.ok(libroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(libroService.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<Libro> guardar(@RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.guardar(libro));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<Libro> actualizar(@PathVariable Integer id, @RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.actualizar(id, libro));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Libro>> buscar(@RequestParam String titulo) {
        return ResponseEntity.ok(libroService.buscarPorTitulo(titulo));
    }

    @GetMapping("/stock-bajo")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<List<Libro>> stockBajo() {
        return ResponseEntity.ok(libroService.obtenerStockBajo());
    }
}
