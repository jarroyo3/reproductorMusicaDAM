package com.example.jorge.reproductormultimedia.widget.cancion;

import java.io.Serializable;

public class Cancion implements Serializable {
    private String id;
    private String titulo;
    private String duracion;
    private String ruta;

    public Cancion(String titulo, String duracion, String ruta) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.ruta = ruta;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
