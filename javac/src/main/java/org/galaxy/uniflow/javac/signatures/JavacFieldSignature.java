package org.galaxy.uniflow.javac.signatures;

import com.sun.tools.javac.code.Symbol;
import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacFieldSignature extends JavacSignature<Symbol.VarSymbol> implements UniFieldSignature {

    public JavacFieldSignature(Symbol.@NotNull VarSymbol symbol) {
        super(symbol);
    }

    @Override
    public @NotNull UniClassType getOwner() {
        return UniflowWrapper.symbolToType(symbol.owner);
    }

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.type(symbol);
    }

    @Override
    public @NotNull String getName() {
        return NameUtils.nameToString(symbol.name);
    }
}
