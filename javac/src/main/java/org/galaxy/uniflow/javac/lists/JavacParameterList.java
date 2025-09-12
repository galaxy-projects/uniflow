package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class JavacParameterList extends JavacList<UniVariable, JCTree.JCVariableDecl> implements UniParameterList {

    public JavacParameterList(List<UniVariable> elements,
                              Consumer<com.sun.tools.javac.util.List<JCTree.JCVariableDecl>> setter,
                              Function<UniVariable, JCTree.JCVariableDecl> converter) {
        super(elements, setter, converter);
    }

    public JavacParameterList(com.sun.tools.javac.util.List<JCTree.JCVariableDecl> elements,
                              Consumer<com.sun.tools.javac.util.List<JCTree.JCVariableDecl>> setter,
                              Function<JCTree.JCVariableDecl, UniVariable> inverterConverter,
                              Function<UniVariable, JCTree.JCVariableDecl> converter) {
        super(elements, setter, inverterConverter, converter);
    }

    @Override
    public boolean hasParameter(@NotNull String name) {
        return elements.stream().anyMatch(param -> param.getName().equals(name));
    }

    @Override
    public int getParameterIndex(@NotNull String name) {
        int index = 0;

        for (UniVariable parameter : elements) {
            if (parameter.getName().equals(name))
                return index;
            index++;
        }
        return -1;
    }

    @Override
    public void removeParameter(int index) {
        if (index >= 0 && index < elements.size()) {
            elements.remove(index);
            update();
        }
    }
}
