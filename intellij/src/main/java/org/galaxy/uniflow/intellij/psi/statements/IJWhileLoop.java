package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiWhileStatement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.statements.UniWhileLoop;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJWhileLoop extends IJStatement<PsiWhileStatement> implements UniWhileLoop {

    public IJWhileLoop(PsiWhileStatement element) {
        super(element);
    }

    @Override
    public void setCondition(@NotNull UniExpression condition) {
        replace(IntellijUnwrapper.unwrap(condition), element.getBody());
    }

    @Override
    public @NotNull UniExpression getCondition() {
        return UniflowWrapper.wrap(element.getCondition());
    }

    @Override
    public void setBody(@NotNull UniStatement body) {
        replace(element.getCondition(), IntellijUnwrapper.unwrap(body));
    }

    @Override
    public @NotNull UniStatement getBody() {
        return UniflowWrapper.wrap(element.getBody());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.DO_WHILE_LOOP;
    }

    private void replace(PsiExpression condition, PsiStatement body) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiWhileStatement newDoWhile = (PsiWhileStatement) factory.createStatementFromText(
                "while (a) {}", null);

        assert newDoWhile.getCondition() != null;
        assert newDoWhile.getBody() != null;

        newDoWhile.getCondition().replace(condition);
        newDoWhile.getBody().replace(body);

        replace(newDoWhile);
    }
}
