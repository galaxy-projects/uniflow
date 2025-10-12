package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiReturnStatement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniReturn;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJReturn extends IJStatement<PsiReturnStatement> implements UniReturn {

    public IJReturn(PsiReturnStatement element) {
        super(element);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        PsiExpression returnValue = IntellijUnwrapper.unwrap(expression);

        if (element.getReturnValue() != null)
            element.getReturnValue().replace(returnValue);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiReturnStatement newReturn = (PsiReturnStatement) factory.createStatementFromText("return a;", null);

            assert newReturn.getReturnValue() != null;
            newReturn.getReturnValue().replace(returnValue);

            replace(newReturn);
        }
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(element.getReturnValue());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.RETURN;
    }
}
