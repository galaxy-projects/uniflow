package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.types.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UniTypeFactory {

    default @NotNull UniType createType(@NotNull Class<?> clazz) {
        if (clazz.isPrimitive())
            return createPrimitiveType(TypeTag.fromPrimitiveType(clazz));
        else if (clazz.isArray())
            return createArrayType(clazz.getComponentType());
        return createClassType(clazz);
    }

    @NotNull UniPrimitiveType createPrimitiveType(@NotNull TypeTag tag);

    @NotNull UniClassType createClassType(@NotNull Class<?> clazz);

    @NotNull UniClassType createClassType(@NotNull String name);

    @NotNull UniArrayType createArrayType(@NotNull Class<?> elementType);

    @NotNull UniArrayType createArrayType(@NotNull UniType elementType);

    @NotNull UniWildcardType createWildcardType(@NotNull UniType type, @NotNull UniWildcardType.BoundKind kind);

    @NotNull UniWildcardType createWildcardType(@NotNull UniType type,
                                                @NotNull UniWildcardType.BoundKind kind,
                                                @NotNull UniType bound);

    @NotNull UniParameterizedType createParameterizedType(@NotNull UniType elementType,
                                                          @NotNull List<@NotNull UniType> argumentTypes);

    @NotNull UniTypeParameter createTypeParameter(@NotNull String name,
                                                  @NotNull List<@NotNull UniType> bounds);

    @NotNull UniTypeParameter createTypeParameter(@NotNull String name,
                                                  @NotNull List<@NotNull UniType> bounds,
                                                  @NotNull List<@NotNull UniAnnotation> annotations);
}
