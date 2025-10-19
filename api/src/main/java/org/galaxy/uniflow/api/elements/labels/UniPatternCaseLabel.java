package org.galaxy.uniflow.api.elements.labels;

import org.galaxy.uniflow.api.pattern.UniPattern;
import org.jetbrains.annotations.NotNull;

public interface UniPatternCaseLabel extends UniCaseLabel {

    void setPattern(@NotNull UniPattern pattern);

    @NotNull UniPattern getPattern();

}
