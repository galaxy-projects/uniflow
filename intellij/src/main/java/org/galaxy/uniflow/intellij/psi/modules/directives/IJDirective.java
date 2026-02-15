package org.galaxy.uniflow.intellij.psi.modules.directives;

import com.intellij.psi.PsiStatement;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.galaxy.uniflow.intellij.psi.statements.IJStatement;

public abstract class IJDirective<T extends PsiStatement> extends IJStatement<T> implements UniDirective {

    public IJDirective(T element) {
        super(element);
    }
}
