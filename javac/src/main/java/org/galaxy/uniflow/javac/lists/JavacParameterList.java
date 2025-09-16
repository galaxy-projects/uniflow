package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class JavacParameterList extends JavacList<UniVariable, JCTree.JCVariableDecl> implements UniParameterList {

    public JavacParameterList(
            Supplier<List<JCTree.JCVariableDecl>> elementsSupplier,
            Consumer<List<JCTree.JCVariableDecl>> setter,
            Function<JCTree.JCVariableDecl, UniVariable> wrapper,
            Function<UniVariable, JCTree.JCVariableDecl> unwrapper) {
        super(elementsSupplier, setter, wrapper, unwrapper);
    }

    @Override
    public boolean hasParameter(@NotNull String name) {
        return elementsSupplier.get().stream()
                .anyMatch(var -> var.name.contentEquals(name));
    }

    @Override
    public int getParameterIndex(@NotNull String name) {
        int index = 0;

        for (JCTree.JCVariableDecl var : elementsSupplier.get()) {
            if (var.name.contentEquals(name))
                return index;
            index++;
        }
        return -1;
    }
}
