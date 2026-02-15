package org.galaxy.uniflow.api.factories;

import org.jetbrains.annotations.NotNull;

public interface UniElementFactoryConversion {

    default boolean supportsJdk9() {
        return this instanceof UniJdk9ElementFactory;
    }

    default @NotNull UniJdk9ElementFactory asJdk9() {
        if (supportsJdk9())
            return (UniJdk9ElementFactory) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    default boolean supportsJdk10() {
        return this instanceof UniJdk10ElementFactory;
    }

    default @NotNull UniJdk10ElementFactory asJdk10() {
        if (supportsJdk10())
            return (UniJdk10ElementFactory) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    default boolean supportsJdk12() {
        return this instanceof UniJdk12ElementFactory;
    }

    default @NotNull UniJdk12ElementFactory asJdk12() {
        if (supportsJdk12())
            return (UniJdk12ElementFactory) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    default boolean supportsJdk15() {
        return this instanceof UniJdk15ElementFactory;
    }

    default @NotNull UniJdk15ElementFactory asJdk15() {
        if (supportsJdk15())
            return (UniJdk15ElementFactory) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    default boolean supportsJdk21() {
        return this instanceof UniJdk21ElementFactory;
    }

    default @NotNull UniJdk21ElementFactory asJdk21() {
        if (supportsJdk21())
            return (UniJdk21ElementFactory) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    default boolean supportsJdk25() {
        return this instanceof UniJdk25ElementFactory;
    }

    default @NotNull UniJdk25ElementFactory asJdk25() {
        if (supportsJdk25())
            return (UniJdk25ElementFactory) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }
}
