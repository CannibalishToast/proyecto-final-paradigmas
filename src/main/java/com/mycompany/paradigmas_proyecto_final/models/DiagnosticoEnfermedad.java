/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.models;

public class DiagnosticoEnfermedad {

    private int id;
    private int diagnosticoId;
    private int enfermedadId;
    private Integer coincidenciaPorcentaje; // puede ser null

    public DiagnosticoEnfermedad() {}

    public DiagnosticoEnfermedad(int id, int diagnosticoId, int enfermedadId, Integer coincidenciaPorcentaje) {
        this.id = id;
        this.diagnosticoId = diagnosticoId;
        this.enfermedadId = enfermedadId;
        this.coincidenciaPorcentaje = coincidenciaPorcentaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiagnosticoId() {
        return diagnosticoId;
    }

    public void setDiagnosticoId(int diagnosticoId) {
        this.diagnosticoId = diagnosticoId;
    }

    public int getEnfermedadId() {
        return enfermedadId;
    }

    public void setEnfermedadId(int enfermedadId) {
        this.enfermedadId = enfermedadId;
    }

    public Integer getCoincidenciaPorcentaje() {
        return coincidenciaPorcentaje;
    }

    public void setCoincidenciaPorcentaje(Integer coincidenciaPorcentaje) {
        this.coincidenciaPorcentaje = coincidenciaPorcentaje;
    }
}
