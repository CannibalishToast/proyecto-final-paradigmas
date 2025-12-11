/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.dao;

/**
 *
 * @author danie
 */

import java.util.*;
import org.jpl7.Query;
import org.jpl7.Term;

public class PrologDiagnosticoDAO {

    /**
     * Ejecuta diagnosticar(SintomasUsuario, Enf, P) en Prolog
     * Retorna lista de resultados con enfermedad_id y porcentaje.
     */
    public static List<ResultadoDiagnosticoProlog> diagnosticar(List<Integer> sintomasUsuario) {

        List<ResultadoDiagnosticoProlog> resultados = new ArrayList<>();

        // Conversión Java → Prolog list
        StringBuilder lista = new StringBuilder("[");
        for (int i = 0; i < sintomasUsuario.size(); i++) {
            lista.append(sintomasUsuario.get(i));
            if (i < sintomasUsuario.size() - 1) lista.append(",");
        }
        lista.append("]");

        String consulta = "diagnosticar(" + lista + ", Enf, P).";

        Query q = new Query(consulta);

        while (q.hasMoreSolutions()) {
            Map<String, Term> sol = q.nextSolution();

            int enfermedadId = Integer.parseInt(sol.get("Enf").toString());
            double porcentaje = Double.parseDouble(sol.get("P").toString());

            resultados.add(new ResultadoDiagnosticoProlog(enfermedadId, porcentaje));
        }

        q.close();
        return resultados;
    }

    /** Pequeño DTO interno para pasar datos al service */
    public static class ResultadoDiagnosticoProlog {
        private int enfermedadId;
        private double porcentaje;

        public ResultadoDiagnosticoProlog(int enfermedadId, double porcentaje) {
            this.enfermedadId = enfermedadId;
            this.porcentaje = porcentaje;
        }

        public int getEnfermedadId() { return enfermedadId; }
        public double getPorcentaje() { return porcentaje; }
    }
}

