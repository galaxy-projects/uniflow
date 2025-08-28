package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.lists.UniIndexedList;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.jetbrains.annotations.NotNull;

public interface UniMethod extends UniMember {

    @NotNull String getName();

    void setReturnType(@NotNull UniType type);

    @NotNull UniType getReturnType();

    @NotNull UniIndexedList<@NotNull UniTypeParameter> getTypeParameters();

    @NotNull UniParameterList getParameters();

    @NotNull UniList<@NotNull UniType> getThrows();

    void setBody(@NotNull UniBlock body);

    @NotNull UniBlock getBody();

    boolean isConstructor();

    boolean isVarArgs();

    @NotNull UniMethodSignature asSignature();

}
