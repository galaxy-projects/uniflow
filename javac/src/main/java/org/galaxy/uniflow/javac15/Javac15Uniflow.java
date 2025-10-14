package org.galaxy.uniflow.javac15;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.javac.VersionedWrapper;
import org.galaxy.uniflow.javac12.Javac12Uniflow;
import org.galaxy.uniflow.javac15.factories.Javac15ElementFactory;
import org.jetbrains.annotations.NotNull;

public class Javac15Uniflow extends Javac12Uniflow {

    public Javac15Uniflow(JavacProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    @Override
    protected @NotNull UniElementFactory createElementFactory() {
        return new Javac15ElementFactory();
    }

    @Override
    public VersionedWrapper getVersionedWrapper() {
        return Uniflow15Wrapper.INSTANCE;
    }
}
