package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.factories.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.RoundEnvironment;
import java.util.function.Supplier;

public abstract class Uniflow {

    private static Uniflow instance = null;

    private final Lazy<UniElementFinder> finder;
    private final Lazy<UniTypeFactory> typeFactory;
    private final Lazy<UniElementFactory> elementFactory;
    private final Lazy<UniModuleFactory> moduleFactory;

    protected Uniflow() {
        if (instance != null)
            throw new IllegalStateException("Uniflow instance has already been created");
        finder = new Lazy<>(this::createFinder);
        typeFactory = new Lazy<>(this::createTypeFactory);
        elementFactory = new Lazy<>(this::createElementFactory);
        moduleFactory = new Lazy<>(this::createModuleFactory);
        instance = this;
    }

    public @NotNull UniElementFinder getFinder() {
        return finder.get();
    }

    public @NotNull UniTypeFactory getTypeFactory() {
        return typeFactory.get();
    }

    public @NotNull UniElementFactory getElementFactory() {
        return elementFactory.get();
    }

    public @NotNull UniModuleFactory getModuleFactory() {
        return moduleFactory.get();
    }

    public abstract @NotNull UniEnvironment createEnvironment(@NotNull RoundEnvironment roundEnv);

    protected abstract @NotNull UniElementFinder createFinder();

    protected abstract @NotNull UniTypeFactory createTypeFactory();

    protected abstract @NotNull UniElementFactory createElementFactory();

    protected abstract @NotNull UniModuleFactory createModuleFactory();

    public static @NotNull Uniflow getInstance() {
        if (instance == null)
            throw new IllegalStateException("Uniflow instance has not been created");
        return instance;
    }

    static class Lazy<T> {

        private T value;
        private final Supplier<T> supplier;

        public Lazy(Supplier<T> supplier) {
            this.value = null;
            this.supplier = supplier;
        }

        public T get() {
            if (value == null)
                value = supplier.get();
            return value;
        }
    }
}
