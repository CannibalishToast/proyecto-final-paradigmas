/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Sistema Experto de Diagnóstico Médico");
        setSize(900, 600);
        setLocationRelativeTo(null); // centrar ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Crear Tabs ---
        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Diagnóstico", new PanelDiagnostico());
        tabs.addTab("Historial", new PanelHistorial());
        tabs.addTab("Reportes", new PanelReportes());
        tabs.addTab("Pacientes", new PanelRegistrarPaciente());

        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}

