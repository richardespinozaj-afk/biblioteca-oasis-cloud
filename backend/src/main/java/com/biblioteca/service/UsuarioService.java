package com.biblioteca.service;

import com.biblioteca.dto.*;
import com.biblioteca.entity.Usuario;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public Usuario registrar(RegistroRequest request) {
        if (usuarioRepository.existsByUsuario(request.getUsuario())) {
            throw new RuntimeException("El usuario ya existe");
        }
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya esta registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setUsuario(request.getUsuario());
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setClave(passwordEncoder.encode(request.getClave()));
        usuario.setRol(Usuario.Rol.valueOf(request.getRol()));
        return usuarioRepository.save(usuario);
    }

    public LoginResponse autenticar(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getClave()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(authentication);
        Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new LoginResponse(token, usuario.getId(), usuario.getUsuario(),
                usuario.getNombre(), usuario.getCorreo(), usuario.getRol());
    }

    @Transactional
    public void cambiarClave(Integer userId, CambioClaveRequest request) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getClaveActual(), usuario.getClave())) {
            throw new RuntimeException("La contrasena actual es incorrecta");
        }
        usuario.setClave(passwordEncoder.encode(request.getClaveNueva()));
        usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
