package com.example.juanb.aero;

import android.app.Application;


public class Myglobalvar extends Application{

    private String someVariable,ID,Nombre,Apellido,Pass,Residencia,Edad;

    public String getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }

    public String getID() { return ID; }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() { return Nombre; }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() { return Apellido; }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getPass() { return Pass; }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public String getResidencia() { return Residencia; }

    public void setResidencia(String Residencia) {
        this.Residencia = Residencia;
    }

    public String getEdad() { return Edad; }

    public void setEdad(String Edad) {
        this.Edad = Edad;
    }



}

