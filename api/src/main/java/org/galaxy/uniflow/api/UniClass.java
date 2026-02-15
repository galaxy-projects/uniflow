package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.interfaces.UniExpressionSupplier;
import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.lists.UniMethodList;
import org.galaxy.uniflow.api.statements.UniField;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniClass extends UniStatement, UniModifiersHolder {

    @NotNull UniClassType asType();

    @NotNull String getName();

    boolean isInterface();

    boolean isAnnotationType();

    boolean isEnum();

    boolean isRecord();

    @NotNull UniType getExtends();

    @NotNull UniList<@NotNull UniType> getImplements();

    @NotNull UniList<@NotNull UniTypeParameter> getTypeParameters();

    @NotNull UniFieldList getFields();

    @NotNull UniMethodList getMethods();

    @NotNull UniMethodList getConstructors();

    @NotNull UniList<@NotNull UniClassInitializer> getInitializers();

    @NotNull UniList<@NotNull UniClass> getInnerClasses();

    @NotNull UniMethodBuilder createConstructor();

    @NotNull UniMethodBuilder createMethod(@NotNull String name);

    @NotNull UniField createField(@NotNull UniModifiers modifiers,
                                  @NotNull String name,
                                  @NotNull Class<?> type,
                                  @Nullable UniExpressionSupplier init);

    @NotNull UniField createField(@NotNull UniModifiers modifiers,
                                  @NotNull String name,
                                  @NotNull UniType type,
                                  @Nullable UniExpressionSupplier init);

}
