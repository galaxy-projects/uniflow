package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.pattern.UniPattern;
import org.jetbrains.annotations.Nullable;

public interface UniPatternInstanceOf extends UniInstanceOf {

    void setPattern(@Nullable UniPattern pattern);

    @Nullable UniPattern getPattern();
}
