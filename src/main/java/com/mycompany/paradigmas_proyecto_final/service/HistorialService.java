/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.service;

import com.mycompany.paradigmas_proyecto_final.dao.*;
import com.mycompany.paradigmas_proyecto_final.models.*;
import com.mycompany.paradigmas_proyecto_final.models.dto.*;

import java.util.*;

public class HistorialService {

    private DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();
    private DiagnosticoSintomaDAO diagnosticoSintomaDAO = new DiagnosticoSintomaDAO();
    private DiagnosticoEnfermedadDAO diagnosticoEnfermedadDAO = new DiagnosticoEnfermedadDAO();
    private SintomaDAO sintomaDAO = new SintomaDAO();
    private EnfermedadDAO enfermedadDAO = new EnfermedadDAO();

    public List<HistorialDetallado> obtenerHistorialPaciente(int pacienteId) {

        List<Diagnostico> diagnos = diagnosticoDAO.getByPaciente(pacienteId);
        List<HistorialDetallado> historial = new ArrayList<>();

        for (Diagnostico d : diagnos) {

            // 1. Obtener síntomas usados en el diagnóstico
            List<DiagnosticoSintoma> sintDiag =
                    diagnosticoSintomaDAO.getByDiagnostico(d.getId());

            List<String> sintomas = new ArrayList<>();

            for (DiagnosticoSintoma sd : sintDiag) {
                Sintoma s = sintomaDAO.getById(sd.getSintomaId());
                if (s != null)
                    sintomas.add(s.getNombre());
            }

            // 2. Enfermedades asociadas
            List<DiagnosticoEnfermedad> enfDiag =
                    diagnosticoEnfermedadDAO.getByDiagnostico(d.getId());

            List<HistorialDetallado.EnfermedadResultado> enfermedades = new ArrayList<>();

            for (DiagnosticoEnfermedad de : enfDiag) {

                Enfermedad e = enfermedadDAO.getById(de.getEnfermedadId());

                if (e != null) {
                    enfermedades.add(
                            new HistorialDetallado.EnfermedadResultado(
                                    e.getNombre(),
                                    de.getCoincidenciaPorcentaje(),
                                    e.getRecomendacion()
                            )
                    );
                }
            }

            historial.add(new HistorialDetallado(
                    d.getId(),
                    d.getFecha(),
                    sintomas,
                    enfermedades
            ));
        }

        return historial;
    }

    public List<FilaHistorial> obtenerHistorialPlano(int pacienteId) {

        List<HistorialDetallado> historial = obtenerHistorialPaciente(pacienteId);
        List<FilaHistorial> filas = new ArrayList<>();

        for (HistorialDetallado h : historial) {

            for (HistorialDetallado.EnfermedadResultado er : h.getEnfermedades()) {

                filas.add(new FilaHistorial(
                        h.getDiagnosticoId(),
                        h.getFecha(),
                        er.getNombre(),
                        er.getPorcentajeCoincidencia(),
                        er.getRecomendacion(),
                        h.getSintomas()
                ));
            }
        }

        return filas;
    }

    public List<HistorialDetallado> obtenerHistorialParaReporte(int pacienteId) {
        return obtenerHistorialPaciente(pacienteId);
    }


    /** DTO para las filas del historial plano */
    public static class FilaHistorial {
        private int diagnosticoId;
        private java.time.LocalDateTime fecha;
        private String enfermedad;
        private int porcentaje;
        private String recomendacion;
        private List<String> sintomas;

        public FilaHistorial(int diagnosticoId, java.time.LocalDateTime fecha,
                             String enfermedad, int porcentaje,
                             String recomendacion, List<String> sintomas) {

            this.diagnosticoId = diagnosticoId;
            this.fecha = fecha;
            this.enfermedad = enfermedad;
            this.porcentaje = porcentaje;
            this.recomendacion = recomendacion;
            this.sintomas = sintomas;
        }

        public int getDiagnosticoId() { return diagnosticoId; }
        public java.time.LocalDateTime getFecha() { return fecha; }
        public String getEnfermedad() { return enfermedad; }
        public int getPorcentaje() { return porcentaje; }
        public String getRecomendacion() { return recomendacion; }
        public List<String> getSintomas() { return sintomas; }
    }
}
