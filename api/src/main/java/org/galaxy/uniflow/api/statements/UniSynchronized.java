package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniSynchronized extends UniStatement {

    void setLock(@NotNull UniExpression lock);

    @NotNull UniExpression getLock();

    void setBody(@NotNull UniBlock body);

    @NotNull UniBlock getBody();

}
