package org.galaxy.uniflow.javac10;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.javac10.factories.Javac10ElementFactory;
import org.galaxy.uniflow.javac9.Javac9Uniflow;
import org.jetbrains.annotations.NotNull;

public class Javac10Uniflow extends Javac9Uniflow {

    public Javac10Uniflow(JavacProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    @Override
    protected @NotNull UniElementFactory createElementFactory() {
        return new Javac10ElementFactory();
    }
}
