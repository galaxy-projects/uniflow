package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniSwitchExpression;
import org.galaxy.uniflow.api.statements.UniJdk12Case;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.statements.UniSwitch;
import org.galaxy.uniflow.api.statements.UniYield;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UniJdk12ElementFactory extends UniJdk10ElementFactory {

    @NotNull UniYield createYield(@NotNull UniExpression value);

    @NotNull UniSwitch createSwitchStatement(@NotNull UniExpression selector,
                                             @NotNull List<@NotNull UniJdk12Case> cases);

    @NotNull UniSwitchExpression createSwitchExpression(@NotNull UniExpression selector,
                                                        @NotNull List<@NotNull UniJdk12Case> cases);

    @NotNull UniJdk12Case createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                     @NotNull List<@NotNull UniStatement> statements);

    @NotNull UniJdk12Case createCase(@NotNull List<@NotNull UniCaseLabel> labels, @NotNull UniElement body);

}
