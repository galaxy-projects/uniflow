package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniJdk21Case extends UniJdk12Case {

    void setGuard(@NotNull UniExpression guard);

    @NotNull UniExpression getGuard();

    interface UniJdk21StatementCase extends UniJdk12StatementCase, UniJdk21Case {}

    interface UniJdk21RuleCase extends UniJdk12RuleCase, UniJdk21Case {}
}
