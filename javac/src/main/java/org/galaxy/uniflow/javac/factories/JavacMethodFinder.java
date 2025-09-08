package org.galaxy.uniflow.javac.factories;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import org.galaxy.uniflow.api.factories.UniMethodFinder;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.signatures.JavacMethodSignature;
import org.galaxy.uniflow.javac.types.JavacClassType;
import org.galaxy.uniflow.javac.types.JavacType;
import org.galaxy.uniflow.javac.util.SymbolUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JavacMethodFinder implements UniMethodFinder {

    private final JavacElementFinder parent;

    public JavacMethodFinder(JavacElementFinder parent) {
        this.parent = parent;
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull UniClassType owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull List<UniType> parameterTypes,
                                             @NotNull List<UniType> thrownTypes) {
        if (!(owner instanceof JavacClassType))
            throw new IllegalArgumentException("Invalid owner type");
        if (!(returnType instanceof JavacType<?, ?>))
            throw new IllegalArgumentException("Invalid return type");
        for (UniType parameterType : parameterTypes) {
            if (!(parameterType instanceof JavacType<?, ?>))
                throw new IllegalArgumentException("Invalid parameter type: " + parameterType);
        }
        JavacClassType javacOwner = (JavacClassType) owner;
        JavacType<?, ?> javacReturnType = (JavacType<?, ?>) returnType;

        Symbol.MethodSymbol method = SymbolUtils.findMethodBySignature(
                javacOwner.getRawType(), name, javacReturnType.getRawType(),
                parameterTypes.stream().map(JavacType.class::cast).map(JavacType::getRawType).toArray(Type[]::new)
        );
        return method != null ? new JavacMethodSignature(method) : null;
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull UniClassType owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType) {
        return find(owner, name, returnType, Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull UniClassType owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull UniType[] parameterTypes) {
        return find(owner, name, returnType, Arrays.asList(parameterTypes), Collections.emptyList());
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull UniClassType owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull UniType[] parameterTypes,
                                             @NotNull UniType[] thrownTypes) {
        return find(owner, name, returnType, Arrays.asList(parameterTypes), Arrays.asList(thrownTypes));
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull UniClassType owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull List<UniType> parameterTypes) {
        return find(owner, name, returnType, parameterTypes, Collections.emptyList());
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull Class<?> owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType) {
        return find(parent.findClass(owner), name, returnType, Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull Class<?> owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull UniType[] parameterTypes) {
        return find(parent.findClass(owner), name, returnType, Arrays.asList(parameterTypes), Collections.emptyList());
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull Class<?> owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull UniType[] parameterTypes,
                                             @NotNull UniType[] thrownTypes) {
        return find(parent.findClass(owner), name, returnType,
                Arrays.asList(parameterTypes), Arrays.asList(thrownTypes));
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull Class<?> owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull List<UniType> parameterTypes) {
        return find(parent.findClass(owner), name, returnType, parameterTypes, Collections.emptyList());
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull Class<?> owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull List<UniType> parameterTypes,
                                             @NotNull List<UniType> thrownTypes) {
        return find(parent.findClass(owner), name, returnType, parameterTypes, thrownTypes);
    }
}
