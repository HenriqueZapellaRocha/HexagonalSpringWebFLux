package com.example.imageValidations.imps;

import com.example.imageValidations.interfaces.FileValidator;

public class Jpg implements FileValidator {

    @Override
    public boolean isValid( String extension ) {
        return extension.equals("jpg");
    }
}
