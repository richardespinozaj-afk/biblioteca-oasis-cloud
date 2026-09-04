package com.biblioteca.service;

import com.biblioteca.entity.Materia;
import com.biblioteca.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {
    private final MateriaRepository materiaRepository;
    public MateriaService(MateriaRepository materiaRepository) { this.materiaRepository = materiaRepository; }
    public List<Materia> listar() { return materiaRepository.findAll(); }
    public Materia obtener(Integer id) { return materiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Materia no encontrada")); }
    public Materia guardar(Materia m) { return materiaRepository.save(m); }
    public void eliminar(Integer id) { materiaRepository.deleteById(id); }
}
