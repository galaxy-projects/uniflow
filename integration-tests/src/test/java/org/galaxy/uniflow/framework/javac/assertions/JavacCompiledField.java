package org.galaxy.uniflow.framework.javac.assertions;

import org.galaxy.uniflow.framework.assertions.CompiledField;
import org.intellij.lang.annotations.MagicConstant;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class JavacCompiledField extends JavacCompiledAnnotationHolder<CompiledField>
        implements CompiledField {

    private final Field field;

    public JavacCompiledField(Field field) {
        super(field);
        this.field = field;
    }

    @Override
    public CompiledField assertModifier(@MagicConstant(valuesFromClass = Modifier.class) int modifier) {
        if ((field.getModifiers() & modifier) == 0)
            Assertions.fail(field.getName() + " does not have modifier: " + modifier);
        return this;
    }

    @Override
    public JavacCompiledField assertType(Type type) {
        Assertions.assertEquals(type, field.getGenericType(), "Field type mismatch");
        return this;
    }
}
