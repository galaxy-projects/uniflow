package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiLambdaExpression;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.expressions.UniLambda;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.lists.IJParameterList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJLambda extends IJExpression<PsiLambdaExpression> implements UniLambda {

    public IJLambda(PsiLambdaExpression element) {
        super(element);
    }

    @Override
    public @NotNull UniParameterList getParameters() {
        return new IJParameterList(element.getParameterList());
    }

    @Override
    public void setBody(@NotNull UniElement body) {
        if (element.getBody() != null)
            element.getBody().replace(IntellijUnwrapper.unwrap(body));
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiLambdaExpression dummy = (PsiLambdaExpression) factory.createIdentifier("() -> {}");

            assert dummy.getBody() != null;
            dummy.getParameterList().replace(element.getParameterList());
            dummy.getBody().replace(IntellijUnwrapper.unwrap(body));
            replace(dummy);
        }
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
