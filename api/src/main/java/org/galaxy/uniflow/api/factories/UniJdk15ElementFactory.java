package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniInstanceOf;
import org.galaxy.uniflow.api.pattern.UniBindingPattern;
import org.galaxy.uniflow.api.pattern.UniGuardedPattern;
import org.galaxy.uniflow.api.pattern.UniParenthesizedPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UniJdk15ElementFactory extends UniJdk12ElementFactory {

    @NotNull UniClass createRecord(@NotNull UniModifiers modifiers,
                                   @NotNull String name,
                                   @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                   @NotNull List<@NotNull UniType> implementing,
                                   @NotNull List<@NotNull UniClassInitializer> initializers);

    @NotNull UniClass createClass(@NotNull UniModifiers modifiers,
                                  @NotNull String name,
                                  @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                  @Nullable UniType extending,
                                  @NotNull List<@NotNull UniType> implementing,
                                  @NotNull List<@NotNull UniExpression> permitting,
                                  @NotNull List<@NotNull UniClassInitializer> initializers);

    @NotNull UniBindingPattern createBindingPattern(@NotNull UniVariable variable);

    @NotNull UniGuardedPattern createGuardedPattern(@NotNull UniPattern pattern, @NotNull UniExpression expression);

    @NotNull UniParenthesizedPattern createParenthesizedPattern(@NotNull UniPattern pattern);

    @NotNull UniInstanceOf createInstanceOf(@NotNull UniExpression expression, @NotNull UniPattern pattern);

}
