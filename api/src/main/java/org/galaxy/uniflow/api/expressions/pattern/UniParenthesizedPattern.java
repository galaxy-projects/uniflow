package org.galaxy.uniflow.api.expressions.pattern;

import org.jetbrains.annotations.NotNull;

public interface UniParenthesizedPattern extends UniPattern {

    void setPattern(@NotNull UniPattern pattern);

    @NotNull UniPattern getPattern();

}
