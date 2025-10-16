package org.galaxy.uniflow.api.statements;

public interface UniCaseBase extends UniStatement {

    default boolean isJdk8() {
        return this instanceof UniJdk8Case;
    }

    default boolean isJdk12() {
        return this instanceof UniJdk12Case;
    }
}
