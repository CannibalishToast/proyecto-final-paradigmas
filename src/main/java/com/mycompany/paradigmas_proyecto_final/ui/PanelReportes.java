/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.ui;

/**
 *
 * @author danie
 */

import javax.swing.*;
import java.awt.*;

public class PanelReportes extends JPanel {

    public PanelReportes() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Generación de Reportes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        add(titulo, BorderLayout.NORTH);
    }
}

