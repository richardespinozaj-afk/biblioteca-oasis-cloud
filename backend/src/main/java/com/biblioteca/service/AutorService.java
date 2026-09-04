package com.biblioteca.service;

import com.biblioteca.entity.Autor;
import com.biblioteca.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    private final AutorRepository autorRepository;
    public AutorService(AutorRepository autorRepository) { this.autorRepository = autorRepository; }
    public List<Autor> listar() { return autorRepository.findAll(); }
    public Autor obtener(Integer id) { return autorRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor no encontrado")); }
    public Autor guardar(Autor a) { return autorRepository.save(a); }
    public void eliminar(Integer id) { autorRepository.deleteById(id); }
}
