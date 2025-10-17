package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.elements.labels.UniConstantCaseLabel;
import org.galaxy.uniflow.api.elements.labels.UniPatternCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.pattern.UniAnyPattern;
import org.galaxy.uniflow.api.pattern.UniDeconstructionPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.statements.UniJdk21Case;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UniJdk21ElementFactory extends UniJdk15ElementFactory {

    @NotNull UniJdk21Case createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                     @Nullable UniExpression guard,
                                     @NotNull List<@NotNull UniStatement> statements);

    @NotNull UniJdk21Case createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                     @Nullable UniExpression guard,
                                     @NotNull UniElement body);

    @NotNull UniConstantCaseLabel createConstantCaseLabel(@NotNull UniExpression expression);

    @NotNull UniPatternCaseLabel createPatternCaseLabel(@NotNull UniPattern pattern);

    @NotNull UniAnyPattern createAnyPattern();

    @NotNull UniDeconstructionPattern createDeconstructionPattern(@NotNull UniExpression deconstructor,
                                                                  @NotNull List<@NotNull UniPattern> nestedPatterns);

}
