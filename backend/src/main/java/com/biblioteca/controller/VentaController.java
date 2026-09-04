package com.biblioteca.controller;

import com.biblioteca.entity.Venta;
import com.biblioteca.entity.DetalleVenta;
import com.biblioteca.service.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<List<Venta>> listar() {
        return ResponseEntity.ok(ventaService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<Venta> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<Venta> crear(@RequestBody VentaRequest request) {
        return ResponseEntity.ok(ventaService.crear(request.getVenta(), request.getDetalles(), request.getIdUsuario()));
    }

    public static class VentaRequest {
        private Venta venta;
        private List<DetalleVenta> detalles;
        private Integer idUsuario;

        public Venta getVenta() { return venta; }
        public void setVenta(Venta venta) { this.venta = venta; }

        public List<DetalleVenta> getDetalles() { return detalles; }
        public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }

        public Integer getIdUsuario() { return idUsuario; }
        public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    }
}
