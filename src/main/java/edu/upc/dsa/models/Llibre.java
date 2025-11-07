package edu.upc.dsa.models;

public abstract class Llibre {
    protected String ISBN;
    protected String titol;
    protected String editorial;
    protected int anyPublicacio;
    protected int numEdicio;
    protected String autor;
    protected String tematica;

    public Llibre(String ISBN, String titol, String editorial, int anyPublicacio, int numEdicio, String autor, String tematica) {
        this.ISBN = ISBN;
        this.titol = titol;
        this.editorial = editorial;
        this.anyPublicacio = anyPublicacio;
        this.numEdicio = numEdicio;
        this.autor = autor;
        this.tematica = tematica;
    }

    public Llibre() {}

    public String getISBN() {
        return ISBN;
    }
    public String getTitol() {
        return titol;
    }
    public String getEditorial() {
        return editorial;
    }
    public int getAnyPublicacio() {
        return anyPublicacio;
    }
    public int getNumEdicio() {
        return numEdicio;
    }
    public String getAutor() {
        return autor;
    }
    public String getTematica() {
        return tematica;
    }
}
