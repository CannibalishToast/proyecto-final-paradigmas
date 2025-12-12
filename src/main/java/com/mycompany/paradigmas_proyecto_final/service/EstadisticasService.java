/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.service;

import com.mycompany.paradigmas_proyecto_final.dao.*;
import com.mycompany.paradigmas_proyecto_final.models.dto.EstadisticaItem;
import com.mycompany.paradigmas_proyecto_final.models.Enfermedad;
import com.mycompany.paradigmas_proyecto_final.models.Sintoma;

import java.util.ArrayList;
import java.util.List;

public class EstadisticasService {

    private DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();
    private DiagnosticoSintomaDAO diagnosticoSintomaDAO = new DiagnosticoSintomaDAO();
    private DiagnosticoEnfermedadDAO diagnosticoEnfermedadDAO = new DiagnosticoEnfermedadDAO();
    private EnfermedadDAO enfermedadDAO = new EnfermedadDAO();
    private SintomaDAO sintomaDAO = new SintomaDAO();

    // ESTADÍSTICAS GLOBALES
    public List<EstadisticaItem> enfermedadesMasComunes() {
        List<EstadisticaItem> lista = new ArrayList<>();

        for (Enfermedad e : enfermedadDAO.getAll()) {
            int cantidad = diagnosticoDAO.contarPorEnfermedad(e.getId());
            lista.add(new EstadisticaItem(e.getNombre(), cantidad));
        }

        lista.sort((a, b) -> Integer.compare(b.getValor(), a.getValor()));
        return lista;
    }

    public List<EstadisticaItem> sintomasMasComunes() {
        List<EstadisticaItem> lista = new ArrayList<>();

        for (Sintoma s : sintomaDAO.getAll()) {
            int cantidad = diagnosticoDAO.contarPorSintoma(s.getId());
            lista.add(new EstadisticaItem(s.getNombre(), cantidad));
        }

        lista.sort((a, b) -> Integer.compare(b.getValor(), a.getValor()));
        return lista;
    }

    // ESTADÍSTICAS INDIVIDUALES POR PACIENTE

    public int totalDiagnosticosPaciente(int pacienteId) {
        return diagnosticoDAO.getByPaciente(pacienteId).size();
    }

    public List<EstadisticaItem> enfermedadesFrecuentesPaciente(int pacienteId) {

        List<EstadisticaItem> lista = new ArrayList<>();

        for (Enfermedad e : enfermedadDAO.getAll()) {
            int count = diagnosticoDAO.contarPorEnfermedadYPaciente(e.getId(), pacienteId);
            if (count > 0)
                lista.add(new EstadisticaItem(e.getNombre(), count));
        }

        lista.sort((a, b) -> Integer.compare(b.getValor(), a.getValor()));
        return lista;
    }

    public List<EstadisticaItem> sintomasFrecuentesPaciente(int pacienteId) {

        List<EstadisticaItem> lista = new ArrayList<>();

        for (Sintoma s : sintomaDAO.getAll()) {
            int count = diagnosticoDAO.contarPorSintomaYPaciente(s.getId(), pacienteId);
            if (count > 0)
                lista.add(new EstadisticaItem(s.getNombre(), count));
        }

        lista.sort((a, b) -> Integer.compare(b.getValor(), a.getValor()));
        return lista;
    }
}
