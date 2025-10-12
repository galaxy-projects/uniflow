package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiThrowStatement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniThrow;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJThrow extends IJStatement<PsiThrowStatement> implements UniThrow {

    public IJThrow(PsiThrowStatement element) {
        super(element);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        PsiExpression exception = IntellijUnwrapper.unwrap(expression);

        if (element.getException() != null)
            element.getException().replace(exception);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiThrowStatement newThrow = (PsiThrowStatement) factory.createStatementFromText("throw e;", null);

            assert newThrow.getException() != null;

            newThrow.getException().replace(exception);

            replace(newThrow);
        }
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(element.getException());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.THROW;
    }
}
