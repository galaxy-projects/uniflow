package org.galaxy.uniflow.javac.factories;

import com.sun.tools.javac.code.Symbol;
import org.galaxy.uniflow.api.factories.UniFieldFinder;
import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.signatures.JavacFieldSignature;
import org.galaxy.uniflow.javac.types.JavacClassType;
import org.galaxy.uniflow.javac.types.JavacType;
import org.galaxy.uniflow.javac.util.SymbolUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class JavacFieldFinder implements UniFieldFinder {

    private final JavacElementFinder parent;

    public JavacFieldFinder(JavacElementFinder parent) {
        this.parent = parent;
    }

    @Override
    public @Nullable UniFieldSignature find(@NotNull UniClassType owner, @NotNull String name) {
        if (!(owner instanceof JavacClassType))
            throw new IllegalArgumentException("Invalid owner type");
        JavacClassType javacOwner = (JavacClassType) owner;
        Symbol.VarSymbol field = SymbolUtils.findFieldByName(javacOwner.getRawType(), name);

        return field != null ? new JavacFieldSignature(field) : null;
    }

    @Override
    public @Nullable UniFieldSignature find(@NotNull Class<?> owner, @NotNull String name) {
        return find(parent.findClass(owner), name);
    }

    @Override
    public @NotNull List<UniFieldSignature> find(@NotNull UniClassType owner, @NotNull UniType fieldType) {
        if (!(owner instanceof JavacClassType))
            throw new IllegalArgumentException("Invalid owner type");
        if (!(fieldType instanceof JavacType<?, ?>))
            throw new IllegalArgumentException("Invalid field type");
        JavacClassType javacOwner = (JavacClassType) owner;
        JavacType<?, ?> javacFieldType = (JavacType<?, ?>) fieldType;
        List<Symbol.VarSymbol> fields = SymbolUtils.findFieldsByType(javacOwner.getRawType(),
                javacFieldType.getRawType());

        return fields.stream().map(JavacFieldSignature::new).collect(Collectors.toList());
    }

    @Override
    public @NotNull List<UniFieldSignature> find(@NotNull Class<?> owner, @NotNull UniType fieldType) {
        return find(parent.findClass(owner), fieldType);
    }

    @Override
    public @NotNull List<UniFieldSignature> find(@NotNull UniClassType owner, @NotNull Class<?> fieldType) {
        return find(owner, parent.findClass(fieldType));
    }

    @Override
    public @NotNull List<UniFieldSignature> find(@NotNull Class<?> owner, @NotNull Class<?> fieldType) {
        return find(parent.findClass(owner), parent.findClass(fieldType));
    }
}
