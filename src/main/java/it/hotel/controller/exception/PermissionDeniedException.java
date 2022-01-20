package it.hotel.controller.exception;

public class PermissionDeniedException extends Exception {

    public PermissionDeniedException() {}

    public PermissionDeniedException(String msg) { super(msg); }

}
