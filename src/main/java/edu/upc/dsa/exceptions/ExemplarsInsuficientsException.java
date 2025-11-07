package edu.upc.dsa.exceptions;

public class ExemplarsInsuficientsException extends PrestecException {
    public ExemplarsInsuficientsException(String isbnLlibre, int disponibles) {
        super("No existeixen exemplars suficients (Disponibles: " + disponibles + ") del llibre amb ISBN '" + isbnLlibre + "'.");
    }
}
