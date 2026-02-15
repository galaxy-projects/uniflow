package org.galaxy.uniflow.intellij.psi.elements.labels;

import com.intellij.psi.PsiCaseLabelElement;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.jetbrains.annotations.NotNull;

public class IJCaseLabel extends IJCaseLabelBase<PsiCaseLabelElement> implements UniCaseLabel {

    public IJCaseLabel(PsiCaseLabelElement element) {
        super(element);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.CASE;
    }
}
