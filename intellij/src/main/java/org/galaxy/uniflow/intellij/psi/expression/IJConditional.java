package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import org.galaxy.uniflow.api.expressions.UniConditional;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJConditional extends IJExpression<PsiConditionalExpression> implements UniConditional {

    public IJConditional(PsiConditionalExpression element) {
        super(element);
    }

    @Override
    public void setCondition(@NotNull UniExpression condition) {
        element.getCondition().replace(IntellijUnwrapper.unwrap(condition));
    }

    @Override
    public @NotNull UniExpression getCondition() {
        return UniflowWrapper.wrap(element.getCondition());
    }

    @Override
    public void setTrueExpression(@NotNull UniExpression expression) {
        if (element.getThenExpression() != null)
            element.getThenExpression().replace(IntellijUnwrapper.unwrap(expression));
        else
            replace(IntellijUnwrapper.unwrap(expression), element.getElseExpression());
    }

    @Override
    public @NotNull UniExpression getTrueExpression() {
        return UniflowWrapper.wrap(element.getThenExpression());
    }

    @Override
    public void setFalseExpression(@NotNull UniExpression expression) {
        if (element.getElseExpression() != null)
            element.getElseExpression().replace(IntellijUnwrapper.unwrap(expression));
        else
            replace(element.getThenExpression(), IntellijUnwrapper.unwrap(expression));
    }

    @Override
    public @NotNull UniExpression getFalseExpression() {
        return UniflowWrapper.wrap(element.getElseExpression());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.CONDITIONAL_EXPRESSION;
    }

    private void replace(PsiExpression thenPart, PsiExpression elsePart) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiConditionalExpression dummy = (PsiConditionalExpression) factory.createTypeElementFromText(
                "a ? b : c", null);

        assert dummy.getThenExpression() != null;
        assert dummy.getElseExpression() != null;
        dummy.getCondition().replace(element.getCondition());
        dummy.getThenExpression().replace(thenPart);
        dummy.getElseExpression().replace(elsePart);
        replace(dummy);
    }
}
