package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiArrayType;
import org.galaxy.uniflow.api.types.UniArrayType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJArrayType extends IJType<PsiArrayType> implements UniArrayType {

    public IJArrayType(PsiArrayType type) {
        super(type);
    }

    @Override
    public void setType(@NotNull UniType type) {}

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.wrap(type.getComponentType());
    }
}
