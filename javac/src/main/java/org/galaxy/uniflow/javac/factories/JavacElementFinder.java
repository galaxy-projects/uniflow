package org.galaxy.uniflow.javac.factories;

import org.galaxy.uniflow.api.factories.UniElementFinder;
import org.galaxy.uniflow.api.factories.UniFieldFinder;
import org.galaxy.uniflow.api.factories.UniMethodFinder;
import org.jetbrains.annotations.NotNull;

public class JavacElementFinder implements UniElementFinder {

    private final UniFieldFinder fieldFinder;
    private final UniMethodFinder methodFinder;

    public JavacElementFinder() {
        fieldFinder = new JavacFieldFinder();
        methodFinder = new JavacMethodFinder();
    }

    @Override
    public @NotNull UniFieldFinder fields() {
        return fieldFinder;
    }

    @Override
    public @NotNull UniMethodFinder methods() {
        return methodFinder;
    }
}
