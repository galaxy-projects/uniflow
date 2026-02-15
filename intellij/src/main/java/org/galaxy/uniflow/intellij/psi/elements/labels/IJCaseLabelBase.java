package org.galaxy.uniflow.intellij.psi.elements.labels;

import com.intellij.psi.PsiElement;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.intellij.psi.IJElement;

public abstract class IJCaseLabelBase<T extends PsiElement> extends IJElement<T> implements UniCaseLabel {

    public IJCaseLabelBase(T element) {
        super(element);
    }
}
