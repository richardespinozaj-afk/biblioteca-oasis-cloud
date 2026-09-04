package com.biblioteca.service;

import com.biblioteca.entity.Estudiante;
import com.biblioteca.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;
    public EstudianteService(EstudianteRepository estudianteRepository) { this.estudianteRepository = estudianteRepository; }
    public List<Estudiante> listar() { return estudianteRepository.findAll(); }
    public Estudiante obtener(Integer id) { return estudianteRepository.findById(id).orElseThrow(() -> new RuntimeException("Estudiante no encontrado")); }
    public Estudiante guardar(Estudiante e) { return estudianteRepository.save(e); }
    public void eliminar(Integer id) { estudianteRepository.deleteById(id); }
}
