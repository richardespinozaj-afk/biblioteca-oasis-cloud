package com.biblioteca.dto;

import com.biblioteca.entity.Usuario;

public class LoginResponse {
    private String token;
    private String tipo = "Bearer";
    private Integer id;
    private String usuario;
    private String nombre;
    private String correo;
    private Usuario.Rol rol;

    public LoginResponse(String token, Integer id, String usuario, String nombre, String correo, Usuario.Rol rol) {
        this.token = token;
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Usuario.Rol getRol() { return rol; }
    public void setRol(Usuario.Rol rol) { this.rol = rol; }
}
