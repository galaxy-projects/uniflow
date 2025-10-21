package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.api.statements.UniParameter;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJParameterList extends IJList<PsiParameterList, PsiParameter, UniParameter> implements UniParameterList {

    public IJParameterList(PsiParameterList list) {
        super(list, PsiParameterList::getParameters, UniParameter[]::new,
                UniflowWrapper::wrap, IntellijUnwrapper::unwrap);
    }

    @Override
    public boolean hasParameter(@NotNull String name) {
        return stream().anyMatch(parameter -> parameter.getName().equals(name));
    }

    @Override
    public int getParameterIndex(@NotNull String name) {
        PsiParameter[] parameters = getElements();

        if (parameters == null) return -1;
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getName().equals(name))
                return i;
        }
        return -1;
    }
}
