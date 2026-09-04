package com.biblioteca.dto;

public class CambioClaveRequest {
    private String claveActual;
    private String claveNueva;

    public String getClaveActual() { return claveActual; }
    public void setClaveActual(String claveActual) { this.claveActual = claveActual; }

    public String getClaveNueva() { return claveNueva; }
    public void setClaveNueva(String claveNueva) { this.claveNueva = claveNueva; }
}
