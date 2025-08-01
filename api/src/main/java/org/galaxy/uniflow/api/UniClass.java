package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.modifiers.UniModifierHolder;
import org.galaxy.uniflow.api.types.UniTypeList;
import org.galaxy.uniflow.api.types.UniTypeName;
import org.galaxy.uniflow.api.types.UniTypeParameterHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniClass extends UniAnnotationHolder, UniModifierHolder, UniTypeParameterHolder {

    @NotNull UniTypeName getName();

    @NotNull UniTypeName asTypeName();

    boolean isInterface();

    boolean isAnnotationType();

    boolean isEnum();

    boolean isRecord();

    @NotNull UniTypeList getExtends();

    @NotNull UniTypeList getImplements();

    @Nullable UniClass getSuperClass();

    @NotNull UniClass @NotNull [] getInterfaces();

    @NotNull UniField @NotNull [] getFields();

    @NotNull UniMethodList getMethods();

    @NotNull UniMethodList getConstructors();

    @NotNull UniClassInitializer @NotNull [] getInitializers();

    @NotNull UniClass @NotNull [] getInnerClasses();

    void addInnerClass(@NotNull UniClass innerClass);

    void removeInnerClass(@NotNull UniClass innerClass);

}
