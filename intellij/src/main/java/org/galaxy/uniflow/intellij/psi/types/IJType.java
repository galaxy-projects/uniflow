package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiElement;
import org.galaxy.uniflow.api.types.UniType;

public class IJType<T extends PsiElement> implements UniType {

    private final T element;

    public IJType(T element) {
        this.element = element;
    }

    public T getElement() {
        return element;
    }
}
