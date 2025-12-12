/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.prolog;

import com.mycompany.paradigmas_proyecto_final.dao.*;
import com.mycompany.paradigmas_proyecto_final.models.*;

import java.util.List;

public class FactsBuilder {

    private final EnfermedadDAO enfermedadDAO = new EnfermedadDAO();
    private final SintomaDAO sintomaDAO = new SintomaDAO();
    private final EnfermedadSintomaDAO enfermedadSintomaDAO = new EnfermedadSintomaDAO();

    public void initProlog() {
        PrologQueryExecutor.loadPrologFile("src/main/resources/prolog/diagnostico.pl");
    }

    public void limpiarBase() {
        PrologQueryExecutor.createDynamicFact("retractall(enfermedad(_, _))");
        PrologQueryExecutor.createDynamicFact("retractall(sintoma(_, _))");
        PrologQueryExecutor.createDynamicFact("retractall(enfermedad_sintoma(_, _))");
    }

    public void construirFactsEnfermedades() {
        List<Enfermedad> lista = enfermedadDAO.getAll();

        for (Enfermedad e : lista) {
            String fact = String.format(
                    "assertz(enfermedad(%d,%s))",
                    e.getId(),
                    PrologQueryExecutor.toAtom(e.getNombre())
            );
            PrologQueryExecutor.createDynamicFact(fact);
        }
    }

    public void construirFactsSintomas() {
        List<Sintoma> lista = sintomaDAO.getAll();

        for (Sintoma s : lista) {
            String fact = String.format(
                    "assertz(sintoma(%d,%s))",
                    s.getId(),
                    PrologQueryExecutor.toAtom(s.getNombre())
            );
            PrologQueryExecutor.createDynamicFact(fact);
        }
    }

    public void construirFactsEnfermedadSintoma() {
        List<EnfermedadSintoma> lista = enfermedadSintomaDAO.getAll();

        for (EnfermedadSintoma es : lista) {
            String fact = String.format(
                    "assertz(enfermedad_sintoma(%d,%d))",
                    es.getEnfermedadId(),
                    es.getSintomaId()
            );
            PrologQueryExecutor.createDynamicFact(fact);
        }
    }

    public void construirTodo() {
        limpiarBase(); 
        construirFactsEnfermedades();
        construirFactsSintomas();
        construirFactsEnfermedadSintoma();
    }
}
