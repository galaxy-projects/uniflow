package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiLambdaExpression;
import com.intellij.psi.PsiParameterList;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniLambda;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJLambda extends IJExpression<PsiLambdaExpression> implements UniLambda {

    public IJLambda(PsiLambdaExpression element) {
        super(element);
    }

    @Override
    public @NotNull UniList<@NotNull UniVariable> getParameters() {
        PsiParameterList parameterList = element.getParameterList();

        // TODO
        return null;
    }

    @Override
    public @NotNull UniElement getBody() {
        return UniflowWrapper.wrap(element.getBody());
    }

    @Override
    public @NotNull BodyKind getBodyKind() {
        if (element.getBody() instanceof PsiExpression)
            return BodyKind.EXPRESSION;
        return BodyKind.STATEMENT;
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.LAMBDA_EXPRESSION;
    }
}
