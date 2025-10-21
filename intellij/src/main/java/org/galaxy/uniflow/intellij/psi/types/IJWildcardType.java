package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiType;
import com.intellij.psi.PsiWildcardType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniWildcardType;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJWildcardType extends IJType<PsiWildcardType> implements UniWildcardType {

    public IJWildcardType(PsiWildcardType type) {
        super(type);
    }

    @Override
    public @NotNull UniType getType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @Nullable UniType getBound() {
        PsiType bound = type.getBound();

        return bound != null ? UniflowWrapper.wrap(bound) : null;
    }

    @Override
    public @NotNull BoundKind getBoundKind() {
        return type.isExtends() ? BoundKind.EXTENDS : (type.isSuper() ? BoundKind.SUPER : BoundKind.UNBOUND);
    }
}
