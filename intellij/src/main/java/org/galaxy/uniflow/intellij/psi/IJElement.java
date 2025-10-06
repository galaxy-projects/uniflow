package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.PsiElement;
import org.galaxy.uniflow.api.UniElement;
import org.jetbrains.annotations.NotNull;

public abstract class IJElement<T extends PsiElement> implements UniElement {

    protected T element;

    public IJElement(T element) {
        this.element = element;
    }

    @Override
    public int getPosition() {
        return element.getStartOffsetInParent();
    }

    @Override
    public boolean hasTag(@NotNull Tag tag) {
        return false;
    }

    @Override
    public Tag getTag() {
        return null;
    }

    @SuppressWarnings("unchecked")
    protected T replace(T newElement) {
        this.element = (T) element.replace(newElement);
        return element;
    }
}
