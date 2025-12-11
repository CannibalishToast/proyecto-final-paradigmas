/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.service;

import com.mycompany.paradigmas_proyecto_final.dao.*;
import com.mycompany.paradigmas_proyecto_final.models.*;

import java.util.*;

public class DiagnosticoService {

    private DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();
    private DiagnosticoEnfermedadDAO diagnosticoEnfermedadDAO = new DiagnosticoEnfermedadDAO();
    private DiagnosticoSintomaDAO diagnosticoSintomaDAO = new DiagnosticoSintomaDAO();
    private EnfermedadDAO enfermedadDAO = new EnfermedadDAO();

    /**
     * Ejecuación completa del diagnóstico.
     */
    public List<ResultadoDiagnostico> realizarDiagnostico(int pacienteId, List<Integer> sintomasSeleccionados) {

        // 1. Ejecutar Prolog
        List<PrologDiagnosticoDAO.ResultadoDiagnosticoProlog> resultadosProlog =
                PrologDiagnosticoDAO.diagnosticar(sintomasSeleccionados);

        resultadosProlog.sort((a, b) -> Double.compare(b.getPorcentaje(), a.getPorcentaje()));

        // 2. Crear diagnóstico en DB
        int diagnosticoId = diagnosticoDAO.insertar(pacienteId);

        // 3. Registrar síntomas seleccionados
        for (Integer s : sintomasSeleccionados) {
            DiagnosticoSintoma ds = new DiagnosticoSintoma();
            ds.setDiagnosticoId(diagnosticoId);
            ds.setSintomaId(s);
            diagnosticoSintomaDAO.insertar(ds);
        }

        // 4. Registrar enfermedades
        List<ResultadoDiagnostico> resultadosFinales = new ArrayList<>();

        for (PrologDiagnosticoDAO.ResultadoDiagnosticoProlog rp : resultadosProlog) {

            int enfermedadId = rp.getEnfermedadId();
            double porcentaje = rp.getPorcentaje();

            Enfermedad enf = enfermedadDAO.getById(enfermedadId);

            DiagnosticoEnfermedad de = new DiagnosticoEnfermedad();
            de.setDiagnosticoId(diagnosticoId);
            de.setEnfermedadId(enfermedadId);
            de.setCoincidenciaPorcentaje((int) porcentaje);
            diagnosticoEnfermedadDAO.insertar(de);

            resultadosFinales.add(
                    new ResultadoDiagnostico(enf.getNombre(), porcentaje, enf.getRecomendacion())
            );
        }

        return resultadosFinales;
    }

    // DTO retornado a la GUI
    public static class ResultadoDiagnostico {
        private String enfermedad;
        private double porcentaje;
        private String recomendacion;

        public ResultadoDiagnostico(String enfermedad, double porcentaje, String recomendacion) {
            this.enfermedad = enfermedad;
            this.porcentaje = porcentaje;
            this.recomendacion = recomendacion;
        }

        public String getEnfermedad() { return enfermedad; }
        public double getPorcentaje() { return porcentaje; }
        public String getRecomendacion() { return recomendacion; }
    }
}
