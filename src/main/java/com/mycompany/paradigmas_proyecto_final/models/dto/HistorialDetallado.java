/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.models.dto;

/**
 *
 * @author danie
 */

import java.time.LocalDateTime;
import java.util.List;

public class HistorialDetallado {

    private int diagnosticoId;
    private LocalDateTime fecha;
    private List<String> sintomas;
    private List<EnfermedadResultado> enfermedades;

    public HistorialDetallado(int diagnosticoId, LocalDateTime fecha,
                              List<String> sintomas,
                              List<EnfermedadResultado> enfermedades) {
        this.diagnosticoId = diagnosticoId;
        this.fecha = fecha;
        this.sintomas = sintomas;
        this.enfermedades = enfermedades;
    }

    public int getDiagnosticoId() { return diagnosticoId; }
    public LocalDateTime getFecha() { return fecha; }
    public List<String> getSintomas() { return sintomas; }
    public List<EnfermedadResultado> getEnfermedades() { return enfermedades; }

    // Clase interna para resultados de enfermedades.
    public static class EnfermedadResultado {
        private String nombre;
        private int porcentajeCoincidencia;
        private String recomendacion; // opcional, útil para la UI

        public EnfermedadResultado(String nombre, int porcentajeCoincidencia, String recomendacion) {
            this.nombre = nombre;
            this.porcentajeCoincidencia = porcentajeCoincidencia;
            this.recomendacion = recomendacion;
        }

        public String getNombre() { return nombre; }

        /** Método solicitado por tu test */
        public int getPorcentajeCoincidencia() { return porcentajeCoincidencia; }

        public String getRecomendacion() { return recomendacion; }
    }
}
