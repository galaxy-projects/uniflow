package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiNewExpression;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniNewArray;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.lists.IJEmptyList;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJNewArray extends IJExpression<PsiNewExpression> implements UniNewArray {

    public IJNewArray(PsiNewExpression element) {
        super(element);
    }

    @Override
    public @Nullable UniType getType() {
        return UniflowWrapper.wrap(element.getType());
    }

    @Override
    public @NotNull UniList<@NotNull UniExpression> getDimensions() {
        return IJLists.expressions(element.getArgumentList());
    }

    @Override
    public @NotNull UniList<@NotNull UniExpression> getInitializers() {
        return IJLists.arrayInitializers(element.getArrayInitializer());
    }

    @Override
    public @NotNull UniList<@NotNull UniAnnotationHolder> getDimAnnotations() {
        return IJEmptyList.create(UniAnnotationHolder.class);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.NEW_ARRAY;
    }

    @Override
    public @NotNull UniList<@NotNull UniAnnotation> getAnnotations() {
        return IJEmptyList.create(UniAnnotation.class);
    }

    @Override
    public @Nullable UniAnnotation getAnnotation(@NotNull UniClassType type) {
        return null;
    }

    @Override
    public @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type) {
        return new UniAnnotation[0];
    }

    @Override
    public boolean hasAnnotation(@NotNull UniClassType type) {
        return false;
    }
}
