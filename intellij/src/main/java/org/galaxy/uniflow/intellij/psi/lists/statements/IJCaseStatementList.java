package org.galaxy.uniflow.intellij.psi.lists.statements;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiSwitchLabelStatement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;

import java.util.List;

public class IJCaseStatementList extends IJStatementListBase<PsiSwitchLabelStatement> {

    public IJCaseStatementList(PsiSwitchLabelStatement caseLabel, List<PsiStatement> statements) {
        super(caseLabel, statements);
    }

    @Override
    protected PsiSwitchLabelStatement createEmpty() {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;

        PsiSwitchLabelStatement newCase;
        if (!parent.isDefaultCase()) {
            newCase = (PsiSwitchLabelStatement) factory.createStatementFromText("case 1:", null);

            assert newCase.getCaseLabelElementList() != null;
            assert parent.getCaseLabelElementList() != null;
            newCase.getCaseLabelElementList().replace(parent.getCaseLabelElementList());
        } else
            newCase = (PsiSwitchLabelStatement) factory.createStatementFromText("default:", null);
        return newCase;
    }
}
