package edu.upc.dsa;

import java.time.LocalDate;
import java.util.List;

import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.LlibreCatalogat;
import edu.upc.dsa.models.LlibreEmmagatzemat;
import edu.upc.dsa.models.Prestec;

import edu.upc.dsa.exceptions.NoHiHaLlibresACatalogarException;
import edu.upc.dsa.exceptions.PrestecException;

public interface BiblioManager { // Canviat de GestorBiblioteca

    // LECTORS
    boolean afegirNouLector(Lector lector);

    // LLIBRES EMMAGATZEMATS
    void emmagatzemarLlibre(LlibreEmmagatzemat llibre);

    // CATALOGACIÓ
    LlibreCatalogat catalogarLlibre() throws NoHiHaLlibresACatalogarException;

    // PRÉSTECS
    void prestarLlibre(String idPrestec, String idLector, String isbnLlibre,
                       LocalDate dataPrestec, LocalDate dataFinalDevolucio) throws PrestecException;

    List<Prestec> consultarPrestecsLector(String idLector);
}
