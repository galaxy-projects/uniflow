package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniUnionType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class JavacUnionType extends JavacExpressionType<JCTree.JCTypeUnion, Type.UnionClassType>
        implements UniUnionType {

    private static final Field ALTERNATIVES;

    public JavacUnionType(JCTree.JCTypeUnion expression, Type.UnionClassType type) {
        super(expression, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull UniList<@NotNull UniType> getTypeAlternatives() {
        List<Type> alternatives;

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
                UniflowWrapper::type,
                JavacUnwrapper::unwrap
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
