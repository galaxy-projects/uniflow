package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UniJdk10ElementFactory extends UniJdk9ElementFactory {

    @NotNull UniVariable createVarVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                           @NotNull String name,
                                           @NotNull Class<?> type,
                                           @Nullable UniExpression init);

    @NotNull UniVariable createVarVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                           @NotNull String name,
                                           @NotNull UniType type,
                                           @Nullable UniExpression init);

    default @NotNull UniVariable createVarVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                                   @NotNull String name,
                                                   @NotNull Class<?> type) {
        return createVarVariable(annotations, name, type, null);
    }

    default @NotNull UniVariable createVarVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                                   @NotNull String name,
                                                   @NotNull UniType type) {
        return createVarVariable(annotations, name, type, null);
    }

}
