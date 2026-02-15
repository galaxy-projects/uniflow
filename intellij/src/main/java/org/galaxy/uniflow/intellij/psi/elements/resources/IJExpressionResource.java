package org.galaxy.uniflow.intellij.psi.elements.resources;

import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiResourceListElement;
import org.galaxy.uniflow.api.elements.resources.UniExpressionResource;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.NotNull;

public class IJExpressionResource extends IJResource<UniExpression> implements UniExpressionResource {

    private UniExpression expression;

    public IJExpressionResource(UniExpression expression) {
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
    public PsiResourceListElement getResourceElement() {
        PsiExpression unwrapped = IntellijUnwrapper.unwrap(expression);

        return IntellijUniflow.getInstance().factory.createResourceFromText(unwrapped.getText(), null);
    }
}
