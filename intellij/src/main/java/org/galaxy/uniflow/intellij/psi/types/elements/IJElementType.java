package org.galaxy.uniflow.intellij.psi.types.elements;

import com.intellij.psi.PsiElement;
import org.galaxy.uniflow.api.types.UniType;

public class IJElementType<T extends PsiElement> implements UniType {

    protected final T element;

    public IJElementType(T element) {
        this.element = element;
    }

    public T getElement() {
        return element;
    }
}
