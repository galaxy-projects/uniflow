package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiResourceList;
import com.intellij.psi.PsiResourceListElement;
import com.intellij.psi.PsiTryStatement;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;

import java.util.ArrayList;
import java.util.List;

public class IJResourceList extends IJList<PsiResourceList, PsiResourceListElement, UniElement> {

    public IJResourceList(PsiResourceList list) {
        super(list, UniElement[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrapResource);
    }

    @Override
    protected PsiResourceListElement[] getElements() {
        List<PsiResourceListElement> elements = new ArrayList<>();

        list.forEach(elements::add);
        return elements.toArray(new PsiResourceListElement[0]);
    }

    @Override
    protected PsiResourceList createEmptyList() {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiTryStatement dummyTry = (PsiTryStatement) factory.createStatementFromText("try () {}", null);

        return dummyTry.getResourceList();
    }
}
