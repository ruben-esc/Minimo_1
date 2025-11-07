package edu.upc.dsa;

import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.LlibreCatalogat;
import edu.upc.dsa.models.LlibreEmmagatzemat;
import edu.upc.dsa.models.Prestec;

import edu.upc.dsa.exceptions.ExemplarsInsuficientsException;
import edu.upc.dsa.exceptions.LlibreNoExisteixException;
import edu.upc.dsa.exceptions.LectorNoExisteixException;
import edu.upc.dsa.exceptions.NoHiHaLlibresACatalogarException;
import edu.upc.dsa.exceptions.PrestecException;

import java.util.LinkedList;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public class BiblioManagerImpl implements BiblioManager {

    private static BiblioManagerImpl instance;

    final static Logger logger = Logger.getLogger(BiblioManagerImpl.class);

    protected final Map<String, Lector> lectors;
    protected final Map<String, LlibreCatalogat> llibresCatalogats;
    protected final List<Prestec> prestecs; // Utilitzem List (com a l'exemple del professor)
    protected final Queue<Stack<LlibreEmmagatzemat>> muntsLlibres;

    private BiblioManagerImpl() {
        this.lectors = new HashMap<>();
        this.llibresCatalogats = new HashMap<>();
        this.prestecs = new ArrayList<>();
        this.muntsLlibres = new LinkedList<>();
        this.muntsLlibres.add(new Stack<>()); // Munt inicial
    }

    public static BiblioManager getInstance() {
        if (instance == null) instance = new BiblioManagerImpl();
        logger.info("Instància de BiblioManagerImpl retornada.");
        return instance;
    }

    public void clear() {
        logger.warn("TRAÇA WARN: Netejant totes les estructures de dades internes per al test.");
        // Buidar les col·leccions
        this.lectors.clear();
        this.llibresCatalogats.clear();
        this.prestecs.clear();
        this.muntsLlibres.clear();

        // IMPORTANT: Restaurar l'estat inicial de la cua de munts
        // Cal un munt buit inicial per a que la funció emmagatzemarLlibre funcioni correctament
        this.muntsLlibres.add(new Stack<>());
        logger.info("TRAÇA INFO: Estructures netejades i restaurades amb un munt inicial.");
    }

    @Override
    public boolean afegirNouLector(Lector nouLector) {
        logger.info("TRAÇA INFO: Inici afegirNouLector. Paràmetre: " + nouLector);

        boolean isNew = !lectors.containsKey(nouLector.getId());

        if (isNew) {
            lectors.put(nouLector.getId(), nouLector);
            logger.info("TRAÇA INFO: Lector amb ID " + nouLector.getId() + " afegit.");
        } else {
            Lector existingLector = lectors.get(nouLector.getId());

            existingLector.setNom(nouLector.getNom());
            existingLector.setCognoms(nouLector.getCognoms());
            existingLector.setDni(nouLector.getDni());
            existingLector.setDataNaixement(nouLector.getDataNaixement());
            existingLector.setLlocNaixement(nouLector.getLlocNaixement());
            existingLector.setAdreça(nouLector.getAdreça());

            logger.info("TRAÇA INFO: Dades del Lector amb ID " + nouLector.getId() + " actualitzades.");
        }
        logger.info("TRAÇA INFO: Final afegirNouLector. Retornant isNew: " + isNew);
        return isNew;
    }

    @Override
    public void emmagatzemarLlibre(LlibreEmmagatzemat llibre) {
        logger.info("TRAÇA INFO: Inici emmagatzemarLlibre. Paràmetre: " + llibre);

        Stack<LlibreEmmagatzemat> ultimMunt = ((LinkedList<Stack<LlibreEmmagatzemat>>) muntsLlibres).getLast();

        if (ultimMunt.size() >= 10) {
            Stack<LlibreEmmagatzemat> nouMunt = new Stack<>();
            nouMunt.push(llibre);
            muntsLlibres.add(nouMunt);
            logger.info("TRAÇA INFO: Llibre amb ID " + llibre.getId() + " apilat en NOU MUNT. Munts totals: " + muntsLlibres.size());
        } else {
            ultimMunt.push(llibre);
            logger.info("TRAÇA INFO: Llibre amb ID " + llibre.getId() + " apilat a munt existent. Llibres al munt: " + ultimMunt.size());
        }
        logger.info("TRAÇA INFO: Final emmagatzemarLlibre.");
    }

    @Override
    public LlibreCatalogat catalogarLlibre() throws NoHiHaLlibresACatalogarException {
        logger.info("TRAÇA INFO: Inici catalogarLlibre. Sense paràmetres.");

        if (muntsLlibres.isEmpty() || muntsLlibres.peek().isEmpty()) {
            // L'enunciat demana ERROR o FATAL
            logger.fatal("TRAÇA FATAL: No hi ha llibres pendents de catalogar.");
            throw new NoHiHaLlibresACatalogarException();
        }

        Stack<LlibreEmmagatzemat> primerMunt = muntsLlibres.peek();
        LlibreEmmagatzemat llibreAPop = primerMunt.pop();
        LlibreCatalogat llibreCatalogat;
        String isbn = llibreAPop.getISBN();
        boolean nouLlibre = false;

        if (llibresCatalogats.containsKey(isbn)) {
            llibreCatalogat = llibresCatalogats.get(isbn);
            llibreCatalogat.incrementarExemplars();
        } else {
            llibreCatalogat = new LlibreCatalogat(
                    llibreAPop.getISBN(), llibreAPop.getTitol(), llibreAPop.getEditorial(),
                    llibreAPop.getAnyPublicacio(), llibreAPop.getNumEdicio(), llibreAPop.getAutor(), llibreAPop.getTematica()
            );
            llibresCatalogats.put(isbn, llibreCatalogat);
            nouLlibre = true;
        }

        if (primerMunt.isEmpty()) {
            muntsLlibres.remove();
            if(muntsLlibres.isEmpty()) {
                muntsLlibres.add(new Stack<>());
            }
            logger.info("TRAÇA INFO: Munt buit eliminat. Nou munt afegit si la cua és buida.");
        }

        logger.info("TRAÇA INFO: Llibre catalogat retornat: " + llibreCatalogat + ". Nou ISBN: " + nouLlibre);
        logger.info("TRAÇA INFO: Final catalogarLlibre.");
        return llibreCatalogat;
    }

    @Override
    public void prestarLlibre(String idPrestec, String idLector, String isbnLlibre,
                              LocalDate dataPrestec, LocalDate dataFinalDevolucio) throws PrestecException {

        logger.info("TRAÇA INFO: Inici prestarLlibre. Paràmetres: idPrestec=" + idPrestec + ", idLector=" + idLector + ", isbnLlibre=" + isbnLlibre);

        if (!lectors.containsKey(idLector)) {
            logger.error("TRAÇA ERROR: Lector no existeix. ID: " + idLector);
            throw new LectorNoExisteixException(idLector);
        }
        if (!llibresCatalogats.containsKey(isbnLlibre)) {
            logger.error("TRAÇA ERROR: Llibre catalogat no existeix. ISBN: " + isbnLlibre);
            throw new LlibreNoExisteixException(isbnLlibre);
        }

        LlibreCatalogat llibre = llibresCatalogats.get(isbnLlibre);

        if (llibre.getExemplarsDisponibles() <= 0) {
            logger.error("TRAÇA ERROR: Exemplars insuficients. ISBN: " + isbnLlibre);
            throw new ExemplarsInsuficientsException(isbnLlibre, llibre.getExemplarsDisponibles());
        }

        llibre.decrementarExemplars();

        Prestec prestec = new Prestec(idPrestec, idLector, isbnLlibre, dataPrestec, dataFinalDevolucio);
        this.prestecs.add(prestec);

        logger.info("TRAÇA INFO: Préstec " + idPrestec + " realitzat. Exemplars restants de " + isbnLlibre + ": " + llibre.getExemplarsDisponibles());
        logger.info("TRAÇA INFO: Final prestarLlibre.");
    }

    @Override
    public List<Prestec> consultarPrestecsLector(String idLector) {
        logger.info("TRAÇA INFO: Inici consultarPrestecsLector. Paràmetre: idLector=" + idLector);

        if (!lectors.containsKey(idLector)) throw new LectorNoExisteixException(idLector);

        List<Prestec> result = prestecs.stream()
                .filter(p -> p.getIdLector().equals(idLector))
                .collect(Collectors.toList());

        logger.info("TRAÇA INFO: Trobats " + result.size() + " préstecs per a " + idLector + ".");
        logger.info("TRAÇA INFO: Final consultarPrestecsLector.");
        return result;
    }
}