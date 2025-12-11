/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.paradigmas_proyecto_final;

import com.mycompany.paradigmas_proyecto_final.prolog.FactsBuilder;
import java.util.Map;
import org.jpl7.Query;
import org.jpl7.Term;

public class Paradigmas_proyecto_final {

    public static void main(String[] args) {

            FactsBuilder fb = new FactsBuilder();

            // 1. Cargar reglas Prolog
            fb.initProlog();

            // 2. Convertir BD → hechos dinámicos
            fb.construirTodo();

            // 3. Probar diagnóstico
            Query q = new Query("diagnosticar([1,2], Enf, P)");
            if (q.hasMoreSolutions()) {
                Map<String,Term> sol = q.nextSolution();
                System.out.println(sol);
            }
        }

}

