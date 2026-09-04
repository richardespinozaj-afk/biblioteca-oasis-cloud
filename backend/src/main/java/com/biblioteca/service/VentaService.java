package com.biblioteca.service;

import com.biblioteca.entity.*;
import com.biblioteca.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;
    private final MovimientoRepository movimientoRepository;

    public VentaService(VentaRepository ventaRepository, DetalleVentaRepository detalleVentaRepository,
                        LibroRepository libroRepository, UsuarioRepository usuarioRepository,
                        MovimientoRepository movimientoRepository) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
        this.movimientoRepository = movimientoRepository;
    }

    public List<Venta> listarTodos() {
        return ventaRepository.findAll();
    }

    public Venta obtenerPorId(Integer id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    @Transactional
    public Venta crear(Venta venta, List<DetalleVenta> detalles, Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        venta.setUsuario(usuario);

        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVenta d : detalles) {
            Libro libro = libroRepository.findById(d.getLibro().getId())
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
            if (libro.getCantidad() < d.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el libro: " + libro.getTitulo());
            }
            libro.setCantidad(libro.getCantidad() - d.getCantidad());
            libroRepository.save(libro);

            d.setLibro(libro);
            d.setVenta(venta);
            total = total.add(d.getPrecio().multiply(BigDecimal.valueOf(d.getCantidad())));

            Movimiento mov = new Movimiento();
            mov.setLibro(libro);
            mov.setTipo(Movimiento.Tipo.venta);
            mov.setCantidad(d.getCantidad());
            mov.setUsuario(usuario);
            movimientoRepository.save(mov);
        }
        venta.setTotal(total);
        venta.setDetalles(detalles);
        return ventaRepository.save(venta);
    }
}
