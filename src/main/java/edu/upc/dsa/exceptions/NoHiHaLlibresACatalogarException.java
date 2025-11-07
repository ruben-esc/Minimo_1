package edu.upc.dsa.exceptions;

public class NoHiHaLlibresACatalogarException extends RuntimeException {
    public NoHiHaLlibresACatalogarException() {
        super("ERROR FATAL: No hi ha cap llibre pendent de catalogar en els munts.");
    }
}
