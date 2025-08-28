package org.galaxy.uniflow.javac.signatures;

import com.sun.tools.javac.code.Symbol;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;

public class JavacOperatorSignature extends JavacSignature<Symbol.OperatorSymbol> implements UniOperatorSignature {

    public JavacOperatorSignature(Symbol.OperatorSymbol symbol) {
        super(symbol);
    }
}
