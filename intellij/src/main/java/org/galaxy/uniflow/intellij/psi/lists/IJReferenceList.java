package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiReferenceList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;

public class IJReferenceList extends IJList<PsiReferenceList, PsiJavaCodeReferenceElement, UniExpression> {

    public IJReferenceList(PsiReferenceList list) {
        super(list, UniExpression[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrapReference);
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
