package org.galaxy.uniflow.api;

import org.jetbrains.annotations.NotNull;

public interface UniImport extends UniElement {

    boolean isGroup();

    boolean isStatic();

    @NotNull UniElement getQualifiedElement();

}
