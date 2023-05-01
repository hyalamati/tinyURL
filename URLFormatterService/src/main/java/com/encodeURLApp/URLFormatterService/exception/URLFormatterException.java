package com.encodeURLApp.URLFormatterService.exception;

public class URLFormatterException extends Exception {

    private static final long serialVersionUID = 1L ;
    public URLFormatterException(String message) {
        super(message);
    }
    public static String urlDataNotFoundException(){
        return "No URL Exists, Please enter valid info";
    }
    public static String urlAlreadyExistsException() {
        return "This shortURL already exists, try again choosing a different shortURL..";
    }
    public static String invalidURLFormatException() {
        return "Please Enter valid URL";
    }
}
