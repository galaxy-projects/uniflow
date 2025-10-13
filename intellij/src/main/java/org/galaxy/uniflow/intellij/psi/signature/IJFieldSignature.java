package org.galaxy.uniflow.intellij.psi.signature;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiParameter;
import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public record IJFieldSignature(PsiParameter parameter) implements UniFieldSignature {

    @Override
    public @NotNull UniClassType getOwner() {
        if (parameter.getParent() instanceof PsiClass psiClass)
            return UniflowWrapper.wrapClassType(psiClass);
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.wrap(parameter.getType());
    }

    @Override
    public @NotNull String getName() {
        return parameter.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IJFieldSignature fieldSignature)
            return fieldSignature.parameter.isEquivalentTo(parameter);
        return false;
    }
}
