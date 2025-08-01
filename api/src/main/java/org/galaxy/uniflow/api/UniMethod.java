package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.methods.UniMethodSignature;
import org.galaxy.uniflow.api.parameters.UniParameterList;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeList;
import org.galaxy.uniflow.api.types.UniTypeName;
import org.galaxy.uniflow.api.types.UniTypeParameterHolder;
import org.jetbrains.annotations.NotNull;

public interface UniMethod extends UniMember, UniTypeParameterHolder {

    @NotNull UniTypeName getName();

    @NotNull UniType getReturnType();

    @NotNull UniParameterList getParameters();

    @NotNull UniTypeList getThrows();

    @NotNull UniBlock getBody();

    boolean isConstructor();

    boolean isVarArgs();

    @NotNull UniMethodSignature asSignature();

}
