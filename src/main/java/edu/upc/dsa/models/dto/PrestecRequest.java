package edu.upc.dsa.models.dto;

public class PrestecRequest {
    private String idLector;
    private String isbnLlibreCatalogat;

    public String getIdLector() { return idLector; }
    public void setIdLector(String idLector) { this.idLector = idLector; }

    public String getIsbnLlibreCatalogat() { return isbnLlibreCatalogat; }
    public void setIsbnLlibreCatalogat(String isbnLlibreCatalogat) { this.isbnLlibreCatalogat = isbnLlibreCatalogat; }

    public PrestecRequest(String idLector, String isbnLlibreCatalogat) {
        this.idLector = idLector;
        this.isbnLlibreCatalogat = isbnLlibreCatalogat;
    }
    public PrestecRequest() {}
}
