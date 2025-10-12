package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiCatchSection;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiTryStatement;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;

public class IJCatchList extends IJList<PsiTryStatement, PsiCatchSection, UniCatch> {

    public IJCatchList(PsiTryStatement psiTryStatement) {
        super(psiTryStatement, UniCatch[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrap);
    }

    @Override
    protected PsiCatchSection[] getElements() {
        return list.getCatchSections();
    }

    @Override
    protected PsiTryStatement createEmptyList() {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiTryStatement newTry = (PsiTryStatement) factory.createStatementFromText("try (a) {} finally {}", null);

        assert list.getTryBlock() != null;
        assert newTry.getTryBlock() != null;
        assert newTry.getResourceList() != null;
        assert newTry.getFinallyBlock() != null;

        if (list.getResourceList() != null)
            newTry.getResourceList().replace(list.getResourceList());
        else
            newTry.getResourceList().delete();
        newTry.getTryBlock().replace(list.getTryBlock());
        if (list.getFinallyBlock() != null)
            newTry.getFinallyBlock().replace(list.getFinallyBlock());
        else
            newTry.getFinallyBlock().delete();
        return newTry;
    }
}
