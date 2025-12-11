/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.ui;

import com.mycompany.paradigmas_proyecto_final.dao.PacienteDAO;
import com.mycompany.paradigmas_proyecto_final.models.Paciente;

import javax.swing.*;
import java.awt.*;

public class PanelRegistrarPaciente extends JPanel {

    private JTextField txtNombre;
    private JTextField txtEdad;
    private PacienteDAO pacienteDAO = new PacienteDAO();

    public PanelRegistrarPaciente() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Registro de Pacientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        // -------------------------------
        // FORMULARIO
        // -------------------------------
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Nombre:"), gbc);

        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        panelForm.add(txtNombre, gbc);

        // Edad
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Edad:"), gbc);

        txtEdad = new JTextField(5);
        gbc.gridx = 1;
        panelForm.add(txtEdad, gbc);

        add(panelForm, BorderLayout.CENTER);

        // -------------------------------
        // BOTÓN REGISTRAR
        // -------------------------------
        JButton btnRegistrar = new JButton("Registrar Paciente");
        btnRegistrar.addActionListener(e -> registrarPaciente());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnRegistrar);

        add(panelBoton, BorderLayout.SOUTH);
    }

    // ===========================================================
    // MÉTODO: Registrar paciente
    // ===========================================================
    private void registrarPaciente() {
        String nombre = txtNombre.getText().trim();
        String edadStr = txtEdad.getText().trim();

        if (nombre.isEmpty() || edadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.");
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Edad inválida.");
            return;
        }

        Paciente p = new Paciente(0, nombre, edad);
        int id = pacienteDAO.insertar(p);

        if (id > 0) {
            JOptionPane.showMessageDialog(this, "Paciente registrado correctamente.");
            txtNombre.setText("");
            txtEdad.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Error registrando paciente.");
        }
    }
}

