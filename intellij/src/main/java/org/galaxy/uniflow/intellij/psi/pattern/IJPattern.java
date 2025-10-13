package org.galaxy.uniflow.intellij.psi.pattern;

import com.intellij.psi.PsiPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.intellij.psi.IJElement;

public abstract class IJPattern<T extends PsiPattern> extends IJElement<T> implements UniPattern {

    public IJPattern(T element) {
        super(element);
    }
}
