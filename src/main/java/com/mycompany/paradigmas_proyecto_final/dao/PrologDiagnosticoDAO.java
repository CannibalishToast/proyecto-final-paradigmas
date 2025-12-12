/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.dao;

import com.mycompany.paradigmas_proyecto_final.models.Enfermedad;
import com.mycompany.paradigmas_proyecto_final.models.Sintoma;
import com.mycompany.paradigmas_proyecto_final.dao.EnfermedadDAO;
import com.mycompany.paradigmas_proyecto_final.dao.SintomaDAO;

import org.jpl7.Query;
import org.jpl7.Term;

import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class PrologDiagnosticoDAO {

    private static boolean prologCargado = false;


    public static void inicializarProlog() {
        if (prologCargado) return; // evitar doble carga


          //1 ubicar diagnostico.pl

        URL url = PrologDiagnosticoDAO.class
                .getClassLoader()
                .getResource("prolog/diagnostico.pl");

        if (url == null) {
            throw new RuntimeException("ERROR: No se encontró diagnostico.pl dentro de resources/prolog/");
        }

        String prologPath;

        try {
            // Convierte file:/C:/... a ruta válida
            prologPath = Paths.get(url.toURI()).toString();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo convertir la ruta del archivo Prolog", e);
        }

        prologPath = prologPath.replace("\\", "/"); // Prolog exige /

        System.out.println("Consultando archivo Prolog en: " + prologPath);

        Query cargarArchivo = new Query("consult('" + prologPath + "')");

        if (!cargarArchivo.hasSolution()) {
            throw new RuntimeException("Fallo al consultar diagnostico.pl");
        }
        cargarArchivo.close();

        cargarHechosDesdeBD();

        prologCargado = true;
    }

    private static void cargarHechosDesdeBD() {

        EnfermedadDAO enfermedadDAO = new EnfermedadDAO();
        SintomaDAO sintomaDAO = new SintomaDAO();

        // --- enfermedades ---
        for (Enfermedad e : enfermedadDAO.getAll()) {
            String fact = "assertz(enfermedad(" + e.getId() + ", '" + e.getNombre() + "'))";
            new Query(fact).hasSolution();
        }

        // --- síntomas ---
        for (Sintoma s : sintomaDAO.getAll()) {
            String fact = "assertz(sintoma(" + s.getId() + ", '" + s.getNombre() + "'))";
            new Query(fact).hasSolution();
        }

        // --- relaciones enfermedad-sintoma ---
        try {
            var conn = com.mycompany.paradigmas_proyecto_final.database.MySQLConnection
                    .getInstance()
                    .getConnection();

            var ps = conn.prepareStatement("SELECT enfermedad_id, sintoma_id FROM enfermedad_sintoma");
            var rs = ps.executeQuery();

            while (rs.next()) {
                int e = rs.getInt(1);
                int s = rs.getInt(2);
                String fact = "assertz(enfermedad_sintoma(" + e + "," + s + "))";
                new Query(fact).hasSolution();
            }

            rs.close();
            ps.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

       //3 Ejecutar el diagnóstico real en Prolog
    public static List<ResultadoDiagnosticoProlog> diagnosticar(List<Integer> sintomasUsuario) {

        inicializarProlog(); // <-- YA FUNCIONAL

        List<ResultadoDiagnosticoProlog> resultados = new ArrayList<>();

        // Convertir lista Java → lista Prolog (ej: [1,2,3])
        String lista = sintomasUsuario.toString();

        Query q = new Query("diagnosticar(" + lista + ", Enf, P)");

        while (q.hasMoreSolutions()) {

            Map<String, Term> sol = q.nextSolution();

            int enfermedadId = sol.get("Enf").intValue();
            double porcentaje = sol.get("P").doubleValue();

            resultados.add(new ResultadoDiagnosticoProlog(enfermedadId, porcentaje));
        }

        q.close();
        return resultados;
    }


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
