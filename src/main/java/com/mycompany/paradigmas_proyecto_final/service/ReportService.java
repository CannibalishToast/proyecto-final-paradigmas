/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.service;

import com.mycompany.paradigmas_proyecto_final.dao.PacienteDAO;
import com.mycompany.paradigmas_proyecto_final.models.Paciente;
import com.mycompany.paradigmas_proyecto_final.models.dto.HistorialDetallado;

import java.io.FileWriter;
import java.util.List;

public class ReportService {

    private HistorialService historialService = new HistorialService();
    private PacienteDAO pacienteDAO = new PacienteDAO();

    /**
     * Genera un CSV con el historial del paciente seleccionado.
     */
    public void generarReportePacienteCSV(int pacienteId, String rutaArchivo) throws Exception {

        Paciente p = pacienteDAO.getById(pacienteId);
        List<HistorialDetallado> historial = historialService.obtenerHistorialParaReporte(pacienteId);

        FileWriter fw = new FileWriter(rutaArchivo);

        fw.write("Reporte del Paciente: " + p.getNombre() + " (Edad " + p.getEdad() + ")\n");
        fw.write("ID Diagnóstico,Fecha,Síntomas,Enfermedades\n");

        for (HistorialDetallado h : historial) {

            String sintomas = String.join(" | ", h.getSintomas());

            StringBuilder enfBuilder = new StringBuilder();
            for (HistorialDetallado.EnfermedadResultado er : h.getEnfermedades()) {
                enfBuilder.append(er.getNombre())
                        .append(" (")
                        .append(er.getPorcentajeCoincidencia())
                        .append("%) | ");
            }

            fw.write(
                    h.getDiagnosticoId() + "," +
                    h.getFecha() + "," +
                    "\"" + sintomas + "\"," +
                    "\"" + enfBuilder.toString() + "\"\n"
            );
        }

        fw.close();
    }
}
