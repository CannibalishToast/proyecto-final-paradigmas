/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.ui;

import com.mycompany.paradigmas_proyecto_final.dao.PacienteDAO;
import com.mycompany.paradigmas_proyecto_final.models.Paciente;
import com.mycompany.paradigmas_proyecto_final.service.HistorialService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelHistorial extends JPanel {

    private JComboBox<Paciente> comboPacientes;
    private JTable tablaHistorial;
    private JTextArea areaSintomas;

    private PacienteDAO pacienteDAO = new PacienteDAO();
    private HistorialService historialService = new HistorialService();

    public PanelHistorial() {
        setLayout(new BorderLayout());

        // -------------------------------------------------------------
        // TÍTULO
        // -------------------------------------------------------------
        JLabel titulo = new JLabel("Historial de Diagnósticos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // -------------------------------------------------------------
        // PANEL SUPERIOR: Selección de paciente
        // -------------------------------------------------------------
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Paciente:"));

        comboPacientes = new JComboBox<>();
        cargarPacientes();
        panelSuperior.add(comboPacientes);

        JButton btnCargar = new JButton("Cargar historial");
        btnCargar.addActionListener(e -> cargarHistorial());
        panelSuperior.add(btnCargar);

        add(panelSuperior, BorderLayout.NORTH);

        // -------------------------------------------------------------
        // TABLA del historial (enfermedad, fecha, recomendación)
        // -------------------------------------------------------------
        tablaHistorial = new JTable();
        tablaHistorial.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Fecha", "Enfermedad", "Coincidencia (%)", "Recomendación"}
        ));

        tablaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Listener para mostrar síntomas al seleccionar una fila
        tablaHistorial.getSelectionModel().addListSelectionListener(e -> mostrarSintomasSeleccion());

        JScrollPane scrollTabla = new JScrollPane(tablaHistorial);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Resultados del historial"));

        add(scrollTabla, BorderLayout.CENTER);

        // -------------------------------------------------------------
        // PANEL INFERIOR: Síntomas del diagnóstico seleccionado
        // -------------------------------------------------------------
        areaSintomas = new JTextArea(6, 40);
        areaSintomas.setEditable(false);
        areaSintomas.setLineWrap(true);

        JScrollPane scrollSintomas = new JScrollPane(areaSintomas);
        scrollSintomas.setBorder(BorderFactory.createTitledBorder("Síntomas del diagnóstico"));

        add(scrollSintomas, BorderLayout.SOUTH);
    }

    // ===================================================================
    // CARGAR PACIENTES
    // ===================================================================
    private void cargarPacientes() {
        List<Paciente> pacientes = pacienteDAO.getAll();
        for (Paciente p : pacientes) comboPacientes.addItem(p);
    }

    // ===================================================================
    // CARGA DEL HISTORIAL
    // ===================================================================
    private List<HistorialService.FilaHistorial> historialPlano;

    private void cargarHistorial() {
        Paciente paciente = (Paciente) comboPacientes.getSelectedItem();
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente.");
            return;
        }

        historialPlano = historialService.obtenerHistorialPlano(paciente.getId());

        DefaultTableModel model = (DefaultTableModel) tablaHistorial.getModel();
        model.setRowCount(0);

        for (HistorialService.FilaHistorial fila : historialPlano) {
            model.addRow(new Object[]{
                    fila.getFecha(),
                    fila.getEnfermedad(),
                    fila.getPorcentaje(),
                    fila.getRecomendacion()
            });
        }

        areaSintomas.setText(""); // limpiar
    }

    // ===================================================================
    // MOSTRAR SÍNTOMAS DE LA FILA SELECCIONADA
    // ===================================================================
    private void mostrarSintomasSeleccion() {
        int filaSel = tablaHistorial.getSelectedRow();
        if (filaSel < 0 || historialPlano == null) return;

        HistorialService.FilaHistorial fila = historialPlano.get(filaSel);

        StringBuilder sb = new StringBuilder();
        for (String s : fila.getSintomas()) {
            sb.append("- ").append(s).append("\n");
        }

        areaSintomas.setText(sb.toString());
    }
}


