package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiYieldStatement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniYield;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJYield extends IJStatement<PsiYieldStatement> implements UniYield {

    public IJYield(PsiYieldStatement element) {
        super(element);
    }

    @Override
    public void setValue(@NotNull UniExpression value) {
        PsiExpression expression = IntellijUnwrapper.unwrap(value);

        if (element.getExpression() != null)
            element.getExpression().replace(expression);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiYieldStatement newYield = (PsiYieldStatement) factory.createStatementFromText("yield a;", null);

            assert newYield.getExpression() != null;

            newYield.getExpression().replace(expression);

            replace(newYield);
        }
    }

    @Override
    public @NotNull UniExpression getValue() {
        return UniflowWrapper.wrap(element.getExpression());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.YIELD;
    }
}
