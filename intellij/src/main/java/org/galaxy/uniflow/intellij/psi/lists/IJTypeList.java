package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReferenceParameterList;
import com.intellij.psi.PsiTypeElement;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;

public class IJTypeList extends IJList<PsiReferenceParameterList, PsiTypeElement, UniType> {

    public IJTypeList(PsiReferenceParameterList list) {
        super(list, UniType[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrap);
    }

    @Override
    protected PsiTypeElement[] getElements() {
        return list.getTypeParameterElements();
    }

    @Override
    protected PsiReferenceParameterList createEmptyList() {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiMethodCallExpression call = (PsiMethodCallExpression) factory.createExpressionFromText("foo()", null);

        return call.getTypeArgumentList();
    }
}
