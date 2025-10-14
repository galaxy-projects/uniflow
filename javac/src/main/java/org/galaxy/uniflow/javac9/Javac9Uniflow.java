package org.galaxy.uniflow.javac9;

import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.VersionedWrapper;
import org.galaxy.uniflow.javac9.factories.Javac9ElementFactory;
import org.jetbrains.annotations.NotNull;

public class Javac9Uniflow extends JavacUniflow {

    public Javac9Uniflow(com.sun.tools.javac.processing.JavacProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    @Override
    protected @NotNull UniElementFactory createElementFactory() {
        return new Javac9ElementFactory();
    }

    @Override
    public VersionedWrapper getVersionedWrapper() {
        return Uniflow9Wrapper.INSTANCE;
    }
}
