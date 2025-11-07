package edu.upc.dsa;

import edu.upc.dsa.exceptions.NoHiHaLlibresACatalogarException;
import edu.upc.dsa.exceptions.LectorNoExisteixException;
import edu.upc.dsa.exceptions.ExemplarsInsuficientsException;

import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.LlibreEmmagatzemat;
import edu.upc.dsa.models.LlibreCatalogat;
import edu.upc.dsa.models.Prestec;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.time.LocalDate;

public class BiblioManagerTest {

    BiblioManager bm;

    // Dades de prova
    private final LocalDate DATA_NAIXEMENT = LocalDate.of(1990, 1, 1);
    private final LocalDate DATA_PRESTEC = LocalDate.now();
    private final LocalDate DATA_DEV = DATA_PRESTEC.plusWeeks(3);

    // Dades inicials
    private Lector lectorInicial;

    @Before
    public void setUp() {
        this.bm = BiblioManagerImpl.getInstance();

        ((BiblioManagerImpl)this.bm).clear();

        // 2. Inicialitzar dades: Afegir un lector
        lectorInicial = new Lector("L1", "Test", "User", "12345678Z", DATA_NAIXEMENT, "BCN", "Adreça Test");
        this.bm.afegirNouLector(lectorInicial);

        // 3. Inicialitzar dades: Emmagatzemar 3 llibres per catalogar (mateix ISBN)
        this.bm.emmagatzemarLlibre(new LlibreEmmagatzemat("E1", "978-A-001", "Programació 101", "Edit A", 2020, 1, "Autor X", "Tech"));
        this.bm.emmagatzemarLlibre(new LlibreEmmagatzemat("E2", "978-A-001", "Programació 101", "Edit A", 2020, 1, "Autor X", "Tech"));
        this.bm.emmagatzemarLlibre(new LlibreEmmagatzemat("E3", "978-B-002", "Disseny Patrons", "Edit B", 2022, 1, "Autor Y", "Tech"));
    }

    @After
    public void tearDown() {
        ((BiblioManagerImpl)this.bm).clear();
    }

    // --- TESTS DE LLIBRES I CATALOGACIÓ ---

    @Test
    public void emmagatzemarICatalogarTest() throws Exception {
        // En setUp, hem afegit 3 llibres (2 al Munt 1, 1 al Munt 2 - si fos de 2)
        // O bé, els 3 al Munt 1 (si el límit de 10 no s'ha arribat)

        // 1. Catalogar 1r llibre (E3: Disseny Patrons)
        LlibreCatalogat lc1 = this.bm.catalogarLlibre();
        Assert.assertEquals("978-B-002", lc1.getISBN());
        Assert.assertEquals(1, lc1.getExemplarsDisponibles());

        // 2. Catalogar 2n llibre (E2: Programació 101)
        LlibreCatalogat lc2 = this.bm.catalogarLlibre();
        Assert.assertEquals("978-A-001", lc2.getISBN());
        Assert.assertEquals(1, lc2.getExemplarsDisponibles()); // 1r exemplar d'aquest ISBN

        // 3. Catalogar 3r llibre (E1: Programació 101)
        LlibreCatalogat lc3 = this.bm.catalogarLlibre();
        Assert.assertEquals("978-A-001", lc3.getISBN());
        Assert.assertEquals(2, lc3.getExemplarsDisponibles()); // Ha d'incrementar-se
    }

    @Test(expected = NoHiHaLlibresACatalogarException.class)
    public void catalogarSenseLlibresTest() throws Exception {
        // Cataloguem els 3 llibres inicials
        this.bm.catalogarLlibre();
        this.bm.catalogarLlibre();
        this.bm.catalogarLlibre();

        // 4t intent ha de fallar
        this.bm.catalogarLlibre();
    }

    // --- TESTS DE LECTOR ---

    @Test
    public void afegirIActualitzarLectorTest() {
        // 1. Comprovar lector inicial (afegit a setUp)
        Assert.assertEquals(false, this.bm.afegirNouLector(lectorInicial)); // No és nou

        // 2. Afegir nou lector (hauria de ser nou)
        Lector l2 = new Lector("L2", "Nova", "Usuaria", "00000000Y", DATA_NAIXEMENT, "Tarragona", "Nova Adreça");
        Assert.assertEquals(true, this.bm.afegirNouLector(l2));

        // 3. Actualitzar dades del lector inicial
        Lector l1_update = new Lector("L1", "Test Updated", "User Updated", "12345678Z_MOD", DATA_NAIXEMENT, "BCN", "Adreça Modificada");
        this.bm.afegirNouLector(l1_update); // S'actualitza
    }

    // --- TESTS DE PRÉSTEC ---

    @Test
    public void prestarLlibreExitosTest() throws Exception {

        // El setUp ha afegit E1, E2 (ISBN 978-A-001) i E3 (ISBN 978-B-002) al munt.

        // 1. Catalogar TOTS els llibres (Això garanteix que '978-A-001' existeix al Map)
        // El primer en sortir és E3 (978-B-002)
        this.bm.catalogarLlibre();
        // El segon en sortir és E2 (978-A-001) -> Exemplars: 1
        this.bm.catalogarLlibre();
        // El tercer en sortir és E1 (978-A-001) -> Exemplars: 2
        this.bm.catalogarLlibre();

        // Ara '978-A-001' té 2 exemplars disponibles

        // 2. Préstec exitós
        this.bm.prestarLlibre("P1", lectorInicial.getId(), "978-A-001", DATA_PRESTEC, DATA_DEV);

        // 3. Comprovar consulta de préstecs
        List<Prestec> prestecs = this.bm.consultarPrestecsLector(lectorInicial.getId());
        Assert.assertEquals(1, prestecs.size());
        Assert.assertEquals("P1", prestecs.get(0).getIdPrestec());
    }

    @Test(expected = LectorNoExisteixException.class)
    public void prestarLectorInexistentTest() throws Exception {
        this.bm.prestarLlibre("P2", "L_NO_EXISTEIX", "978-A-001", DATA_PRESTEC, DATA_DEV);
    }

    @Test(expected = ExemplarsInsuficientsException.class)
    public void prestarSenseExemplarsTest() throws Exception {
        // PREPARACIÓ: Catalogar 1 exemplar i prestar-lo per deixar-lo a 0
        this.bm.catalogarLlibre(); // Exemplars: 1 (del 978-B-002)
        this.bm.prestarLlibre("P3", lectorInicial.getId(), "978-B-002", DATA_PRESTEC, DATA_DEV); // Exemplars restants: 0

        // 2n Préstec ha de fallar
        this.bm.prestarLlibre("P4", lectorInicial.getId(), "978-B-002", DATA_PRESTEC, DATA_DEV);
    }
}
