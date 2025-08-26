package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.jetbrains.annotations.NotNull;

public interface UniErroneous extends UniAnnotationValue, UniExpression {

    @NotNull UniList<UniElement> getErrors();

}
