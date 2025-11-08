package edu.upc.dsa;

import java.time.LocalDate;
import java.util.List;

import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.LlibreCatalogat;
import edu.upc.dsa.models.LlibreEmmagatzemat;
import edu.upc.dsa.models.Prestec;

import edu.upc.dsa.exceptions.NoHiHaLlibresACatalogarException;
import edu.upc.dsa.exceptions.PrestecException;

public interface BiblioManager {

    boolean afegirNouLector(Lector lector);

    void emmagatzemarLlibre(LlibreEmmagatzemat llibre);

    LlibreCatalogat catalogarLlibre() throws NoHiHaLlibresACatalogarException;

    void prestarLlibre(String idLector, String isbnLlibre) throws PrestecException;

    List<Prestec> consultarPrestecsLector(String idLector);
}
