package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiNewExpression;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniNewClass;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJNewClass extends IJExpression<PsiNewExpression> implements UniNewClass {

    public IJNewClass(PsiNewExpression element) {
        super(element);
    }

    @Override
    public @Nullable UniExpression getEnclosingExpression() {
        return UniflowWrapper.wrap(element.getClassOrAnonymousClassReference());
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getTypeArguments() {
        return IJLists.types(element.getTypeArgumentList());
    }

    @Override
    public @NotNull UniExpression getIdentifier() {
        return UniflowWrapper.wrap(element.getQualifier());
    }

    @Override
    public @NotNull UniList<@NotNull UniExpression> getArguments() {
        return IJLists.expressions(element.getArgumentList());
    }

    @Override
    public @NotNull UniClassType getClassName() {
        return (UniClassType) UniflowWrapper.wrap(element.getType());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.NEW_CLASS;
    }
}
