package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiClassType;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniParameterizedType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.lists.IJReadOnlyList;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class IJParameterizedType extends IJType<PsiClassType> implements UniParameterizedType {

    public IJParameterizedType(PsiClassType type) {
        super(type);
    }

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.wrap(type.rawType());
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getTypeArguments() {
        return new IJReadOnlyList<>(
                Arrays.stream(type.getParameters()).map(UniflowWrapper::wrap).toList(),
                UniType[]::new
        );
    }
}
