package org.galaxy.uniflow.javac.factories;

import com.sun.tools.javac.code.BoundKind;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.factories.UniTypeFactory;
import org.galaxy.uniflow.api.types.*;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.annotations.JavacAnnotation;
import org.galaxy.uniflow.javac.types.*;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JavacTypeFactory implements UniTypeFactory {

    private final TreeMaker treeMaker;

    public JavacTypeFactory() {
        treeMaker = JavacUniflow.getInstance().treeMaker;
    }

    @Override
    public @NotNull UniClassType createClassType(@NotNull Class<?> clazz) {
        return createClassType(clazz.getName());
    }

    @Override
    public @NotNull UniClassType createClassType(@NotNull String name) {
        Symbol.ClassSymbol sym = JavacUniflow.getInstance().elements.getTypeElement(name);

        return new JavacClassType(
                treeMaker.Ident(sym),
                (Type.ClassType) sym.type
        );
    }

    @Override
    public @NotNull UniType createVoidType() {
        return new JavacExpressionType<>(
                treeMaker.TypeIdent(com.sun.tools.javac.code.TypeTag.VOID),
                JavacUniflow.getInstance().symtab.voidType
        );
    }

    @Override
    public @NotNull UniPrimitiveType createPrimitiveType(@NotNull TypeTag tag) {
        com.sun.tools.javac.code.TypeTag convert = EnumUtils.convert(com.sun.tools.javac.code.TypeTag.class, tag);
        Type.JCPrimitiveType sym = JavacUnwrapper.tagToPrimitiveType(tag);

        return new JavacPrimitiveType(treeMaker.TypeIdent(convert), new Type.JCPrimitiveType(convert, sym.tsym));
    }

    @Override
    public @NotNull UniArrayType createArrayType(@NotNull Class<?> elementType) {
        return createArrayType(createClassType(elementType));
    }

    @Override
    public @NotNull UniArrayType createArrayType(@NotNull UniType elementType) {
        if (!(elementType instanceof JavacExpressionType<?, ?>))
            throw new IllegalArgumentException("Invalid element type");
        JavacExpressionType<?, ?> javacElementType = (JavacExpressionType<?, ?>) elementType;
        Type rawElementType = javacElementType.getRawType();

        return new JavacArrayType(
                treeMaker.TypeArray(javacElementType.getExpression()),
                new Type.ArrayType(rawElementType, rawElementType.tsym, rawElementType.getMetadata())
        );
    }

    @Override
    public @NotNull UniWildcardType createUnboundedWildcardType() {
        return new JavacWildcardType(
                treeMaker.Wildcard(
                        treeMaker.TypeBoundKind(BoundKind.UNBOUND),
                        null
                ),
                new Type.WildcardType(null, BoundKind.UNBOUND, null)
        );
    }

    @Override
    public @NotNull UniWildcardType createWildcardType(UniWildcardType.@NotNull BoundKind kind,
                                                       @NotNull UniType bound) {
        if (!(bound instanceof JavacType<?, ?>))
            throw new IllegalArgumentException("Invalid bound type");
        BoundKind boundKind = EnumUtils.convert(BoundKind.class, kind);
        JavacType<?, ?> javacBoundType = (JavacType<?, ?>) bound;
        Type boundRawType = ((JavacType<?, ?>) bound).getRawType();

        return new JavacWildcardType(
                treeMaker.Wildcard(
                        treeMaker.TypeBoundKind(boundKind),
                        javacBoundType.getExpression()
                ),
                new Type.WildcardType(boundRawType, boundKind, boundRawType.tsym,
                        new Type.TypeVar(boundRawType.tsym, boundRawType, boundRawType.getLowerBound(),
                                boundRawType.getMetadata()),
                        boundRawType.getMetadata())
        );
    }

    @Override
    public @NotNull UniParameterizedType createParameterizedType(@NotNull UniType elementType,
                                                                 @NotNull List<@NotNull UniType> argumentTypes) {
        if (!(elementType instanceof JavacExpressionType<?, ?>))
            throw new IllegalArgumentException("Invalid element type");
        for (UniType type : argumentTypes) {
            if (!(type instanceof JavacExpressionType<?, ?>))
                throw new IllegalArgumentException("Invalid type argument: " + type);
        }
        JavacExpressionType<?, ?> javacElementType = (JavacExpressionType<?, ?>) elementType;
        Type rawElementType = javacElementType.getRawType();
        com.sun.tools.javac.util.List<Type> types = argumentTypes.stream()
                .map(JavacType.class::cast)
                .map(JavacType::getRawType)
                .collect(com.sun.tools.javac.util.List.collector());

        return new JavacParameterizedType(
                treeMaker.TypeApply(
                        javacElementType.getExpression(),
                        argumentTypes.stream()
                                .map(JavacExpressionType.class::cast)
                                .map(expr -> (JCTree.JCExpression) expr.getExpression())
                                .collect(com.sun.tools.javac.util.List.collector())
                ),
                new Type.ClassType(rawElementType, types, rawElementType.tsym, rawElementType.getMetadata())
        );
    }

    @Override
    public @NotNull UniTypeParameter createTypeParameter(@NotNull String name, @NotNull List<@NotNull UniType> bounds) {
        for (UniType type : bounds) {
            if (!(type instanceof JavacType<?, ?>))
                throw new IllegalArgumentException("Invalid bound type: " + type);
        }
        TreeMaker treeMaker = JavacUniflow.getInstance().treeMaker;

        return new JavacTypeParameter(
                treeMaker.TypeParameter(
                        NameUtils.name(name),
                        bounds.stream().map(JavacUnwrapper::typeToTree)
                                .collect(com.sun.tools.javac.util.List.collector())
                )
        );
    }

    @Override
    public @NotNull UniTypeParameter createTypeParameter(@NotNull String name,
                                                         @NotNull List<@NotNull UniType> bounds,
                                                         @NotNull List<@NotNull UniAnnotation> annotations) {
        for (UniType type : bounds) {
            if (!(type instanceof JavacType<?, ?>))
                throw new IllegalArgumentException("Invalid bound type: " + type);
        }
        for (UniAnnotation annotation : annotations) {
            if (!(annotation instanceof JavacAnnotation))
                throw new IllegalArgumentException("Invalid annotation: " + annotation);
        }
        TreeMaker treeMaker = JavacUniflow.getInstance().treeMaker;

        return new JavacTypeParameter(
                treeMaker.TypeParameter(
                        NameUtils.name(name),
                        bounds.stream().map(JavacUnwrapper::typeToTree)
                                .collect(com.sun.tools.javac.util.List.collector()),
                        annotations.stream().map(JavacUnwrapper::unwrap)
                                .collect(com.sun.tools.javac.util.List.collector())
                )
        );
    }
}
