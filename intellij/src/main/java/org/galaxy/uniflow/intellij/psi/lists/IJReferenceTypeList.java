package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiReferenceList;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;

public class IJReferenceTypeList extends IJList<PsiReferenceList, PsiJavaCodeReferenceElement, UniType> {

    public IJReferenceTypeList(PsiReferenceList list) {
        super(list, UniType[]::new, UniflowWrapper::wrapClassType, IntellijUnwrapper::unwrapType);
    }

    @Override
    protected PsiJavaCodeReferenceElement[] getElements() {
        return list.getReferenceElements();
    }

    @Override
    protected PsiReferenceList createEmptyList() {
        return IntellijUniflow.getInstance().factory.createReferenceList(new PsiJavaCodeReferenceElement[0]);
    }
}
