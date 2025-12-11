/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.ui;

import com.mycompany.paradigmas_proyecto_final.dao.PacienteDAO;
import com.mycompany.paradigmas_proyecto_final.models.Paciente;
import com.mycompany.paradigmas_proyecto_final.models.dto.EstadisticaItem;
import com.mycompany.paradigmas_proyecto_final.service.HistorialService;
import com.mycompany.paradigmas_proyecto_final.service.EstadisticasService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelHistorial extends JPanel {

    private JComboBox<Paciente> comboPacientes;
    private JTable tablaHistorial;
    private JTextArea areaSintomas;

    private JTable tablaEstadEnfermedades;
    private JTable tablaEstadSintomas;

    private PacienteDAO pacienteDAO = new PacienteDAO();
    private HistorialService historialService = new HistorialService();
    private EstadisticasService estadisticasService = new EstadisticasService();

    private List<HistorialService.FilaHistorial> historialPlano;

    public PanelHistorial() {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Historial de Diagnósticos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // ==========================================================
        // PANEL SUPERIOR: PACIENTE + BOTONES
        // ==========================================================
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Paciente:"));

        comboPacientes = new JComboBox<>();
        cargarPacientes();
        panelSuperior.add(comboPacientes);

        JButton btnCargar = new JButton("Cargar historial");
        btnCargar.addActionListener(e -> cargarHistorial());
        panelSuperior.add(btnCargar);

        JButton btnActualizar = new JButton("Actualizar lista");
        btnActualizar.addActionListener(e -> refrescarPacientes());
        panelSuperior.add(btnActualizar);

        add(panelSuperior, BorderLayout.NORTH);

        // ==========================================================
        // TABLA HISTORIAL
        // ==========================================================
        tablaHistorial = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Fecha", "Enfermedad", "Coincidencia (%)", "Recomendación"}
        ));

        tablaHistorial.getSelectionModel()
                .addListSelectionListener(e -> mostrarSintomasSeleccion());

        JScrollPane scrollTabla = new JScrollPane(tablaHistorial);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Historial del paciente"));

        // ==========================================================
        // PANEL INFERIOR → ESTADÍSTICAS + SÍNTOMAS
        // ==========================================================
        JPanel panelInferior = new JPanel(new GridLayout(1, 3));

        areaSintomas = new JTextArea(6, 20);
        areaSintomas.setEditable(false);
        JScrollPane scrollSintomas = new JScrollPane(areaSintomas);
        scrollSintomas.setBorder(BorderFactory.createTitledBorder("Síntomas del diagnóstico"));

        tablaEstadEnfermedades = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Enfermedad", "Veces"}
        ));
        JScrollPane scrollEnf = new JScrollPane(tablaEstadEnfermedades);
        scrollEnf.setBorder(BorderFactory.createTitledBorder("Enfermedades más frecuentes"));

        tablaEstadSintomas = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Síntoma", "Veces"}
        ));
        JScrollPane scrollEst = new JScrollPane(tablaEstadSintomas);
        scrollEst.setBorder(BorderFactory.createTitledBorder("Síntomas más frecuentes"));

        panelInferior.add(scrollSintomas);
        panelInferior.add(scrollEnf);
        panelInferior.add(scrollEst);

        JSplitPane splitPane =
                new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTabla, panelInferior);
        splitPane.setDividerLocation(280);

        add(splitPane, BorderLayout.CENTER);
    }

    // ==========================================================
    // CARGAR PACIENTES UNA VEZ
    // ==========================================================
    private void cargarPacientes() {
        comboPacientes.removeAllItems();
        for (Paciente p : pacienteDAO.getAll())
            comboPacientes.addItem(p);
    }

    // ==========================================================
    // REFRESCAR LISTA DE PACIENTES (CALL DESDE BOTÓN)
    // ==========================================================
    private void refrescarPacientes() {
        cargarPacientes();
        JOptionPane.showMessageDialog(this, "Lista de pacientes actualizada");
    }

    // ==========================================================
    // CARGAR HISTORIAL DEL PACIENTE
    // ==========================================================
    private void cargarHistorial() {

        Paciente paciente = (Paciente) comboPacientes.getSelectedItem();
        if (paciente == null) return;

        historialPlano = historialService.obtenerHistorialPlano(paciente.getId());

        DefaultTableModel model =
                (DefaultTableModel) tablaHistorial.getModel();
        model.setRowCount(0);

        for (HistorialService.FilaHistorial fila : historialPlano) {
            model.addRow(new Object[]{
                    fila.getFecha(),
                    fila.getEnfermedad(),
                    fila.getPorcentaje(),
                    fila.getRecomendacion()
            });
        }

        areaSintomas.setText("");

        cargarEstadisticasPaciente(paciente.getId());
    }

    // ==========================================================
    // ESTADÍSTICAS DEL PACIENTE
    // ==========================================================
    private void cargarEstadisticasPaciente(int pacienteId) {

        DefaultTableModel m1 =
                (DefaultTableModel) tablaEstadEnfermedades.getModel();
        m1.setRowCount(0);

        for (EstadisticaItem item :
                estadisticasService.enfermedadesFrecuentesPaciente(pacienteId)) {
            m1.addRow(new Object[]{item.getNombre(), item.getValor()});
        }

        DefaultTableModel m2 =
                (DefaultTableModel) tablaEstadSintomas.getModel();
        m2.setRowCount(0);

        for (EstadisticaItem item :
                estadisticasService.sintomasFrecuentesPaciente(pacienteId)) {
            m2.addRow(new Object[]{item.getNombre(), item.getValor()});
        }
    }

    // ==========================================================
    // MOSTRAR SÍNTOMAS DE LA FILA SELECCIONADA
    // ==========================================================
    private void mostrarSintomasSeleccion() {
        int filaSel = tablaHistorial.getSelectedRow();
        if (filaSel < 0) return;

        HistorialService.FilaHistorial fila = historialPlano.get(filaSel);

        StringBuilder sb = new StringBuilder();
        for (String s : fila.getSintomas())
            sb.append("- ").append(s).append("\n");

        areaSintomas.setText(sb.toString());
    }
}
