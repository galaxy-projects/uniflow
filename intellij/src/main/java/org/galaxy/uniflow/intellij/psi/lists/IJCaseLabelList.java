package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiCaseLabelElement;
import com.intellij.psi.PsiCaseLabelElementList;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiSwitchLabelStatement;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;

public class IJCaseLabelList extends IJList<PsiCaseLabelElementList, PsiCaseLabelElement, UniCaseLabel> {

    public IJCaseLabelList(PsiCaseLabelElementList list) {
        super(list, UniCaseLabel[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrap);
    }

    @Override
    protected PsiCaseLabelElement[] getElements() {
        return list.getElements();
    }

    @Override
    protected PsiCaseLabelElementList createEmptyList() {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiSwitchLabelStatement newCase = (PsiSwitchLabelStatement) factory.createStatementFromText("case 1:", null);
        PsiCaseLabelElementList emptyList = newCase.getCaseLabelElementList();

        assert emptyList != null;
        emptyList.getFirstChild().delete();

        return emptyList;
    }
}
