package edu.upc.dsa.models;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;
import java.util.Objects;

public class Prestec {
    @XmlElement(name = "idPrestec")
    private String idPrestec;

    @XmlElement(name="idLector")
    private String idLector;

    @XmlElement(name="isbnllibreCatalogat")
    private String isbnLlibreCatalogat;

    @XmlElement(name="dataPrestec")
    private LocalDate dataPrestec;

    @XmlElement(name="dataFinalDevolucio")
    private LocalDate dataFinalDevolucio;

    @XmlElement(name="estat")
    private String estat;

    public Prestec(String idPrestec, String idLector, String isbnLlibreCatalogat, LocalDate dataPrestec, LocalDate dataFinalDevolucio) {
        this.idPrestec = idPrestec;
        this.idLector = idLector;
        this.isbnLlibreCatalogat = isbnLlibreCatalogat;
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
    public String getIsbnLlibreCatalogat() {
        return isbnLlibreCatalogat;
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
        return "Prestec{ID=" + idPrestec + ", Lector=" + idLector + ", ISBN=" + isbnLlibreCatalogat + ", Estat=" + estat + "}";
    }
}
