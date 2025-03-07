package com.example.imageValidations.imps;

import com.example.imageValidations.interfaces.FileValidator;

public class Jpeg implements FileValidator {
    @Override
    public boolean isValid( String extension ) {
        return extension.equals("jpeg");
    }
}
