package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniJdk12Case extends UniCaseBase {

    @NotNull UniList<@NotNull UniCaseLabel> getLabels();

    @NotNull CaseKind getCaseKind();

    interface UniJdk12StatementCase extends UniJdk12Case {

        @NotNull UniList<UniStatement> getStatements();

    }

    interface UniJdk12RuleCase extends UniJdk12Case {

        void setBody(@NotNull UniElement body);

        @Nullable UniElement getBody();

    }

    enum CaseKind {
        /**
         * @see UniJdk12StatementCase
         */
        STATEMENT,
        /**
         * @see UniJdk12RuleCase
         */
        RULE
    }
}
