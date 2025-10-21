package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniJdk21Case;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.galaxy.uniflow.intellij.psi.lists.statements.IJCaseStatementList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class IJCase<T extends PsiSwitchLabelStatementBase> extends IJStatement<T> implements UniJdk21Case {

    public IJCase(T caseStatement) {
        super(caseStatement);
    }

    @Override
    public @NotNull UniList<@NotNull UniCaseLabel> getLabels() {
        return IJLists.caseLabels(element.getCaseLabelElementList());
    }

    @Override
    public @Nullable UniExpression getGuard() {
        PsiExpression guard = element.getGuardExpression();

        return guard != null ? UniflowWrapper.wrap(guard) : null;
    }

    @Override
    public @NotNull Kind getKind() {
        if (element.isDefaultCase())
            return Kind.DEFAULT_CASE_LABEL;
        return Kind.CASE;
    }

    public static class IJStatementCase extends IJCase<PsiSwitchLabelStatement> implements UniJdk21StatementCase {

        private final List<PsiStatement> statements;

        public IJStatementCase(PsiSwitchLabelStatement caseStatement, List<PsiStatement> statements) {
            super(caseStatement);
            this.statements = statements;
        }

        @Override
        public void setGuard(@NotNull UniExpression guard) {
            PsiExpression expression = IntellijUnwrapper.unwrap(guard);

            if (element.getGuardExpression() != null)
                element.getGuardExpression().replace(expression);
            else {
                PsiElementFactory factory = IntellijUniflow.getInstance().factory;
                PsiSwitchLabelStatement newCase = (PsiSwitchLabelStatement) factory.createStatementFromText(
                        "case String s when s.length() > 0: return null;", null);

                assert newCase.getCaseLabelElementList() != null;
                assert element.getCaseLabelElementList() != null;
                assert newCase.getGuardExpression() != null;
                assert newCase.getEnclosingSwitchBlock() != null;
                assert element.getEnclosingSwitchBlock() != null;

                newCase.getCaseLabelElementList().replace(element.getCaseLabelElementList());
                newCase.getGuardExpression().replace(expression);
                newCase.getEnclosingSwitchBlock().replace(element.getEnclosingSwitchBlock());

                replace(newCase);
            }
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

    public static class IJRuleCase extends IJCase<PsiSwitchLabeledRuleStatement> implements UniJdk21RuleCase {

        public IJRuleCase(PsiSwitchLabeledRuleStatement caseStatement) {
            super(caseStatement);
        }

        @Override
        public void setBody(@NotNull UniElement body) {
            replace(element.getCaseLabelElementList(), element.getGuardExpression(), IntellijUnwrapper.unwrap(body));
        }

        @Override
        public void setGuard(@NotNull UniExpression guard) {
            replace(element.getCaseLabelElementList(), IntellijUnwrapper.unwrap(guard), element.getBody());
        }

        @Override
        public @Nullable UniElement getBody() {
            return UniflowWrapper.wrap(element.getBody());
        }

        @Override
        public @NotNull CaseKind getCaseKind() {
            return CaseKind.RULE;
        }

        private void replace(PsiCaseLabelElementList labels, PsiExpression guard, PsiElement body) {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiSwitchLabeledRuleStatement newCase = (PsiSwitchLabeledRuleStatement) factory.createStatementFromText(
                    "case String s when s.length() > 0 -> null;", null);

            assert newCase.getCaseLabelElementList() != null;
            assert newCase.getGuardExpression() != null;
            assert newCase.getBody() != null;

            newCase.getCaseLabelElementList().replace(labels);
            if (guard != null)
                newCase.getGuardExpression().replace(guard);
            else newCase.getGuardExpression().delete();
            newCase.getBody().replace(body);

            replace(newCase);
        }
    }
}
