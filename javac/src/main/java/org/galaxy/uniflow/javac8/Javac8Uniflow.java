package org.galaxy.uniflow.javac8;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.VersionedWrapper;
import org.galaxy.uniflow.javac8.factories.Javac8ElementFactory;
import org.jetbrains.annotations.NotNull;

public class Javac8Uniflow extends JavacUniflow {

    public Javac8Uniflow(JavacProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    @Override
    protected @NotNull UniElementFactory createElementFactory() {
        return new Javac8ElementFactory();
    }

    @Override
    public VersionedWrapper getVersionedWrapper() {
        return Uniflow8Wrapper.INSTANCE;
    }
}
