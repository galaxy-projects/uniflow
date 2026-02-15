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
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacUnionType extends JavacExpressionType<JCTree.JCTypeUnion, Type.UnionClassType>
        implements UniUnionType {

    private static final ReflectField ALTERNATIVES;

    public JavacUnionType(JCTree.JCTypeUnion expression, Type.UnionClassType type) {
        super(expression, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull UniList<@NotNull UniType> getTypeAlternatives() {
        return new JavacList<>(
                () -> (List<Type>) ALTERNATIVES.get(type),
                newList -> ALTERNATIVES.set(type, newList),
                UniflowWrapper::type,
                JavacUnwrapper::unwrap
        );
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Type.UnionClassType.class);
            ALTERNATIVES = type.field("alternatives_field");
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
