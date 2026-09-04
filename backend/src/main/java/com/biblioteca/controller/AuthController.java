package com.biblioteca.controller;

import com.biblioteca.dto.*;
import com.biblioteca.entity.Usuario;
import com.biblioteca.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(usuarioService.autenticar(request));
    }

    @PostMapping("/registro")
    public ResponseEntity<MensajeResponse> registro(@RequestBody RegistroRequest request) {
        usuarioService.registrar(request);
        return ResponseEntity.ok(new MensajeResponse("Usuario registrado exitosamente"));
    }

    @PostMapping("/cambiar-clave/{userId}")
    public ResponseEntity<MensajeResponse> cambiarClave(@PathVariable Integer userId, @RequestBody CambioClaveRequest request) {
        usuarioService.cambiarClave(userId, request);
        return ResponseEntity.ok(new MensajeResponse("Contrasena actualizada exitosamente"));
    }
}
