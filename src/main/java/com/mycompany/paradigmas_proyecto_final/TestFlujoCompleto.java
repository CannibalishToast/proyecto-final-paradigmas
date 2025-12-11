/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final;

/**
 *
 * @author danie
 */

import com.mycompany.paradigmas_proyecto_final.service.*;
import com.mycompany.paradigmas_proyecto_final.prolog.*;

import org.jpl7.Query;

import java.util.Arrays;
import java.util.List;

public class TestFlujoCompleto {

    public static void main(String[] args) {

        System.out.println("=== TEST → Flujo completo Diagnóstico + Historial ===\n");

        try {
            // -------------------------
            // 1. Cargar reglas Prolog y hechos
            // -------------------------
            FactsBuilder fb = new FactsBuilder();
            fb.initProlog();
            fb.construirTodo();   // importante: carga hechos din. desde BD

            boolean ok = new Query("current_predicate(diagnosticar/3).").hasSolution();
            System.out.println("diagnosticar/3 cargado: " + ok);

            // -------------------------
            // 2. Ejecutar diagnóstico
            // -------------------------
            DiagnosticoService diagService = new DiagnosticoService();

            int pacienteId = 1;
            List<Integer> sintomas = Arrays.asList(1, 2);

            var resultados = diagService.realizarDiagnostico(pacienteId, sintomas);

            System.out.println("\nResultados del diagnóstico:");
            for (var r : resultados) {
                System.out.println("  Enfermedad: " + r.getEnfermedad() +
                                   " | %: " + r.getPorcentaje());
            }

            // -------------------------
            // 3. Historial completo
            // -------------------------
            System.out.println("\n=== Historial del paciente ===");

            HistorialService hs = new HistorialService();
            var historial = hs.obtenerHistorialPaciente(pacienteId);

            historial.forEach(h -> {
                System.out.println("----------------------------------------");
                System.out.println("Diagnóstico #" + h.getDiagnosticoId() +
                                   " | Fecha: " + h.getFecha());
                System.out.println("Síntomas: " + h.getSintomas());
                System.out.println("Enfermedades:");
                h.getEnfermedades().forEach(e ->
                    System.out.println("   - " + e.getNombre() + " (" +
                                        e.getPorcentajeCoincidencia() + "%) - " +
                                        e.getRecomendacion())
                );
            });

            System.out.println("\n=== TEST COMPLETADO ===\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

