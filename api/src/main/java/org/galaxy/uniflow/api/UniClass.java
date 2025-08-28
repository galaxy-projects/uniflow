package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.lists.UniIndexedList;
import org.galaxy.uniflow.api.lists.UniMethodList;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniClass {

    @NotNull UniModifiers getModifiers();

    @NotNull UniClassType asType();

    @NotNull String getName();

    boolean isInterface();

    boolean isAnnotationType();

    boolean isEnum();

    boolean isRecord();

    @NotNull UniList<@NotNull UniType> getExtends();

    @NotNull UniList<@NotNull UniType> getImplements();

    @Nullable UniClass getSuperClass();

    @NotNull UniIndexedList<@NotNull UniTypeParameter> getTypeParameters();

    @NotNull UniClass @NotNull [] getInterfaces();

    @NotNull UniFieldList getFields();

    @NotNull UniMethodList getMethods();

    @NotNull UniMethodList getConstructors();

    @NotNull UniList<UniClassInitializer> getInitializers();

    @NotNull UniClass @NotNull [] getInnerClasses();

    void addInnerClass(@NotNull UniClass innerClass);

    void removeInnerClass(@NotNull UniClass innerClass);

}
