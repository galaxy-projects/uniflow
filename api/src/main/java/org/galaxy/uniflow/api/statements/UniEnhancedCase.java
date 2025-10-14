package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniEnhancedCase extends UniCaseBase {

    @NotNull CaseKind getCaseKind();

    interface UniStatementCase extends UniEnhancedCase {

        @NotNull UniList<UniStatement> getStatements();

    }

    interface UniRuleCase extends UniEnhancedCase {

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
