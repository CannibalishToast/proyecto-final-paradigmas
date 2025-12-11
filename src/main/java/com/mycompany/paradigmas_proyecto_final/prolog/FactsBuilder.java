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

    /** Cargar archivo PL */
    public void initProlog() {
        PrologQueryExecutor.loadPrologFile("src/main/resources/prolog/diagnostico.pl");
    }

    /** 🔥 Limpia la base dinámica dentro de Prolog */
    public void limpiarBase() {
        PrologQueryExecutor.createDynamicFact("retractall(enfermedad(_, _))");
        PrologQueryExecutor.createDynamicFact("retractall(sintoma(_, _))");
        PrologQueryExecutor.createDynamicFact("retractall(enfermedad_sintoma(_, _))");
    }

    /** Hechos: enfermedad(Id, Nombre) */
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

    /** Hechos: sintoma(Id, Nombre) */
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

    /** Hechos: enfermedad_sintoma(IdEnf, IdSint) */
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

    /** Construye TODO — limpio, seguro, sin duplicados */
    public void construirTodo() {
        limpiarBase(); // <-- 🔥 clave para evitar hechos duplicados
        construirFactsEnfermedades();
        construirFactsSintomas();
        construirFactsEnfermedadSintoma();
    }
}
