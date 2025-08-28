package org.galaxy.uniflow.javac.signatures;

import com.sun.tools.javac.code.Symbol;
import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacMethodSignature extends JavacSignature<Symbol.MethodSymbol> implements UniMethodSignature {

    public JavacMethodSignature(Symbol.MethodSymbol symbol) {
        super(symbol);
    }

    @Override
    public @NotNull UniClassType getOwner() {
        return UniUtils.symbolToType(symbol.owner);
    }

    @Override
    public @NotNull String getName() {
        return NameUtils.nameToString(symbol.name);
    }

    @Override
    public @NotNull UniType getReturnType() {
        return UniUtils.type(symbol.getReturnType());
    }

    @Override
    public @NotNull UniFieldSignature @NotNull [] getParameters() {
        return symbol.getParameters().map(JavacFieldSignature::new).toArray(new UniFieldSignature[0]);
    }

    @Override
    public @NotNull UniType @NotNull [] getParameterTypes() {
        return symbol.getParameters().map(symbol -> UniUtils.type(symbol.type)).toArray(new UniType[0]);
    }

    @Override
    public @NotNull UniType @NotNull [] getThrownTypes() {
        return symbol.getThrownTypes().map(UniUtils::type).toArray(new UniType[0]);
    }
}
