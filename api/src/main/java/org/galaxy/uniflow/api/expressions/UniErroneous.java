package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniErroneous extends UniExpression {

    @NotNull UniList<UniElement> getErrors();

}
