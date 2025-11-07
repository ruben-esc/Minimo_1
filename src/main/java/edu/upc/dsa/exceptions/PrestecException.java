package edu.upc.dsa.exceptions;

public class PrestecException extends RuntimeException {
    public PrestecException(String missatge) {
        super("ERROR DE PRÉSTEC: " + missatge);
    }
}
