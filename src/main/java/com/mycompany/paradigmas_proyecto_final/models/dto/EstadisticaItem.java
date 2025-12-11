/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.models.dto;

public class EstadisticaItem {

    private String nombre;
    private int valor;

    public EstadisticaItem(String nombre, int valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() { return nombre; }

    public int getValor() { return valor; }

    @Override
    public String toString() {
        return nombre + " (" + valor + ")";
    }
}
