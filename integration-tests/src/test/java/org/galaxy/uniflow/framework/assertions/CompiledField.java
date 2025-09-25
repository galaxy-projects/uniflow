package org.galaxy.uniflow.framework.assertions;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class CompiledField extends CompiledAnnotationHolder<CompiledField> {

    private final Field field;

    public CompiledField(Field field) {
        super(field);
        this.field = field;
    }

    public CompiledField assertType(Type type) {
        Assertions.assertEquals(type, field.getGenericType(), "Field type mismatch");
        return this;
    }
}
