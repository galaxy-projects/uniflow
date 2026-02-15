package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiParameter;
import org.galaxy.uniflow.api.statements.UniParameter;

public class IJParameter extends IJVariableBase<PsiParameter> implements UniParameter {

    public IJParameter(PsiParameter element) {
        super(element);
    }
}
