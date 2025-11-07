package edu.upc.dsa.exceptions;

public class LlibreNoExisteixException extends PrestecException {
    public LlibreNoExisteixException(String isbnLlibre) {
        super("El llibre catalogat amb ISBN '" + isbnLlibre + "' no existeix.");
    }
}
