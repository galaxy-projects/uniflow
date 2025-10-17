package org.galaxy.uniflow.javac21;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.javac.VersionedWrapper;
import org.galaxy.uniflow.javac15.Javac15Uniflow;
import org.galaxy.uniflow.javac21.factories.Javac21ElementFactory;
import org.jetbrains.annotations.NotNull;

public class Javac21Uniflow extends Javac15Uniflow {

    public Javac21Uniflow(JavacProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    @Override
    protected @NotNull UniElementFactory createElementFactory() {
        return new Javac21ElementFactory();
    }

    @Override
    public VersionedWrapper getVersionedWrapper() {
        return Uniflow21Wrapper.INSTANCE;
    }
}
