package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiTypeCastExpression;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniTypeCast;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJTypeCast extends IJExpression<PsiTypeCastExpression> implements UniTypeCast {

    public IJTypeCast(PsiTypeCastExpression element) {
        super(element);
    }

    @Override
    public void setType(@NotNull UniElement type) {
        replace(IntellijUnwrapper.unwrap(type), element.getOperand());
    }

    @Override
    public @NotNull UniElement getType() {
        return UniflowWrapper.wrap(element.getCastType());
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        replace(element.getCastType(), IntellijUnwrapper.unwrap(expression));
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(element.getOperand());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.TYPE_CAST;
    }

    private void replace(PsiElement castType, PsiExpression operand) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiTypeCastExpression dummy = (PsiTypeCastExpression) factory.createExpressionFromText("(String) a",
                null);

        assert dummy.getCastType() != null;
        assert dummy.getOperand() != null;
        dummy.getCastType().replace(castType);
        dummy.getOperand().replace(operand);
        replace(dummy);
    }
}
