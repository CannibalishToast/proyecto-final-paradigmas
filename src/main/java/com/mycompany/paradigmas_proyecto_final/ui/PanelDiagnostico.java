/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.ui;

import com.mycompany.paradigmas_proyecto_final.dao.PacienteDAO;
import com.mycompany.paradigmas_proyecto_final.dao.SintomaDAO;
import com.mycompany.paradigmas_proyecto_final.models.Paciente;
import com.mycompany.paradigmas_proyecto_final.models.Sintoma;
import com.mycompany.paradigmas_proyecto_final.service.DiagnosticoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelDiagnostico extends JPanel {

    private JComboBox<Paciente> comboPacientes;
    private JPanel panelCheckboxes;
    private JTable tablaResultados;
    private JButton btnDiagnosticar;

    private SintomaDAO sintomaDAO = new SintomaDAO();
    private PacienteDAO pacienteDAO = new PacienteDAO();

    private DiagnosticoService diagnosticoService = new DiagnosticoService();

    public PanelDiagnostico() {
        setLayout(new BorderLayout());

        // ---------------------------------------------------------------------
        // TÍTULO
        // ---------------------------------------------------------------------
        JLabel titulo = new JLabel("Módulo de Diagnóstico", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // ---------------------------------------------------------------------
        // PANEL IZQUIERDO
        // ---------------------------------------------------------------------
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setPreferredSize(new Dimension(300, 0));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Selección de paciente ----
        comboPacientes = new JComboBox<>();
        cargarPacientes();

        JPanel panelPaciente = new JPanel(new BorderLayout());
        panelPaciente.add(new JLabel("Paciente:"), BorderLayout.NORTH);
        panelPaciente.add(comboPacientes, BorderLayout.CENTER);
        panelIzquierdo.add(panelPaciente, BorderLayout.NORTH);

        // ---- Lista de síntomas ----
        panelCheckboxes = new JPanel();
        panelCheckboxes.setLayout(new BoxLayout(panelCheckboxes, BoxLayout.Y_AXIS));
        cargarSintomas();

        JScrollPane scrollSintomas = new JScrollPane(panelCheckboxes);
        scrollSintomas.setBorder(BorderFactory.createTitledBorder("Síntomas"));
        panelIzquierdo.add(scrollSintomas, BorderLayout.CENTER);

        // ---- Botón Diagnosticar ----
        btnDiagnosticar = new JButton("Realizar diagnóstico");
        btnDiagnosticar.addActionListener(e -> realizarDiagnostico());
        panelIzquierdo.add(btnDiagnosticar, BorderLayout.SOUTH);

        add(panelIzquierdo, BorderLayout.WEST);

        // PANEL DERECHO: Tabla de resultados
        tablaResultados = new JTable();
        tablaResultados.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Enfermedad", "Coincidencia (%)", "Recomendación"}
        ));

        JScrollPane scrollTabla = new JScrollPane(tablaResultados);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Resultados"));
        add(scrollTabla, BorderLayout.CENTER);
    }


    private void cargarPacientes() {
        List<Paciente> pacientes = pacienteDAO.getAll();
        for (Paciente p : pacientes) {
            comboPacientes.addItem(p);
        }
    }

    private void cargarSintomas() {
        List<Sintoma> sintomas = sintomaDAO.getAll();
        for (Sintoma s : sintomas) {
            JCheckBox cb = new JCheckBox(s.getNombre());
            cb.putClientProperty("id", s.getId());
            panelCheckboxes.add(cb);
        }
    }

    // Realizar diagnóstico conectado a SERVICE REAL

    private void realizarDiagnostico() {
        Paciente paciente = (Paciente) comboPacientes.getSelectedItem();
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener síntomas seleccionados
        List<Integer> sintomasSeleccionados = new ArrayList<>();

        for (Component comp : panelCheckboxes.getComponents()) {
            if (comp instanceof JCheckBox cb) {
                if (cb.isSelected()) {
                    int id = (int) cb.getClientProperty("id");
                    sintomasSeleccionados.add(id);
                }
            }
        }

        if (sintomasSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un síntoma.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ejecutar diagnóstico REAL
        List<DiagnosticoService.ResultadoDiagnostico> resultados =
                diagnosticoService.realizarDiagnostico(paciente.getId(), sintomasSeleccionados);

        // Limpiar tabla
        DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
        model.setRowCount(0);

        // Rellenar tabla
        for (DiagnosticoService.ResultadoDiagnostico r : resultados) {
            model.addRow(new Object[]{
                    r.getEnfermedad(),
                    String.format("%.2f", r.getPorcentaje()),
                    r.getRecomendacion()
            });
        }

        JOptionPane.showMessageDialog(this, "Diagnóstico realizado correctamente.");
    }
}
