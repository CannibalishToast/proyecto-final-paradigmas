/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final;

import org.jpl7.Query;

public class TestProlog {

    public static void main(String[] args) {
        String ruta = "src/main/resources/prolog/diagnostico.pl";

        Query q = new Query("consult('" + ruta + "')");

        if (q.hasSolution()) {
            System.out.println("diagnostico.pl cargado correctamente.");
        } else {
            System.out.println("Error cargando diagnostico.pl");
        }
    }
}
