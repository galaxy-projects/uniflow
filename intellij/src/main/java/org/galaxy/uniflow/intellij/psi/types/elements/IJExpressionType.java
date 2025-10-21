package org.galaxy.uniflow.intellij.psi.types.elements;

import com.intellij.psi.PsiExpression;

public class IJExpressionType<T extends PsiExpression> extends IJElementType<T> {

    public IJExpressionType(T element) {
        super(element);
    }
}
