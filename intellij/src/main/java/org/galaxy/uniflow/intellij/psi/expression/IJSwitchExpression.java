package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiSwitchExpression;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniSwitchExpression;
import org.galaxy.uniflow.api.statements.UniJdk21Case;
import org.galaxy.uniflow.intellij.psi.lists.IJCaseList;
import org.galaxy.uniflow.intellij.psi.statements.IJSwitchStatement;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJSwitchExpression extends IJExpression<PsiSwitchExpression> implements UniSwitchExpression {

    public IJSwitchExpression(PsiSwitchExpression element) {
        super(element);
    }

    @Override
    public void setSelector(@NotNull UniExpression selector) {
        IJSwitchStatement.replaceSelector(element, selector, this::replace);
    }

    @Override
    public @NotNull UniExpression getSelector() {
        return UniflowWrapper.wrap(element.getExpression());
    }

    @Override
    public @NotNull UniList<@NotNull UniJdk21Case> getCases() {
        return new IJCaseList(element);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.SWITCH_EXPRESSION;
    }
}
