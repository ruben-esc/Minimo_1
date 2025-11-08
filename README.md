# Lliurament part I i part II a les 11h. Cal retocar la REST API, només funciona un POST (/biblioteca/lectors), la resta donen error 500

# Solucionat error 500/serialització i finalització Part II

Solucionats els problemes crítics que impedien el funcionament de l'API:

1.  Robustesa del Model: S'afegeixen les anotacions @XmlElement als camps de les classes que utilitzen herència (Llibre, LlibreEmmagatzemat, LlibreCatalogat) i a la classe Prestec per solucionar errors de deserialització (400/500) i garantir el mapeig correcte de camps entre JSON i Java.

2.  Disseny REST: Es refactoritza el flux de préstec:
    El servidor genera automàticament l'ID del préstec i les dates de la transacció.
    S'utilitza el DTO PrestecRequest per a una API més neta.

3.  Sincronització: S'actualitzen tots els tests JUnit i la Interfície (BiblioManager) per utilitzar la nova signatura de préstec simplificada.

# Captura de Pantalla del Servei REST

4. GET /biblioteca/prestecs/{idLector} (Consulta)
   S'adjunta la captura de pantalla que mostra la resposta exitosa del servidor a la petició GET /biblioteca/prestecs/{idLector}, amb un codi 200 OK.

   Nota:Per obtenir aquest Response Body amb les dades del préstec, s'ha verificat la correcta execució i persistència de les dades a través de les següents operacions POST consecutives:
    * POST /lectors: Creació del Lector (amb "idLector: 1").
    * POST /llibres/emmagatzemar:Inserció del llibre al munt.
    * POST /llibres/catalogar: Catalogació del llibre i increment d'exemplars.
    * POST /prestecs: Realització d'un préstec a favor del "idLector: 1".
