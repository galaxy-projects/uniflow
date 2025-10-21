package org.galaxy.uniflow.intellij.psi.pattern;

import com.intellij.psi.PsiUnnamedPattern;
import org.galaxy.uniflow.api.pattern.UniAnyPattern;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class IJAnyPattern extends IJPattern<PsiUnnamedPattern> implements UniAnyPattern {

    public IJAnyPattern(PsiUnnamedPattern element) {
        super(element);
    }

    @Override
    public void setType(@NotNull UniType type) {
        element.getTypeElement().replace(IntellijUnwrapper.unwrap(type));
    }

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.wrapAsType(element.getTypeElement());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.ANY_PATTERN;
    }
}
