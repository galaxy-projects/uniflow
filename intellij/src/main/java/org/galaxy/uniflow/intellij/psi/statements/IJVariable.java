package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiVariable;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class IJVariable extends IJStatement<PsiVariable> implements UniVariable {

    public IJVariable(PsiVariable element) {
        super(element);
    }

    @Override
    public @NotNull String getName() {
        return Objects.requireNonNull(element.getName());
    }

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.wrap(element.getType());
    }

    @Override
    public void setInitializer(@Nullable UniExpression expression) {
        element.setInitializer(IntellijUnwrapper.unwrap(expression));
    }

    @Override
    public @Nullable UniExpression getInitializer() {
        return UniflowWrapper.wrap(element.getInitializer());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.VARIABLE;
    }

    @Override
    public @NotNull UniModifiers getModifiers() {
        return UniflowWrapper.wrap(element.getModifierList());
    }
}
