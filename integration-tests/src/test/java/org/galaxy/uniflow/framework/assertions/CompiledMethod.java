package org.galaxy.uniflow.framework.assertions;

import org.intellij.lang.annotations.MagicConstant;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.function.Consumer;

public interface CompiledMethod extends CompiledAnnotationHolder<CompiledMethod> {

    CompiledMethod assertModifier(@MagicConstant(valuesFromClass = Modifier.class) int modifier);

    CompiledMethod assertReturnType(Type type);

    CompiledMethod assertParameterCount(int count);

    CompiledMethod assertParameter(int index);

    CompiledMethod assertParameter(int index, Consumer<CompiledParameter> consumer);

    CompiledMethod assertExecute(Object expected, Object... args);

    CompiledMethod assertExecute(Object expected, Object instance, Object... args);

}
