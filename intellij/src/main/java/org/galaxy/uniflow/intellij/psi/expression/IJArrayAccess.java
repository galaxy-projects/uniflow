package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiArrayAccessExpression;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import org.galaxy.uniflow.api.expressions.UniArrayAccess;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IJArrayAccess extends IJExpression<PsiArrayAccessExpression> implements UniArrayAccess {

    public IJArrayAccess(PsiArrayAccessExpression element) {
        super(element);
    }

    @Override
    public void set(@NotNull UniExpression array, @NotNull UniExpression index) {
        replace(IntellijUnwrapper.unwrap(array), IntellijUnwrapper.unwrap(index));
    }

    @Override
    public void setArray(@NotNull UniExpression array) {
        Objects.requireNonNull(element.getIndexExpression(), "Array access index cannot be null");

        replace(IntellijUnwrapper.unwrap(array), element.getIndexExpression());
    }

    @Override
    public @NotNull UniExpression getArray() {
        return UniflowWrapper.wrap(element.getArrayExpression());
    }

    @Override
    public void setIndex(@NotNull UniExpression index) {
        PsiExpression newIndex = IntellijUnwrapper.unwrap(index);

        if (element.getIndexExpression() != null)
            element.getIndexExpression().replace(newIndex);
        else
            replace(element.getArrayExpression(), newIndex);
    }

    @Override
    public @NotNull UniExpression getIndex() {
        return UniflowWrapper.wrap(element.getIndexExpression());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.ARRAY_ACCESS;
    }

    private void replace(PsiExpression array, PsiExpression index) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiArrayAccessExpression dummy = (PsiArrayAccessExpression) factory.createExpressionFromText("dummy[0]", null);

        dummy.getArrayExpression().replace(array);
        assert dummy.getIndexExpression() != null;
        dummy.getIndexExpression().replace(index);

        replace(dummy);
    }
}
