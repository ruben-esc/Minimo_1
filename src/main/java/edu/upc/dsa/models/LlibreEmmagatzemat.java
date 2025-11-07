package edu.upc.dsa.models;

import java.util.Objects;

public class LlibreEmmagatzemat extends Llibre {
    private String id;

    public LlibreEmmagatzemat(String id, String ISBN, String titol, String editorial, int anyPublicacio, int numEdicio, String autor, String tematica) {
        super(ISBN, titol, editorial, anyPublicacio, numEdicio, autor, tematica);
        this.id = id;
    }

    public LlibreEmmagatzemat() {
        super();
    }

    public String getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LlibreEmmagatzemat that = (LlibreEmmagatzemat) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LlibreEmmagatzemat{ID=" + id + ", ISBN=" + ISBN + ", Titol='" + titol + "'}";
    }
}
