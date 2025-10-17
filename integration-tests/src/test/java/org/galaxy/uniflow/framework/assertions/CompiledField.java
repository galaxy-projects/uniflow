package org.galaxy.uniflow.framework.assertions;

import org.intellij.lang.annotations.MagicConstant;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public interface CompiledField extends CompiledAnnotationHolder<CompiledField> {

    CompiledField assertModifier(@MagicConstant(valuesFromClass = Modifier.class) int modifier);

    CompiledField assertType(Type type);

}
