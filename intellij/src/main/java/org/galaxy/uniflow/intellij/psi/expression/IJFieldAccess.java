package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceExpression;
import org.galaxy.uniflow.api.expressions.UniFieldAccess;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.types.IJExpressionType;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IJFieldAccess extends IJExpression<PsiReferenceExpression> implements UniFieldAccess {

    public IJFieldAccess(PsiReferenceExpression element) {
        super(element);
    }

    @Override
    public void setSelected(@NotNull UniType selected) {
        if (!(selected instanceof IJExpressionType<?>))
            throw new IllegalArgumentException("Selected type must be an IJExpressionType");
        IJExpressionType<?> type = (IJExpressionType<?>) selected;

        if (element.getQualifierExpression() != null)
            element.getQualifierExpression().replace(type.getElement());
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiReferenceExpression newRef = (PsiReferenceExpression) factory.createExpressionFromText(
                    type.getElement().getText() + "." + element.getReferenceName(), null);

            replace(newRef);
        }
    }

    @Override
    public @NotNull UniType getSelected() {
        return UniflowWrapper.wrapAsType(element.getQualifierExpression());
    }

    @Override
    public void setName(@NotNull String name) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiReference ref = factory.createReferenceFromText(name, null);
        PsiElement qualifier = element.getQualifier();

        if (qualifier == null)
            throw new IllegalStateException("Qualifier is null");
        if (element.getReferenceNameElement() != null)
            element.getReferenceNameElement().replace(ref.getElement());
        else {
            PsiReferenceExpression newRef = (PsiReferenceExpression) factory.createExpressionFromText(
                    qualifier.getText() + "." + name, null);

            replace(newRef);
        }
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
