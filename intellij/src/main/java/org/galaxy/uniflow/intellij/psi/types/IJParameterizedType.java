package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiDiamondType;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniParameterizedType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.lists.IJEmptyList;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJParameterizedType extends IJType<PsiDiamondType> implements UniParameterizedType {

    public IJParameterizedType(PsiDiamondType type) {
        super(type);
    }

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.wrap(type.getDeepComponentType());
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getTypeArguments() {
        return IJEmptyList.create(UniType.class);
    }
}
