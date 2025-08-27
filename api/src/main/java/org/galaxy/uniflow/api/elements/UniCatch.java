package org.galaxy.uniflow.api.elements;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;

public interface UniCatch extends UniElement {

    void setParameter(@NotNull UniVariable parameter);

    @NotNull UniVariable getParameter();

    void setBody(@NotNull UniBlock body);

    @NotNull UniBlock getBody();

}
