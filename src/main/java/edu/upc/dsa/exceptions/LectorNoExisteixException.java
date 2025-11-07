package edu.upc.dsa.exceptions;

public class LectorNoExisteixException extends PrestecException {
    public LectorNoExisteixException(String idLector) {
        super("El lector amb ID '" + idLector + "' no existeix.");
    }
}
