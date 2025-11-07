package edu.upc.dsa.models;

import java.time.LocalDate;
import java.util.Objects;

public class Prestec {
    private String idPrestec;
    private String idLector;
    private String ISBNLlibreCatalogat;
    private LocalDate dataPrestec;
    private LocalDate dataFinalDevolucio;
    private String estat; // P. ex.: "En tràmit" [cite: 28]

    public Prestec(String idPrestec, String idLector, String ISBNLlibreCatalogat, LocalDate dataPrestec, LocalDate dataFinalDevolucio) {
        this.idPrestec = idPrestec;
        this.idLector = idLector;
        this.ISBNLlibreCatalogat = ISBNLlibreCatalogat;
        this.dataPrestec = dataPrestec;
        this.dataFinalDevolucio = dataFinalDevolucio;
        this.estat = "En tràmit";
    }

    public Prestec() {}

    public String getIdPrestec() {
        return idPrestec;
    }
    public String getIdLector() {
        return idLector;
    }
    public String getISBNLlibreCatalogat() {
        return ISBNLlibreCatalogat;
    }
    public LocalDate getDataPrestec() {
        return dataPrestec;
    }
    public LocalDate getDataFinalDevolucio() {
        return dataFinalDevolucio;
    }
    public String getEstat() {
        return estat;
    }

    // --- SETTERS ---
    // Només es permet canviar l'estat per gestionar devolucions
    public void setEstat(String estat) {
        this.estat = estat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestec prestec = (Prestec) o;
        return Objects.equals(idPrestec, prestec.idPrestec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrestec);
    }

    @Override
    public String toString() {
        return "Prestec{ID=" + idPrestec + ", Lector=" + idLector + ", ISBN=" + ISBNLlibreCatalogat + ", Estat=" + estat + "}";
    }
}
