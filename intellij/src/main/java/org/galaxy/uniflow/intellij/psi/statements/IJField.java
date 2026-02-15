package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiField;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.statements.UniField;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.Nullable;

public class IJField extends IJVariableBase<PsiField> implements UniField {

    public IJField(PsiField element) {
        super(element);
    }

    @Override
    public @Nullable UniClass getEnclosingClass() {
        return UniflowWrapper.wrap(element.getContainingClass());
    }
}
