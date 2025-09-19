package org.galaxy.uniflow.api;

import org.jetbrains.annotations.NotNull;

public interface UniImport extends UniElement {

    boolean isGroup();

    boolean isStatic();

    void setQualifiedElement(@NotNull UniElement qualifiedElement);

    @NotNull UniElement getQualifiedElement();

}
