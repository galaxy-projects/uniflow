package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniSwitchExpression;
import org.galaxy.uniflow.api.statements.UniJdk15Case;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.statements.UniYield;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UniJdk12ElementFactory extends UniJdk10ElementFactory {

    @NotNull UniYield createYield(@NotNull UniExpression value);

    @NotNull UniSwitchExpression createSwitchExpression(@NotNull UniExpression selector,
                                                        @NotNull List<@NotNull UniJdk15Case> cases);

    @NotNull UniJdk15Case createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                     @NotNull List<@NotNull UniStatement> statements);

    @NotNull UniJdk15Case createCase(@NotNull List<@NotNull UniCaseLabel> labels, @NotNull UniElement body);

}
