package com.easytask.adaptet.navigationObjects;

/**
 * Created by danny on 30/10/14.
 */
public class Navigation_Object {


    private String titutlo;
    private int icono;

    public Navigation_Object(String titutlo, int icono) {
        this.titutlo = titutlo;
        this.icono = icono;
    }

    public String getTitutlo() {
        return titutlo;
    }

    public void setTitutlo(String titutlo) {
        this.titutlo = titutlo;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

}
