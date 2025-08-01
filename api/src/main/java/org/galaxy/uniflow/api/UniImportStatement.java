package org.galaxy.uniflow.api;

import org.jetbrains.annotations.NotNull;

public interface UniImportStatement extends UniElement {

    boolean isGroup();

    boolean isStatic();

    @NotNull UniElement getQualifiedElement();

}
