package org.galaxy.uniflow.api.elements;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;

public interface UniCatch extends UniElement {

    @NotNull UniVariable getParameter();

    @NotNull UniBlock getBody();

}
