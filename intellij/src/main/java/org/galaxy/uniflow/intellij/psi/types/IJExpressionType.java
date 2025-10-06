package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiExpression;

public class IJExpressionType<T extends PsiExpression> extends IJType<T> {

    public IJExpressionType(T element) {
        super(element);
    }
}
