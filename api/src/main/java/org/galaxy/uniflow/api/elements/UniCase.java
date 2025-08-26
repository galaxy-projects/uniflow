package org.galaxy.uniflow.api.elements;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniCase extends UniElement {

    @NotNull UniList<UniExpression> getExpressions();

    @NotNull UniList<UniStatement> getStatements();

    void setBody(@NotNull UniElement body);

    @Nullable UniElement getBody();

    void setKind(@NotNull CaseKind caseKind);

    @NotNull CaseKind getCaseKind();

    enum CaseKind {
        STATEMENT,
        RULE
    }
}
