package org.galaxy.uniflow.javac.elements.resources;

import org.galaxy.uniflow.api.elements.resources.UniExpressionResource;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public class JavacExpressionResource extends JavacResource<UniExpression> implements UniExpressionResource {

    private UniExpression expression;

    public JavacExpressionResource(UniExpression expression) {
        this.expression = expression;
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        this.expression = expression;
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return expression;
    }

    @Override
    public UniExpression getElement() {
        return expression;
    }
}
