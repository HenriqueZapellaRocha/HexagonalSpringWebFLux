package com.example.imageValidations.imps;

import com.example.imageValidations.interfaces.FileValidator;

public class Png implements FileValidator {

    @Override
    public boolean isValid(String extension) {
        return extension.equals("png");
    }
}
