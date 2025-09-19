package org.galaxy.uniflow.javac.util;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SymbolUtils {

    public static Symbol.VarSymbol findFieldByName(Type type, String name) {
        return findByName(Symbol.VarSymbol.class, type, name);
    }

    public static List<Symbol.VarSymbol> findFieldsByType(Type type, Type fieldType) {
        return findAllBy(Symbol.VarSymbol.class, type, var -> var.type.equals(fieldType));
    }

    public static Symbol.MethodSymbol findMethodByName(Type type, String name) {
        return findByName(Symbol.MethodSymbol.class, type, name);
    }

    public static Symbol.MethodSymbol findMethodBySignature(Type type, String name, Type returnType, Type[] args) {
        for (Symbol symbol : type.tsym.members().getSymbols()) {
            if (!(symbol instanceof Symbol.MethodSymbol)) continue;
            if (!symbol.getSimpleName().contentEquals(name)) continue;
            Symbol.MethodSymbol method = (Symbol.MethodSymbol) symbol;

            if (!method.getReturnType().equals(returnType)) continue;
            if (method.getParameters().size() != args.length) continue;
            int index = 0;

            for (Symbol.VarSymbol param : method.getParameters()) {
                if (!param.type.equals(args[index]))
                    continue;
                index++;
            }
            return method;
        }
        return null;
    }

    private static <T extends Symbol> T findByName(Class<T> clazz, Type type, String name) {
        for (Symbol symbol : type.tsym.members().getSymbols())
            if (clazz.isInstance(symbol) && symbol.getSimpleName().contentEquals(name))
                return clazz.cast(symbol);
        return null;
    }

    private static <T extends Symbol> List<T> findAllBy(Class<T> clazz, Type type, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();

        for (Symbol symbol : type.tsym.members().getSymbols()) {
            if (clazz.isInstance(symbol)) {
                T instance = clazz.cast(symbol);

                if (predicate.test(instance))
                    result.add(instance);
            }
        }
        return result;
    }
}
