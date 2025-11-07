package edu.upc.dsa.models;

import java.util.Objects;

public class LlibreCatalogat extends Llibre {
    private int exemplarsDisponibles; // Quantitat total d'exemplars d'aquest ISBN

    // Constructor quan es cataloga el PRIMER exemplar
    public LlibreCatalogat(String ISBN, String titol, String editorial, int anyPublicacio, int numEdicio, String autor, String tematica) {
        super(ISBN, titol, editorial, anyPublicacio, numEdicio, autor, tematica);
        this.exemplarsDisponibles = 1;
    }

    public LlibreCatalogat(){
        super();
    }

    public int getExemplarsDisponibles() {
        return exemplarsDisponibles;
    }

    // Mètodes per a la gestió d'inventari
    public void incrementarExemplars() {
        this.exemplarsDisponibles++;
    }

    public boolean decrementarExemplars() {
        if (this.exemplarsDisponibles > 0) {
            this.exemplarsDisponibles--;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LlibreCatalogat that = (LlibreCatalogat) o;
        return Objects.equals(ISBN, that.ISBN); // La clau és l'ISBN
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    @Override
    public String toString() {
        return "LlibreCatalogat{ISBN=" + ISBN + ", Titol='" + titol + "', Disponibles=" + exemplarsDisponibles + "}";
    }
}
