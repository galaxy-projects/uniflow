package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiSwitchLabeledRuleStatement;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.lists.IJCaseLabelList;
import org.galaxy.uniflow.intellij.psi.lists.IJEmptyList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJRuleCase extends IJStatement<PsiSwitchLabeledRuleStatement> implements UniCase {

    public IJRuleCase(PsiSwitchLabeledRuleStatement element) {
        super(element);
    }

    @Override
    public @NotNull UniList<UniCaseLabel> getLabels() {
        return new IJCaseLabelList(element.getCaseLabelElementList());
    }

    @Override
    public @NotNull UniList<UniExpression> getExpressions() {
        return IJEmptyList.create(UniExpression.class);
    }

    @Override
    public @NotNull UniList<UniStatement> getStatements() {
        return IJEmptyList.create(UniStatement.class);
    }

    @Override
    public void setBody(@NotNull UniElement body) {
        PsiElement bodyElement = IntellijUnwrapper.unwrap(body);

        if (element.getBody() != null)
            element.getBody().replace(bodyElement);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiSwitchLabeledRuleStatement dummy = (PsiSwitchLabeledRuleStatement) factory.createStatementFromText(
                    "case 1 -> null", null);

            assert dummy.getBody() != null;
            assert dummy.getCaseLabelElementList() != null;
            assert element.getCaseLabelElementList() != null;
            dummy.getCaseLabelElementList().replace(element.getCaseLabelElementList());
            dummy.getBody().replace(bodyElement);
            replace(dummy);
        }
    }

    @Override
    public @Nullable UniElement getBody() {
        return UniflowWrapper.wrap(element.getBody());
    }

    @Override
    public @NotNull CaseKind getCaseKind() {
        return CaseKind.RULE;
    }

    @Override
    public @NotNull Kind getKind() {
        if (element.isDefaultCase())
            return Kind.DEFAULT_CASE_LABEL;
        return Kind.CASE;
    }
}
