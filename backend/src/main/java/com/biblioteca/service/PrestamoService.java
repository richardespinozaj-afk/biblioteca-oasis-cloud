package com.biblioteca.service;

import com.biblioteca.entity.*;
import com.biblioteca.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final EstudianteRepository estudianteRepository;
    private final MovimientoRepository movimientoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, LibroRepository libroRepository,
                           EstudianteRepository estudianteRepository, MovimientoRepository movimientoRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.estudianteRepository = estudianteRepository;
        this.movimientoRepository = movimientoRepository;
    }

    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }

    public Prestamo obtenerPorId(Integer id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado"));
    }

    @Transactional
    public Prestamo crear(Prestamo prestamo, Integer idUsuario) {
        Libro libro = libroRepository.findById(prestamo.getLibro().getId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        Estudiante estudiante = estudianteRepository.findById(prestamo.getEstudiante().getId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        if (libro.getCantidad() < prestamo.getCantidad()) {
            throw new RuntimeException("Stock insuficiente");
        }

        libro.setCantidad(libro.getCantidad() - prestamo.getCantidad());
        libroRepository.save(libro);

        prestamo.setEstudiante(estudiante);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setEstado(Prestamo.Estado.activo);

        Prestamo guardado = prestamoRepository.save(prestamo);

        Movimiento mov = new Movimiento();
        mov.setLibro(libro);
        mov.setTipo(Movimiento.Tipo.prestamo);
        mov.setCantidad(prestamo.getCantidad());
        Usuario u = new Usuario(); u.setId(idUsuario);
        mov.setUsuario(u);
        movimientoRepository.save(mov);

        return guardado;
    }

    @Transactional
    public Prestamo devolver(Integer idPrestamo, Integer idUsuario) {
        Prestamo prestamo = obtenerPorId(idPrestamo);
        if (prestamo.getEstado() == Prestamo.Estado.devuelto) {
            throw new RuntimeException("El prestamo ya fue devuelto");
        }

        Libro libro = prestamo.getLibro();
        libro.setCantidad(libro.getCantidad() + prestamo.getCantidad());
        libroRepository.save(libro);

        prestamo.setEstado(Prestamo.Estado.devuelto);
        prestamo.setFechaDevolucion(LocalDate.now());
        Prestamo guardado = prestamoRepository.save(prestamo);

        Movimiento mov = new Movimiento();
        mov.setLibro(libro);
        mov.setTipo(Movimiento.Tipo.devolucion);
        mov.setCantidad(prestamo.getCantidad());
        Usuario u = new Usuario(); u.setId(idUsuario);
        mov.setUsuario(u);
        movimientoRepository.save(mov);

        return guardado;
    }

    public List<Prestamo> listarPorEstudiante(Integer estudianteId) {
        return prestamoRepository.findByEstudianteId(estudianteId);
    }

    public List<Prestamo> listarActivos() {
        return prestamoRepository.findByEstado(Prestamo.Estado.activo);
    }
}
