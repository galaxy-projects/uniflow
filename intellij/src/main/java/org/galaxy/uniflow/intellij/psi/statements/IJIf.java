package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiIfStatement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniIf;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJIf extends IJStatement<PsiIfStatement> implements UniIf {

    public IJIf(PsiIfStatement element) {
        super(element);
    }

    @Override
    public void setCondition(@NotNull UniExpression condition) {
        PsiExpression expression = IntellijUnwrapper.unwrap(condition);

        if (element.getCondition() != null)
            element.getCondition().replace(expression);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiIfStatement newIf = (PsiIfStatement) factory.createStatementFromText("if (condition)", null);

            assert newIf.getCondition() != null;

            newIf.getCondition().replace(expression);
            if (element.getThenBranch() != null)
                newIf.setThenBranch(element.getThenBranch());
            if (element.getElseBranch() != null)
                newIf.setElseBranch(element.getElseBranch());
            replace(newIf);
        }
    }

    @Override
    public @NotNull UniExpression getCondition() {
        return UniflowWrapper.wrap(element.getCondition());
    }

    @Override
    public void setThenStatement(@NotNull UniStatement thenStatement) {
        element.setThenBranch(IntellijUnwrapper.unwrap(thenStatement));
    }

    @Override
    public @NotNull UniStatement getThenStatement() {
        return UniflowWrapper.wrap(element.getThenBranch());
    }

    @Override
    public void setElseStatement(@NotNull UniStatement elseStatement) {
        element.setElseBranch(IntellijUnwrapper.unwrap(elseStatement));
    }

    @Override
    public @NotNull UniStatement getElseStatement() {
        return UniflowWrapper.wrap(element.getElseBranch());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.IF;
    }
}
