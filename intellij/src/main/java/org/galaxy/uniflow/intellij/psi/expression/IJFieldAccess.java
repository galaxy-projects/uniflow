package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiReferenceExpression;
import org.galaxy.uniflow.api.expressions.UniFieldAccess;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IJFieldAccess extends IJExpression<PsiReferenceExpression> implements UniFieldAccess {

    public IJFieldAccess(PsiReferenceExpression element) {
        super(element);
    }

    @Override
    public @NotNull UniType getSelected() {
        return UniflowWrapper.wrapAsType(element.getQualifierExpression());
    }

    @Override
    public @NotNull String getName() {
        return Objects.requireNonNull(element.getReferenceName(), "Reference name is null");
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.MEMBER_REFERENCE;
    }
}
