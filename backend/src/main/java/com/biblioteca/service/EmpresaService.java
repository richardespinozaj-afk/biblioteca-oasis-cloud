package com.biblioteca.service;

import com.biblioteca.entity.Empresa;
import com.biblioteca.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    public EmpresaService(EmpresaRepository empresaRepository) { this.empresaRepository = empresaRepository; }
    public List<Empresa> listar() { return empresaRepository.findAll(); }
    public Empresa obtener(Integer id) { return empresaRepository.findById(id).orElseThrow(() -> new RuntimeException("Empresa no encontrada")); }
    public Empresa guardar(Empresa e) { return empresaRepository.save(e); }
}
