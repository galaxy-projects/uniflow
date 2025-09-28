package org.galaxy.uniflow.framework.assertions;

import org.intellij.lang.annotations.MagicConstant;

import java.lang.reflect.Modifier;
import java.util.function.Consumer;

public interface CompiledClass extends CompiledAnnotationHolder<CompiledClass> {

    CompiledClass assertModifier(@MagicConstant(valuesFromClass = Modifier.class) int modifier);

    CompiledClass assertField(String name);

    CompiledClass assertFields(String... names);

    CompiledClass assertField(String name, Consumer<CompiledField> consumer);

    CompiledClass assertMethod(String name);

    CompiledClass assertMethods(String... names);

    CompiledClass assertMethod(String name, Consumer<CompiledMethod> consumer);

    CompiledClass assertMethod(String name, Class<?>[] parameterTypes);

    CompiledClass assertMethod(String name, Class<?>[] parameterTypes, Consumer<CompiledMethod> consumer);

}
