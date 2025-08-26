package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.api.factories.UniElementFinder;
import org.galaxy.uniflow.api.factories.UniModuleFactory;
import org.galaxy.uniflow.api.factories.UniTypeFactory;

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

    public UniElementFinder getFinder() {
        return finder.get();
    }

    public UniTypeFactory getTypeFactory() {
        return typeFactory.get();
    }

    public UniElementFactory getElementFactory() {
        return elementFactory.get();
    }

    public UniModuleFactory getModuleFactory() {
        return moduleFactory.get();
    }

    protected abstract UniElementFinder createFinder();

    protected abstract UniTypeFactory createTypeFactory();

    protected abstract UniElementFactory createElementFactory();

    protected abstract UniModuleFactory createModuleFactory();

    public static Uniflow getInstance() {
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
