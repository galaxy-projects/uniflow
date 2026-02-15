package org.galaxy.uniflow.javac.factories;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.common.factories.CommonMethodFinder;
import org.galaxy.uniflow.javac.signatures.JavacMethodSignature;
import org.galaxy.uniflow.javac.types.JavacClassType;
import org.galaxy.uniflow.javac.types.JavacType;
import org.galaxy.uniflow.javac.util.SymbolUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

import static org.galaxy.uniflow.javac.util.JavacUtils.check;
import static org.galaxy.uniflow.javac.util.JavacUtils.checkList;

public class JavacMethodFinder extends CommonMethodFinder {

    @Override
    @SuppressWarnings("rawtypes")
    public @Nullable UniMethodSignature find(@NotNull UniClassType owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull List<UniType> parameterTypes) {
        JavacClassType javacOwner = check(owner, JavacClassType.class);
        JavacType<?, ?> javacReturnType = check(returnType, JavacType.class);
        Stream<JavacType> javacParameterTypes = checkList(parameterTypes, JavacType.class);

        Symbol.MethodSymbol method = SymbolUtils.findMethodBySignature(
                javacOwner.getRawType(), name, javacReturnType.getRawType(),
                javacParameterTypes.map(JavacType::getRawType).toArray(Type[]::new)
        );
        return method != null ? new JavacMethodSignature(method) : null;
    }
}
