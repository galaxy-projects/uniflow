package org.galaxy.uniflow.javac15;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.javac.VersionedWrapper;
import org.galaxy.uniflow.javac10.Javac10Uniflow;
import org.galaxy.uniflow.javac12.Uniflow12Wrapper;
import org.galaxy.uniflow.javac12.factories.Javac12ElementFactory;
import org.jetbrains.annotations.NotNull;

public class Javac12Uniflow extends Javac10Uniflow {

    public Javac12Uniflow(JavacProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    @Override
    protected @NotNull UniElementFactory createElementFactory() {
        return new Javac12ElementFactory();
    }

    @Override
    public VersionedWrapper getVersionedWrapper() {
        return Uniflow12Wrapper.INSTANCE;
    }
}
