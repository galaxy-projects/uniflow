package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiTypeParameter;
import com.intellij.psi.PsiTypeParameterList;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;

public class IJTypeParameterList extends IJList<PsiTypeParameterList, PsiTypeParameter, UniTypeParameter> {

    public IJTypeParameterList(PsiTypeParameterList list) {
        super(list, UniTypeParameter[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrap);
    }

    @Override
    protected PsiTypeParameter[] getElements() {
        return list.getTypeParameters();
    }

    @Override
    protected PsiTypeParameterList createEmptyList() {
        return IntellijUniflow.getInstance().factory.createTypeParameterList();
    }
}
