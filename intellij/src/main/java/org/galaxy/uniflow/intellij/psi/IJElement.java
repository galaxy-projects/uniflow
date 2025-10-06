package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.PsiElement;
import org.galaxy.uniflow.api.UniElement;

public abstract class IJElement<T extends PsiElement> implements UniElement {

    protected T element;

    public IJElement(T element) {
        this.element = element;
    }

    @Override
    public int getPosition() {
        return element.getStartOffsetInParent();
    }

    @SuppressWarnings("unchecked")
    protected T replace(T newElement) {
        this.element = (T) element.replace(newElement);
        return element;
    }
}
