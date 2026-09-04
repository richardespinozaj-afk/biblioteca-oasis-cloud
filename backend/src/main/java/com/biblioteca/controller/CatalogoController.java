package com.biblioteca.controller;

import com.biblioteca.entity.*;
import com.biblioteca.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogos")
public class CatalogoController {

    private final AutorService autorService;
    private final EditorialService editorialService;
    private final MateriaService materiaService;

    public CatalogoController(AutorService autorService, EditorialService editorialService, MateriaService materiaService) {
        this.autorService = autorService;
        this.editorialService = editorialService;
        this.materiaService = materiaService;
    }

    @GetMapping("/autores")
    public ResponseEntity<List<Autor>> autores() { return ResponseEntity.ok(autorService.listar()); }

    @PostMapping("/autores")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Autor> guardarAutor(@RequestBody Autor a) { return ResponseEntity.ok(autorService.guardar(a)); }

    @DeleteMapping("/autores/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarAutor(@PathVariable Integer id) { autorService.eliminar(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/editoriales")
    public ResponseEntity<List<Editorial>> editoriales() { return ResponseEntity.ok(editorialService.listar()); }

    @PostMapping("/editoriales")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Editorial> guardarEditorial(@RequestBody Editorial e) { return ResponseEntity.ok(editorialService.guardar(e)); }

    @DeleteMapping("/editoriales/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarEditorial(@PathVariable Integer id) { editorialService.eliminar(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/materias")
    public ResponseEntity<List<Materia>> materias() { return ResponseEntity.ok(materiaService.listar()); }

    @PostMapping("/materias")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Materia> guardarMateria(@RequestBody Materia m) { return ResponseEntity.ok(materiaService.guardar(m)); }

    @DeleteMapping("/materias/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarMateria(@PathVariable Integer id) { materiaService.eliminar(id); return ResponseEntity.noContent().build(); }
}
