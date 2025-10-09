package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiMethodCallExpression;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniMethodInvocation;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.lists.IJExpressionList;
import org.galaxy.uniflow.intellij.psi.lists.IJTypeList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJMethodInvocation extends IJExpression<PsiMethodCallExpression> implements UniMethodInvocation {

    public IJMethodInvocation(PsiMethodCallExpression element) {
        super(element);
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getTypeArguments() {
        return new IJTypeList(element.getTypeArgumentList());
    }

    @Override
    public void setMethodSelect(@NotNull UniExpression methodSelect) {
        element.getMethodExpression().replace(IntellijUnwrapper.unwrap(methodSelect));
    }

    @Override
    public @NotNull UniExpression getMethodSelect() {
        return UniflowWrapper.wrap(element.getMethodExpression());
    }

    @Override
    public @NotNull UniList<UniExpression> getArguments() {
        return new IJExpressionList(element.getArgumentList());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.METHOD_INVOCATION;
    }
}
