package com.biblioteca.service;

import com.biblioteca.entity.Editorial;
import com.biblioteca.repository.EditorialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorialService {
    private final EditorialRepository editorialRepository;
    public EditorialService(EditorialRepository editorialRepository) { this.editorialRepository = editorialRepository; }
    public List<Editorial> listar() { return editorialRepository.findAll(); }
    public Editorial obtener(Integer id) { return editorialRepository.findById(id).orElseThrow(() -> new RuntimeException("Editorial no encontrada")); }
    public Editorial guardar(Editorial e) { return editorialRepository.save(e); }
    public void eliminar(Integer id) { editorialRepository.deleteById(id); }
}
