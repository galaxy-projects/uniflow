package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.UniTypeBound;
import org.galaxy.uniflow.api.UniWildcard;
import org.galaxy.uniflow.api.types.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UniTypeFactory {

    @NotNull UniPrimitiveType asType(@NotNull TypeTag tag);

    @NotNull UniArrayType createArrayType(@NotNull UniType elementType);

    @NotNull UniParameterizedType createParameterizedType(@NotNull UniType elementType,
                                                          @NotNull List<@NotNull UniType> argumentTypes);

    @NotNull UniTypeParameter createTypeParameter(@NotNull String name,
                                                  @NotNull List<@NotNull UniType> bounds);

    @NotNull UniWildcard createWildcard(@NotNull UniTypeBound bound, @NotNull UniType type);

    @NotNull UniTypeBound createTypeBound(@NotNull UniTypeBound.BoundKind kind);

}
