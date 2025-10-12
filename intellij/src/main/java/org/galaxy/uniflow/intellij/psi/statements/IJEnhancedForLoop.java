package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiForeachStatement;
import com.intellij.psi.PsiStatement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniEnhancedForLoop;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJEnhancedForLoop extends IJStatement<PsiForeachStatement> implements UniEnhancedForLoop {

    public IJEnhancedForLoop(PsiForeachStatement element) {
        super(element);
    }

    // TODO: change to parameter
    @Override
    public void setVariable(@NotNull UniVariable variable) {
        element.getIterationParameter().replace(IntellijUnwrapper.unwrap(variable));
    }

    @Override
    public @NotNull UniVariable getVariable() {
        return UniflowWrapper.wrap(element.getIterationParameter());
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        replace(IntellijUnwrapper.unwrap(expression), element.getBody());
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(element.getIteratedValue());
    }

    @Override
    public void setBody(@NotNull UniStatement body) {
        replace(element.getIteratedValue(), IntellijUnwrapper.unwrap(body));
    }

    @Override
    public @NotNull UniStatement getBody() {
        return UniflowWrapper.wrap(element.getBody());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.ENHANCED_FOR_LOOP;
    }

    private void replace(PsiExpression iteratedValue, PsiStatement body) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiForeachStatement newForEach = (PsiForeachStatement) factory.createStatementFromText("for (String a : b)",
                null);

        assert newForEach.getIteratedValue() != null;
        assert newForEach.getBody() != null;

        newForEach.getIterationParameter().replace(element.getIterationParameter());
        newForEach.getIteratedValue().replace(iteratedValue);
        newForEach.getBody().replace(body);

        replace(newForEach);
    }
}
