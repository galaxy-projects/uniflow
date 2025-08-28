package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameterHolder;
import org.jetbrains.annotations.NotNull;

public interface UniMethod extends UniMember, UniTypeParameterHolder {

    @NotNull String getName();

    @NotNull UniType getReturnType();

    @NotNull UniParameterList getParameters();

    @NotNull UniList<@NotNull UniType> getThrows();

    @NotNull UniBlock getBody();

    boolean isConstructor();

    boolean isVarArgs();

    @NotNull UniMethodSignature asSignature();

}
