package org.galaxy.uniflow.intellij.psi.signature;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public record IJFieldSignature(PsiField field) implements UniFieldSignature {

    @Override
    public @NotNull UniClassType getOwner() {
        if (field.getParent() instanceof PsiClass psiClass)
            return UniflowWrapper.wrapClassType(psiClass);
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.wrap(field.getType());
    }

    @Override
    public @NotNull String getName() {
        return field.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IJFieldSignature(PsiField field1))
            return field1.isEquivalentTo(field);
        return false;
    }
}
