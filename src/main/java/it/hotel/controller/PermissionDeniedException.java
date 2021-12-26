package it.hotel.controller;

public class PermissionDeniedException extends Exception {

    public PermissionDeniedException() {}

    public PermissionDeniedException(String msg) { super(msg); }

}
