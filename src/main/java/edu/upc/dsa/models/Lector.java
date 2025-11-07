package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;
import java.time.LocalDate;
import java.util.Objects;

public class Lector {

    private String id;
    private String nom;
    private String cognoms;
    private String dni;
    private LocalDate dataNaixement;
    private String llocNaixement;
    private String adreça;

    public Lector(String identificador, String nom, String cognoms, String dni, LocalDate dataNaixement, String llocNaixement, String adreça) {
        this.id = identificador;
        this.nom = nom;
        this.cognoms = cognoms;
        this.dni = dni;
        this.dataNaixement = dataNaixement;
        this.llocNaixement = llocNaixement;
        this.adreça = adreça;
    }

    public Lector() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }
    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getDataNaixement() {
        return dataNaixement;
    }
    public void setDataNaixement(LocalDate dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    public String getLlocNaixement() {
        return llocNaixement;
    }
    public void setLlocNaixement(String llocNaixement) {
        this.llocNaixement = llocNaixement;
    }

    public String getAdreça() {
        return adreça;
    }
    public void setAdreça(String adreça) {
        this.adreça = adreça;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lector lector = (Lector) o;
        return Objects.equals(id, lector.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Lector{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", cognoms='" + cognoms + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }

}