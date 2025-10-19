package org.galaxy.uniflow.api.pattern;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniDeconstructionPattern extends UniPattern {

    void setDeconstructor(@NotNull UniExpression deconstructor);

    @NotNull UniExpression getDeconstructor();

    @NotNull UniList<@NotNull UniPattern> getNestedPatterns();

}
