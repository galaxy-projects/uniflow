package org.galaxy.uniflow.javac25;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.javac.VersionedWrapper;
import org.galaxy.uniflow.javac21.Javac21Uniflow;
import org.galaxy.uniflow.javac25.factories.Javac25ElementFactory;
import org.jetbrains.annotations.NotNull;

public class Javac25Uniflow extends Javac21Uniflow {

    public Javac25Uniflow(JavacProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    @Override
    protected @NotNull UniElementFactory createElementFactory() {
        return new Javac25ElementFactory();
    }

    @Override
    public VersionedWrapper getVersionedWrapper() {
        return Uniflow25Wrapper.INSTANCE;
    }
}
