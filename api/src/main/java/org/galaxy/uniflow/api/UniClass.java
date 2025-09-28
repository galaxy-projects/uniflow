package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.lists.UniMethodList;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.jetbrains.annotations.NotNull;

public interface UniClass extends UniStatement, UniModifiersHolder {

    @NotNull UniClassType asType();

    @NotNull UniVariable createThis();

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

}
