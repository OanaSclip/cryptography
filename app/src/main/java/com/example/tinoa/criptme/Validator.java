package com.example.tinoa.criptme;


public class Validator {
    public Boolean plainTextValidation(String text) {
        return text.matches("[a-z ]*");

    }

    public Boolean cipherTextValidation(String text) {
        return text.matches("[A-Z ]*");
    }
}
