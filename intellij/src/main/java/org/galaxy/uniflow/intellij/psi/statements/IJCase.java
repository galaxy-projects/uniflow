package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiSwitchLabelStatement;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.lists.IJCaseLabelList;
import org.galaxy.uniflow.intellij.psi.lists.IJCaseStatementList;
import org.galaxy.uniflow.intellij.psi.lists.IJEmptyList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IJCase extends IJStatement<PsiSwitchLabelStatement> implements UniCase {

    private final List<PsiStatement> statements;

    public IJCase(PsiSwitchLabelStatement caseStatement, List<PsiStatement> statements) {
        super(caseStatement);
        this.statements = statements;
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
        return new IJCaseStatementList(element, statements);
    }

    @Override
    public void setBody(@NotNull UniElement body) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @Nullable UniElement getBody() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull CaseKind getCaseKind() {
        return CaseKind.STATEMENT;
    }

    @Override
    public @NotNull Kind getKind() {
        if (element.isDefaultCase())
            return Kind.DEFAULT_CASE_LABEL;
        return Kind.CASE;
    }
}
