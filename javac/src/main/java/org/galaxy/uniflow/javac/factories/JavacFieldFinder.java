package org.galaxy.uniflow.javac.factories;

import com.sun.tools.javac.code.Symbol;
import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.common.factories.CommonFieldFinder;
import org.galaxy.uniflow.javac.signatures.JavacFieldSignature;
import org.galaxy.uniflow.javac.types.JavacClassType;
import org.galaxy.uniflow.javac.types.JavacType;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.SymbolUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class JavacFieldFinder extends CommonFieldFinder {
    @Override
    public @Nullable UniFieldSignature find(@NotNull UniClassType owner, @NotNull String name) {
        JavacClassType javacOwner = JavacUtils.check(owner, JavacClassType.class);
        Symbol.VarSymbol field = SymbolUtils.findFieldByName(javacOwner.getRawType(), name);

        return field != null ? new JavacFieldSignature(field) : null;
    }

    @Override
    public @NotNull List<UniFieldSignature> find(@NotNull UniClassType owner, @NotNull UniType fieldType) {
        JavacClassType javacOwner = JavacUtils.check(owner, JavacClassType.class);
        JavacType<?, ?> javacFieldType = JavacUtils.check(fieldType, JavacType.class);
        List<Symbol.VarSymbol> fields = SymbolUtils.findFieldsByType(javacOwner.getRawType(),
                javacFieldType.getRawType());

        return fields.stream().map(JavacFieldSignature::new).collect(Collectors.toList());
    }
}
