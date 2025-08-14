package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public interface UniAnnotationFactory {

    @NotNull UniAnnotationValue createValueConstant(@NotNull UniType type, @NotNull Object value);

    @NotNull UniAnnotationValue createValueClass(@NotNull UniType type);

    @NotNull UniAnnotationValue createValueArray(@NotNull UniAnnotationValue @NotNull [] values);

    @NotNull UniAnnotationValue createValueError(@NotNull UniType type);

    @NotNull UniAnnotationValue createValueEnum(@NotNull UniType type, @NotNull String elementName);

    @NotNull <E extends Enum<E>> UniAnnotationValue createValueEnum(@NotNull E element);

    @NotNull UniAnnotationValue createValueCompound(
            @NotNull Map<@NotNull String, @NotNull UniAnnotationValue> values);

    default @NotNull UniAnnotationValue createValueCompound(@NotNull String name,
                                                            @NotNull UniAnnotationValue value) {
        HashMap<String, UniAnnotationValue> values = new HashMap<>();

        values.put(name, value);
        return createValueCompound(values);
    }
}
