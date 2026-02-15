package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiExpression;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.intellij.psi.IJElement;

public abstract class IJExpression<T extends PsiExpression> extends IJElement<T> implements UniExpression {

    public IJExpression(T element) {
        super(element);
    }
}
