package org.galaxy.uniflow.intellij.psi.elements.labels;

import com.intellij.psi.PsiCaseLabelElement;
import com.intellij.psi.PsiExpression;
import org.galaxy.uniflow.api.elements.labels.UniConstantCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJConstantCaseLabel extends IJCaseLabelBase<PsiCaseLabelElement> implements UniConstantCaseLabel {

    private PsiExpression expression;

    public IJConstantCaseLabel(PsiCaseLabelElement element, PsiExpression expression) {
        super(element);
        this.expression = expression;
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        PsiExpression psiExpression = IntellijUnwrapper.unwrap(expression);

        if (this.expression != null)
            this.expression.delete();
        element.replace(psiExpression);
        this.expression = psiExpression;
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(expression);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.CASE;
    }
}
