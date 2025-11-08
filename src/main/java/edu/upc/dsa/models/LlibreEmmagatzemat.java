package edu.upc.dsa.models;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

public class LlibreEmmagatzemat extends Llibre {
    @XmlElement(name = "id")
    private String id;

    public LlibreEmmagatzemat(String id, String isbn, String titol, String editorial, int anyPublicacio, int numEdicio, String autor, String tematica) {
        super(isbn, titol, editorial, anyPublicacio, numEdicio, autor, tematica);
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
        return "LlibreEmmagatzemat{ID=" + id + ", ISBN=" + isbn + ", Titol='" + titol + "'}";
    }
}
