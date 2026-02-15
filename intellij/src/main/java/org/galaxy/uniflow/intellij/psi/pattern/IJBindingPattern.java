package org.galaxy.uniflow.intellij.psi.pattern;

import com.intellij.psi.PsiTypeTestPattern;
import com.intellij.psi.PsiVariable;
import org.galaxy.uniflow.api.pattern.UniBindingPattern;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJBindingPattern extends IJPattern<PsiTypeTestPattern> implements UniBindingPattern {

    public IJBindingPattern(PsiTypeTestPattern element) {
        super(element);
    }

    @Override
    public void setVariable(@NotNull UniVariable variable) {
        PsiVariable patternVariable = IntellijUnwrapper.unwrap(variable);

        if (element.getPatternVariable() != null)
            element.getPatternVariable().replace(patternVariable);
        else {
            PsiTypeTestPattern newPattern = (PsiTypeTestPattern) Util.createPattern("String s");

            assert newPattern.getPatternVariable() != null;

            newPattern.getPatternVariable().replace(patternVariable);

            replace(newPattern);
        }
    }

    @Override
    public @NotNull UniVariable getVariable() {
        return UniflowWrapper.wrap(element.getPatternVariable());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.BINDING_PATTERN;
    }
}
