package org.galaxy.uniflow.intellij.psi.elements.labels;

import com.intellij.psi.PsiCaseLabelElement;
import com.intellij.psi.PsiPattern;
import org.galaxy.uniflow.api.elements.labels.UniPatternCaseLabel;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.NotNull;

public class IJPatternCaseLabel extends IJCaseLabelBase<PsiCaseLabelElement> implements UniPatternCaseLabel {

    private PsiPattern pattern;

    public IJPatternCaseLabel(PsiCaseLabelElement element, PsiPattern pattern) {
        super(element);
        this.pattern = pattern;
    }

    @Override
    public void setPattern(@NotNull UniPattern pattern) {
        PsiPattern psiPattern = IntellijUnwrapper.unwrap(pattern);

        if (this.pattern != null)
            this.pattern.delete();
        element.replace(psiPattern);
        this.pattern = psiPattern;
    }

    @Override
    public @NotNull UniPattern getPattern() {
        return null;
    }

    @Override
    public @NotNull Kind getKind() {
        return null;
    }
}
