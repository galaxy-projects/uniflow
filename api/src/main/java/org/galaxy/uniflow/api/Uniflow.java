package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.api.factories.UniElementFinder;
import org.galaxy.uniflow.api.factories.UniFiler;
import org.galaxy.uniflow.api.factories.UniTypeFactory;
import org.galaxy.uniflow.api.logger.UniBuildLogger;
import org.galaxy.uniflow.api.logger.UniSystemLogger;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class Uniflow {

    private static Uniflow instance = null;

    private final Lazy<UniElementFinder> finder;
    private final Lazy<UniTypeFactory> typeFactory;
    private final Lazy<UniElementFactory> elementFactory;
    private final Lazy<UniFiler> filer;
    private final Lazy<UniBuildLogger> buildLogger;
    private final Lazy<UniSystemLogger> systemLogger;

    protected Uniflow() {
        finder = new Lazy<>(this::createFinder);
        typeFactory = new Lazy<>(this::createTypeFactory);
        elementFactory = new Lazy<>(this::createElementFactory);
        filer = new Lazy<>(this::createFiler);
        buildLogger = new Lazy<>(this::createBuildLogger);
        systemLogger = new Lazy<>(this::createSystemLogger);
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

    public @NotNull UniFiler getFiler() {
        return filer.get();
    }

    public @NotNull UniBuildLogger getBuildLogger() {
        return buildLogger.get();
    }

    public @NotNull UniSystemLogger getSystemLogger() {
        return systemLogger.get();
    }

    protected abstract @NotNull UniElementFinder createFinder();

    protected abstract @NotNull UniTypeFactory createTypeFactory();

    protected abstract @NotNull UniElementFactory createElementFactory();

    protected abstract @NotNull UniFiler createFiler();

    protected abstract @NotNull UniBuildLogger createBuildLogger();

    protected abstract @NotNull UniSystemLogger createSystemLogger();

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
