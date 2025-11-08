package edu.upc.dsa.models;

import javax.xml.bind.annotation.XmlElement;

public abstract class Llibre {
    @XmlElement(name = "isbn")
    protected String isbn;

    @XmlElement(name = "titol")
    protected String titol;

    @XmlElement(name = "editorial")
    protected String editorial;

    @XmlElement(name = "anyPublicacio")
    protected int anyPublicacio;

    @XmlElement(name = "numEdicio")
    protected int numEdicio;

    @XmlElement(name = "autor")
    protected String autor;

    @XmlElement(name = "tematica")
    protected String tematica;

    public Llibre(String isbn, String titol, String editorial, int anyPublicacio, int numEdicio, String autor, String tematica) {
        this.isbn = isbn;
        this.titol = titol;
        this.editorial = editorial;
        this.anyPublicacio = anyPublicacio;
        this.numEdicio = numEdicio;
        this.autor = autor;
        this.tematica = tematica;
    }

    public Llibre() {}

    public String getIsbn() {
        return isbn;
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
