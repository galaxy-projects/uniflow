package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiInstanceOfExpression;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniInstanceOf;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJInstanceOf extends IJExpression<PsiInstanceOfExpression> implements UniInstanceOf {

    public IJInstanceOf(PsiInstanceOfExpression element) {
        super(element);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        element.getOperand().replace(IntellijUnwrapper.unwrap(expression));
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(element.getOperand());
    }

    @Override
    public @NotNull UniElement getType() {
        return UniflowWrapper.wrapAsType(element.getCheckType());
    }

    @Override
    public void setPattern(@Nullable UniPattern pattern) {
        if (element.getPattern() != null)
            element.getPattern().replace(IntellijUnwrapper.unwrap(pattern));
        else {
            if (element.getCheckType() == null)
                throw new IllegalStateException("Check type is null");
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiInstanceOfExpression dummy = (PsiInstanceOfExpression) factory.createExpressionFromText(
                    "a instanceof String a", null);

            assert dummy.getPattern() != null;
            assert dummy.getCheckType() != null;
            dummy.getOperand().replace(element.getOperand());
            dummy.getCheckType().replace(element.getCheckType());
            dummy.getPattern().replace(IntellijUnwrapper.unwrap(pattern));

            replace(dummy);
        }
    }

    @Override
    public @Nullable UniPattern getPattern() {
        return UniflowWrapper.wrap(element.getPattern());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.INSTANCE_OF;
    }
}
