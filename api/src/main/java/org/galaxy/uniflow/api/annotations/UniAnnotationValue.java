package org.galaxy.uniflow.api.annotations;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface UniAnnotationValue extends UniElement {

    @NotNull UniType getType();

    interface Constant extends UniAnnotationValue {

        @NotNull Object getValue();

    }

    interface Class extends UniAnnotationValue {}

    interface Array extends UniAnnotationValue {

        @NotNull UniAnnotationValue @NotNull [] getValues();

    }

    interface Error extends UniAnnotationValue {}

    interface Enum extends UniAnnotationValue {

        @NotNull String getElementName();

    }

    interface Compound extends UniAnnotationValue {

        @NotNull Map<@NotNull String, @NotNull UniAnnotationValue> getValues();

    }
}
