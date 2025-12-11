/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.models;

public class DiagnosticoSintoma {

    private int id;
    private int diagnosticoId;
    private int sintomaId;

    public DiagnosticoSintoma() {}

    public DiagnosticoSintoma(int id, int diagnosticoId, int sintomaId) {
        this.id = id;
        this.diagnosticoId = diagnosticoId;
        this.sintomaId = sintomaId;
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

    public int getSintomaId() {
        return sintomaId;
    }

    public void setSintomaId(int sintomaId) {
        this.sintomaId = sintomaId;
    }
}
