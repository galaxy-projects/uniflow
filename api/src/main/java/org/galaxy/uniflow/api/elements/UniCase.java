package org.galaxy.uniflow.api.elements;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.expressions.UniExpressionList;
import org.galaxy.uniflow.api.statements.UniStatementList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniCase extends UniElement {

    @NotNull UniExpressionList getExpressions();

    @NotNull UniStatementList getStatements();

    void setBody(@NotNull UniElement body);

    @Nullable UniElement getBody();

    void setKind(@NotNull CaseKind caseKind);

    @NotNull CaseKind getKind();

    enum CaseKind {
        STATEMENT,
        RULE
    }
}
