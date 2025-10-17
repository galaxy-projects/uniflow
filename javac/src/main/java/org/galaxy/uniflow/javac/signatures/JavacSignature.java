package org.galaxy.uniflow.javac.signatures;

import com.sun.tools.javac.code.Symbol;

public class JavacSignature<T extends Symbol> {

    protected final T symbol;

    public JavacSignature(T symbol) {
        this.symbol = symbol;
    }

    public T getSymbol() {
        return symbol;
    }
}
