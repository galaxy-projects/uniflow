package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import org.galaxy.uniflow.api.types.UniType;

public class JavacType<T extends Type> implements UniType {

    protected final T type;

    public JavacType(T type) {
        this.type = type;
    }
}
