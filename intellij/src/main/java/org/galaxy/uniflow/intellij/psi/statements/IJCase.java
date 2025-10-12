package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.lists.IJCaseLabelList;
import org.galaxy.uniflow.intellij.psi.lists.IJCaseStatementList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class IJCase<T extends PsiSwitchLabelStatementBase> extends IJStatement<T> implements UniCase {

    public IJCase(T caseStatement) {
        super(caseStatement);
    }

    @Override
    public @NotNull UniList<UniCaseLabel> getLabels() {
        return new IJCaseLabelList(element.getCaseLabelElementList());
    }

    @Override
    public @NotNull Kind getKind() {
        if (element.isDefaultCase())
            return Kind.DEFAULT_CASE_LABEL;
        return Kind.CASE;
    }

    public static class IJStatementCase extends IJCase<PsiSwitchLabelStatement> implements UniStatementCase {

        private final List<PsiStatement> statements;

        public IJStatementCase(PsiSwitchLabelStatement caseStatement, List<PsiStatement> statements) {
            super(caseStatement);
            this.statements = statements;
        }

        @Override
        public @NotNull UniList<UniStatement> getStatements() {
            return new IJCaseStatementList(element, statements);
        }

        @Override
        public @NotNull CaseKind getCaseKind() {
            return CaseKind.STATEMENT;
        }
    }

    public static class IJRuleCase extends IJCase<PsiSwitchLabeledRuleStatement> implements UniRuleCase {

        public IJRuleCase(PsiSwitchLabeledRuleStatement caseStatement) {
            super(caseStatement);
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
    }
}
