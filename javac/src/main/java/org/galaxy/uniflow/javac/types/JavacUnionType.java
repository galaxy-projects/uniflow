package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniUnionType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class JavacUnionType extends JavacType<Type.UnionClassType> implements UniUnionType {

    private static final Field ALTERNATIVES;

    public JavacUnionType(Type.UnionClassType type) {
        super(type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull UniList<@NotNull UniType> getTypeAlternatives() {
        List<Type> alternatives = null;

        try {
            alternatives = (List<Type>) ALTERNATIVES.get(type);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return new JavacList<>(
                alternatives,
                newList -> {
                    try {
                        ALTERNATIVES.set(type, newList);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                },
                UniUtils::type,
                JavacUtils::javac
        );
    }

    static {
        try {
            ALTERNATIVES = Type.UnionClassType.class.getDeclaredField("alternatives_field");
            ALTERNATIVES.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
