/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.prolog;

import org.jpl7.Query;

public class PrologQueryExecutor {


    public static void loadPrologFile(String path) {
        String consulta = "consult('" + path.replace("\\", "/") + "')";
        Query q = new Query(consulta);
        if (!q.hasSolution()) {
            throw new RuntimeException("Error cargando archivo Prolog: " + path);
        }
        System.out.println("Archivo Prolog cargado: " + path);
    }

    public static void createDynamicFact(String fact) {
        Query q = new Query(fact);
        if (q.hasSolution()) {
            System.out.println("Hecho dinámico agregado: " + fact);
        } else {
            System.out.println("Error agregando hecho: " + fact);
        }
    }

    public static String toAtom(String s) {
        if (s == null) return "''";
        String safe = s
                .toLowerCase()
                .replace(" ", "_")
                .replace("-", "_")
                .replace("'", "\\'");
        return "'" + safe + "'";
    }
}
