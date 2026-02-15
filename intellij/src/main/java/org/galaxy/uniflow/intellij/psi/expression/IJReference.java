package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiReference;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public record IJReference(PsiReference reference) implements UniExpression {

    @Override
    public int getPosition() {
        return -1;
    }

    @Override
    public @NotNull Kind getKind() {
        throw new UnsupportedOperationException();
    }
}
