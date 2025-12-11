package com.mycompany.paradigmas_proyecto_final.models;

public class Enfermedad {

    private int id;
    private String nombre;
    private String descripcion;
    private int categoriaId;
    private String recomendacion;

    public Enfermedad() {}

    public Enfermedad(int id, String nombre, String descripcion, int categoriaId, String recomendacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoriaId = categoriaId;
        this.recomendacion = recomendacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }
}
