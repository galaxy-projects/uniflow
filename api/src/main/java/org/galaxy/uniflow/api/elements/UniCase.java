package org.galaxy.uniflow.api.elements;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniCase extends UniStatement {

    @NotNull UniList<UniCaseLabel> getLabels();

    @NotNull CaseKind getCaseKind();

    interface UniStatementCase extends UniCase {

        @NotNull UniList<UniStatement> getStatements();

    }

    interface UniRuleCase extends UniCase {

        void setBody(@NotNull UniElement body);

        @Nullable UniElement getBody();

    }

    enum CaseKind {
        /**
         * @see UniStatementCase
         */
        STATEMENT,
        /**
         * @see UniRuleCase
         */
        RULE
    }
}
