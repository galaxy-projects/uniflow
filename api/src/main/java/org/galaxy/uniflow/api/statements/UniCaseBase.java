package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.factories.UniConstants;
import org.jetbrains.annotations.NotNull;

public interface UniCaseBase extends UniStatement {

    default boolean isJdk8() {
        return this instanceof UniJdk8Case;
    }

    default UniJdk8Case asJdk8() {
        if (isJdk8())
            return (UniJdk8Case) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    default boolean isJdk12() {
        return this instanceof UniJdk12Case;
    }

    default @NotNull UniJdk12Case asJdk12() {
        if (isJdk12())
            return (UniJdk12Case) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    default boolean isJdk21() {
        return this instanceof UniJdk21Case;
    }

    default UniJdk21Case asJdk21() {
        if (isJdk21())
            return (UniJdk21Case) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }
}
