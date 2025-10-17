package org.galaxy.uniflow.framework.assertions;

import java.lang.reflect.Type;

public interface CompiledParameter extends CompiledAnnotationHolder<CompiledParameter> {

    CompiledParameter assertType(Type type);

}
