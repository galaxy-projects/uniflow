package org.galaxy.uniflow.javac.signatures;

import com.sun.tools.javac.code.Symbol;
import lombok.Getter;

public class JavacSignature<T extends Symbol> {

    @Getter
    protected final T symbol;

    public JavacSignature(T symbol) {
        this.symbol = symbol;
    }
}
