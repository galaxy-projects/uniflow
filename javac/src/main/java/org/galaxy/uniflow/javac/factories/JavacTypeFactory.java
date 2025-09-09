package org.galaxy.uniflow.javac.factories;

import com.sun.tools.javac.code.BoundKind;
import com.sun.tools.javac.code.Type;
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

    @Override
    public @NotNull UniPrimitiveType asType(@NotNull TypeTag tag) {
        com.sun.tools.javac.code.TypeTag convert = EnumUtils.convert(com.sun.tools.javac.code.TypeTag.class, tag);
        Type.JCPrimitiveType sym = JavacUnwrapper.tagToPrimitiveType(tag);

        return new JavacPrimitiveType(null, new Type.JCPrimitiveType(convert, sym.tsym));
    }

    @Override
    public @NotNull UniArrayType createArrayType(@NotNull UniType elementType) {
        if (!(elementType instanceof JavacType<?, ?>))
            throw new IllegalArgumentException("Invalid element type");
        Type rawElementType = ((JavacType<?, ?>) elementType).getRawType();

        return new JavacArrayType(null,
                new Type.ArrayType(rawElementType, rawElementType.tsym, rawElementType.getMetadata()));
    }

    @Override
    public @NotNull UniWildcardType createWildcardType(@NotNull UniType type, UniWildcardType.@NotNull BoundKind kind) {
        if (!(type instanceof JavacType<?, ?>))
            throw new IllegalArgumentException("Invalid type");
        BoundKind boundKind = EnumUtils.convert(BoundKind.class, kind);
        Type rawType = ((JavacType<?, ?>) type).getRawType();

        return new JavacWildcardType(null,
                new Type.WildcardType(rawType, boundKind, rawType.tsym, rawType.getMetadata()));
    }

    @Override
    public @NotNull UniWildcardType createWildcardType(@NotNull UniType type,
                                                       UniWildcardType.@NotNull BoundKind kind,
                                                       @NotNull UniType bound) {
        if (!(type instanceof JavacType<?, ?>))
            throw new IllegalArgumentException("Invalid type");
        if (!(bound instanceof JavacType<?, ?>))
            throw new IllegalArgumentException("Invalid bound type");
        BoundKind boundKind = EnumUtils.convert(BoundKind.class, kind);
        Type rawType = ((JavacType<?, ?>) type).getRawType();
        Type boundRawType = ((JavacType<?, ?>) bound).getRawType();

        return new JavacWildcardType(null,
                new Type.WildcardType(rawType, boundKind, rawType.tsym,
                        new Type.TypeVar(boundRawType.tsym, boundRawType, boundRawType.getLowerBound(),
                                boundRawType.getMetadata()),
                        rawType.getMetadata()));
    }

    @Override
    public @NotNull UniParameterizedType createParameterizedType(@NotNull UniType elementType,
                                                                 @NotNull List<@NotNull UniType> argumentTypes) {
        if (!(elementType instanceof JavacType<?, ?>))
            throw new IllegalArgumentException("Invalid element type");
        for (UniType type : argumentTypes) {
            if (!(type instanceof JavacType<?, ?>))
                throw new IllegalArgumentException("Invalid type argument: " + type);
        }
        Type rawElementType = ((JavacType<?, ?>) elementType).getRawType();
        com.sun.tools.javac.util.List<Type> types = argumentTypes.stream()
                .map(JavacType.class::cast)
                .map(JavacType::getRawType)
                .collect(com.sun.tools.javac.util.List.collector());

        return new JavacParameterizedType(null,
                new Type.ClassType(rawElementType, types, rawElementType.tsym, rawElementType.getMetadata()));
    }

    @Override
    public @NotNull UniTypeParameter createTypeParameter(@NotNull String name, @NotNull List<@NotNull UniType> bounds) {
        for (UniType type : bounds) {
            if (!(type instanceof JavacType<?, ?>))
                throw new IllegalArgumentException("Invalid bound type: " + type);
        }
        TreeMaker treeMaker = JavacUniflow.getInstance().treeMaker;

        return new JavacTypeParameter(treeMaker.TypeParameter(
                NameUtils.name(name),
                bounds.stream().map(JavacUnwrapper::typeToTree).collect(com.sun.tools.javac.util.List.collector())
        ), 0);
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

        return new JavacTypeParameter(treeMaker.TypeParameter(
                NameUtils.name(name),
                bounds.stream().map(JavacUnwrapper::typeToTree).collect(com.sun.tools.javac.util.List.collector()),
                annotations.stream().map(JavacUnwrapper::unwrap).collect(com.sun.tools.javac.util.List.collector())
        ), 0);
    }
}
