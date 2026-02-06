package org.galaxy.uniflow.intellij.psi.elements.labels;

import com.intellij.psi.PsiDefaultCaseLabelElement;
import org.galaxy.uniflow.api.elements.labels.UniDefaultCaseLabel;
import org.jetbrains.annotations.NotNull;

public class IJDefaultCaseLabel extends IJCaseLabelBase<PsiDefaultCaseLabelElement> implements UniDefaultCaseLabel {

    public IJDefaultCaseLabel(PsiDefaultCaseLabelElement element) {
        super(element);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.DEFAULT_CASE_LABEL;
    }
}
