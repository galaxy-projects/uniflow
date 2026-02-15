package org.galaxy.uniflow.api.elements.imports;

import org.galaxy.uniflow.api.UniElement;

public interface UniImportBase extends UniElement {

    default boolean isStatic() {
        return this instanceof UniStaticImport;
    }
}
