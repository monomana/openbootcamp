package com.company;

public class Tarea {
    private String fecha;
    private String usuario;
    private String tarea;

    public Tarea(String fecha, String usuario, String tarea) {
        this.fecha = fecha;
        this.usuario = usuario;
        this.tarea = tarea;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }
}
