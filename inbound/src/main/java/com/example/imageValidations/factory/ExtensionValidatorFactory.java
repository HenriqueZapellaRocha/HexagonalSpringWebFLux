package com.example.imageValidations.factory;

import com.example.imageValidations.factory.ExtensionsEnum;
import com.example.imageValidations.imps.Jpeg;
import com.example.imageValidations.imps.Jpg;
import com.example.imageValidations.imps.Png;
import com.example.imageValidations.interfaces.FileValidator;

public class ExtensionValidatorFactory {

    public static FileValidator factory(ExtensionsEnum extension ) {

        switch ( extension ) {

            case JPEG -> {
                return new Jpeg();
            }
            case JPG -> {
                return new Jpg();
            }
            case PNG -> {
                return new Png();
            }
            default -> throw new RuntimeException( "This strategie is not recognized" );
        }
    }
}
