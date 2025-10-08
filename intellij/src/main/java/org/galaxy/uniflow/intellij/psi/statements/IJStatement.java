package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiStatement;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.IJElement;

public abstract class IJStatement<T extends PsiStatement> extends IJElement<T> implements UniStatement {

    public IJStatement(T element) {
        super(element);
    }
}
