package org.galaxy.uniflow.javac.factories;

import org.galaxy.uniflow.api.factories.UniElementFinder;
import org.galaxy.uniflow.api.factories.UniFieldFinder;
import org.galaxy.uniflow.api.factories.UniMethodFinder;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacElementFinder implements UniElementFinder {

    private final UniFieldFinder fieldFinder;
    private final UniMethodFinder methodFinder;

    public JavacElementFinder() {
        fieldFinder = new JavacFieldFinder(this);
        methodFinder = new JavacMethodFinder(this);
    }

    @Override
    public @NotNull UniClassType findClass(@NotNull Class<?> clazz) {
        return findClass(clazz.getSimpleName());
    }

    @Override
    public @NotNull UniClassType findClass(@NotNull String name) {
        return (UniClassType) UniflowWrapper.type(JavacUniflow.getInstance().elements.getTypeElement(name));
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
