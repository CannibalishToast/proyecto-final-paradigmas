/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.models;

public class EnfermedadSintoma {

    private int id;
    private int enfermedadId;
    private int sintomaId;

    public EnfermedadSintoma() {}

    public EnfermedadSintoma(int id, int enfermedadId, int sintomaId) {
        this.id = id;
        this.enfermedadId = enfermedadId;
        this.sintomaId = sintomaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnfermedadId() {
        return enfermedadId;
    }

    public void setEnfermedadId(int enfermedadId) {
        this.enfermedadId = enfermedadId;
    }

    public int getSintomaId() {
        return sintomaId;
    }

    public void setSintomaId(int sintomaId) {
        this.sintomaId = sintomaId;
    }
}
