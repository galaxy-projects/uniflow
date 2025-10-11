package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiParenthesizedExpression;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniParenthesized;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJParenthesized extends IJExpression<PsiParenthesizedExpression> implements UniParenthesized {

    public IJParenthesized(PsiParenthesizedExpression element) {
        super(element);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        if (element.getExpression() != null)
            element.getExpression().replace(IntellijUnwrapper.unwrap(expression));
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiParenthesizedExpression dummy = (PsiParenthesizedExpression) factory.createExpressionFromText(
                    "(" + element.getText() + ")", null);

            assert dummy.getExpression() != null;
            dummy.getExpression().replace(IntellijUnwrapper.unwrap(expression));
            replace(dummy);
        }
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(element.getExpression());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.PARENTHESIZED;
    }
}
