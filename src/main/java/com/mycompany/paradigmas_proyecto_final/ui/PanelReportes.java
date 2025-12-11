/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.ui;

import com.mycompany.paradigmas_proyecto_final.dao.PacienteDAO;
import com.mycompany.paradigmas_proyecto_final.models.Paciente;
import com.mycompany.paradigmas_proyecto_final.models.dto.HistorialDetallado;
import com.mycompany.paradigmas_proyecto_final.service.HistorialService;
import com.mycompany.paradigmas_proyecto_final.service.ReportService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelReportes extends JPanel {

    private ReportService reportService = new ReportService();
    private HistorialService historialService = new HistorialService();
    private PacienteDAO pacienteDAO = new PacienteDAO();

    private JComboBox<Paciente> comboPacientes;
    private JTable tablaHistorial;

    public PanelReportes() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Reportes: Resumen por Paciente", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        // ===============================
        // PANEL SUPERIOR CON CONTROLES
        // ===============================
        JPanel panelTop = new JPanel();

        comboPacientes = new JComboBox<>();
        cargarPacientes();

        JButton btnVer = new JButton("Ver Historial");
        JButton btnCSV = new JButton("Exportar CSV");

        panelTop.add(new JLabel("Paciente:"));
        panelTop.add(comboPacientes);
        panelTop.add(btnVer);
        panelTop.add(btnCSV);

        add(panelTop, BorderLayout.NORTH);

        // ===============================
        // TABLA DE HISTORIAL
        // ===============================
        tablaHistorial = new JTable();
        tablaHistorial.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Fecha", "Síntomas", "Enfermedades"}
        ));

        add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);

        // ===============================
        // EVENTOS
        // ===============================

        btnVer.addActionListener(e -> cargarHistorial());

        btnCSV.addActionListener(e -> exportarCSV());
    }

    private void cargarPacientes() {
        comboPacientes.removeAllItems();
        for (Paciente p : pacienteDAO.getAll()) {
            comboPacientes.addItem(p);
        }
    }

    private void cargarHistorial() {
        Paciente p = (Paciente) comboPacientes.getSelectedItem();
        if (p == null) return;

        List<HistorialDetallado> historial = historialService.obtenerHistorialPaciente(p.getId());

        DefaultTableModel model = (DefaultTableModel) tablaHistorial.getModel();
        model.setRowCount(0);

        for (HistorialDetallado h : historial) {
            String sintomas = String.join(" | ", h.getSintomas());

            StringBuilder enfermedades = new StringBuilder();
            for (HistorialDetallado.EnfermedadResultado er : h.getEnfermedades()) {
                enfermedades.append(er.getNombre())
                        .append(" (")
                        .append(er.getPorcentajeCoincidencia())
                        .append("%) | ");
            }

            model.addRow(new Object[]{
                    h.getDiagnosticoId(),
                    h.getFecha(),
                    sintomas,
                    enfermedades.toString()
            });
        }
    }

    private void exportarCSV() {
        Paciente p = (Paciente) comboPacientes.getSelectedItem();
        if (p == null) return;

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar Reporte CSV");

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                reportService.generarReportePacienteCSV(
                        p.getId(),
                        chooser.getSelectedFile().getAbsolutePath()
                );
                JOptionPane.showMessageDialog(this, "CSV generado correctamente.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error generando CSV.");
            }
        }
    }
}

