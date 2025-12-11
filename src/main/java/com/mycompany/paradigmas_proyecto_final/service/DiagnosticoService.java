/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.service;

/**
 *
 * @author danie
 */

import com.mycompany.paradigmas_proyecto_final.dao.*;
import com.mycompany.paradigmas_proyecto_final.models.*;
import com.mycompany.paradigmas_proyecto_final.dao.PrologDiagnosticoDAO;
import java.util.*;

public class DiagnosticoService {

    private DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();
    private DiagnosticoEnfermedadDAO diagnosticoEnfermedadDAO = new DiagnosticoEnfermedadDAO();
    private DiagnosticoSintomaDAO diagnosticoSintomaDAO = new DiagnosticoSintomaDAO();
    private EnfermedadDAO enfermedadDAO = new EnfermedadDAO();

    /**
     * Flujo completo del diagnóstico:
     * 1. Ejecutar Prolog
     * 2. Insertar diagnostico en DB
     * 3. Insertar relaciones
     * 4. Retornar resultados listos para GUI
     */
    public List<ResultadoDiagnostico> realizarDiagnostico(int pacienteId, List<Integer> sintomasSeleccionados) {

        // -------------------------------
        // 1. Ejecutar Prolog
        // -------------------------------
        List<PrologDiagnosticoDAO.ResultadoDiagnosticoProlog> resultadosProlog =
                PrologDiagnosticoDAO.diagnosticar(sintomasSeleccionados);

        // ordenar de mayor a menor
        resultadosProlog.sort((a, b) -> Double.compare(b.getPorcentaje(), a.getPorcentaje()));

        // -------------------------------
        // 2. Crear un diagnóstico en DB
        // -------------------------------
        Diagnostico diag = new Diagnostico();
        diag.setPacienteId(pacienteId);
        int diagnosticoId = diagnosticoDAO.insertar(diag);

        // -------------------------------
        // 3. Registrar síntomas seleccionados
        // -------------------------------
        for (Integer s : sintomasSeleccionados) {
            DiagnosticoSintoma ds = new DiagnosticoSintoma();
            ds.setDiagnosticoId(diagnosticoId);
            ds.setSintomaId(s);
            diagnosticoSintomaDAO.insertar(ds);
        }

        // -------------------------------
        // 4. Registrar enfermedades resultantes
        // -------------------------------
        List<ResultadoDiagnostico> resultadosFinales = new ArrayList<>();

        for (PrologDiagnosticoDAO.ResultadoDiagnosticoProlog rp : resultadosProlog) {

            int enfId = rp.getEnfermedadId();
            double porcentaje = rp.getPorcentaje();

            Enfermedad enf = enfermedadDAO.getById(enfId);

            // Insertar relacion diagnostico_enfermedad
            DiagnosticoEnfermedad de = new DiagnosticoEnfermedad();
            de.setDiagnosticoId(diagnosticoId);
            de.setEnfermedadId(enfId);
            de.setCoincidenciaPorcentaje((int) porcentaje);
            diagnosticoEnfermedadDAO.insertar(de);

            // preparar resultado para GUI
            resultadosFinales.add(new ResultadoDiagnostico(enf.getNombre(), porcentaje, enf.getRecomendacion()));
        }

        return resultadosFinales;
    }

    // DTO usado para la UI
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
