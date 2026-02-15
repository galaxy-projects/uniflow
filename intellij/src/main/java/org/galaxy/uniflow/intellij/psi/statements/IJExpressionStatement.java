package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiExpressionStatement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniExpressionStatement;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJExpressionStatement extends IJStatement<PsiExpressionStatement> implements UniExpressionStatement {

    public IJExpressionStatement(PsiExpressionStatement element) {
        super(element);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        element.getExpression().replace(IntellijUnwrapper.unwrap(expression));
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(element.getExpression());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.EXPRESSION_STATEMENT;
    }
}
