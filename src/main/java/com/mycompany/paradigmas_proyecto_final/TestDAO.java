/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final;

import com.mycompany.paradigmas_proyecto_final.dao.*;
import com.mycompany.paradigmas_proyecto_final.models.*;

import java.util.List;

public class TestDAO {

    public static void main(String[] args) {

        System.out.println("=== TEST: Conexión y DAOs ===");

        // ============================
        // 1. Probar lectura de CATEGORÍAS
        // ============================
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Categoria> categorias = categoriaDAO.getAll();

        System.out.println("\n--- Categorías ---");
        categorias.forEach(c ->
                System.out.println(c.getId() + " - " + c.getNombre())
        );

        // ============================
        // 2. Probar lectura de ENFERMEDADES
        // ============================
        EnfermedadDAO enfermedadDAO = new EnfermedadDAO();
        List<Enfermedad> enfermedades = enfermedadDAO.getAll();

        System.out.println("\n--- Enfermedades ---");
        enfermedades.forEach(e ->
                System.out.println(e.getId() + " - " + e.getNombre() +
                        " | Categoria: " + e.getCategoriaId())
        );

        // ============================
        // 3. Probar lectura de SÍNTOMAS
        // ============================
        SintomaDAO sintomaDAO = new SintomaDAO();
        List<Sintoma> sintomas = sintomaDAO.getAll();

        System.out.println("\n--- Síntomas ---");
        sintomas.forEach(s ->
                System.out.println(s.getId() + " - " + s.getNombre())
        );

        // ============================
        // 4. Insertar un PACIENTE
        // ============================
        PacienteDAO pacienteDAO = new PacienteDAO();

        Paciente nuevo = new Paciente();
        nuevo.setNombre("Paciente de Prueba");
        nuevo.setEdad(30);

        int pacienteId = pacienteDAO.insertar(nuevo);
        System.out.println("\nPaciente insertado con ID: " + pacienteId);

        // ============================
        // 5. Crear un DIAGNÓSTICO
        // ============================
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();
        int diagnosticoId = diagnosticoDAO.insertar(pacienteId);
        System.out.println("Diagnóstico creado con ID: " + diagnosticoId);

        // ============================
        // 6. Asociar ENFERMEDADES al diagnóstico
        // ============================
        DiagnosticoEnfermedadDAO deDAO = new DiagnosticoEnfermedadDAO();

        DiagnosticoEnfermedad de1 = new DiagnosticoEnfermedad();
        de1.setDiagnosticoId(diagnosticoId);
        de1.setEnfermedadId(enfermedades.get(0).getId());
        de1.setCoincidenciaPorcentaje(80);
        deDAO.insertar(de1);

        DiagnosticoEnfermedad de2 = new DiagnosticoEnfermedad();
        de2.setDiagnosticoId(diagnosticoId);
        de2.setEnfermedadId(enfermedades.get(1).getId());
        de2.setCoincidenciaPorcentaje(40);
        deDAO.insertar(de2);

        System.out.println("Relaciones diagnóstico–enfermedad insertadas.");

        // ============================
        // 7. Asociar SÍNTOMAS al diagnóstico
        // ============================
        DiagnosticoSintomaDAO dsDAO = new DiagnosticoSintomaDAO();

        DiagnosticoSintoma ds1 = new DiagnosticoSintoma();
        ds1.setDiagnosticoId(diagnosticoId);
        ds1.setSintomaId(sintomas.get(0).getId());
        dsDAO.insertar(ds1);

        System.out.println("Relación diagnóstico–síntoma insertada.");

        // ============================
        // 8. Consultar historial del paciente
        // ============================
        System.out.println("\n--- Historial del paciente ---");
        List<Diagnostico> historial = diagnosticoDAO.getByPaciente(pacienteId);

        historial.forEach(d ->
                System.out.println("Diagnóstico ID: " + d.getId() + " | Fecha: " + d.getFecha())
        );

        System.out.println("\n=== FIN DEL TEST ===");
    }
}
