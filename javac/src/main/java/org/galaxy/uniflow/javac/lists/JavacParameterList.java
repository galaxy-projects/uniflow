package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;

public class JavacParameterList implements UniParameterList {

    private final JavacList<UniVariable, JCTree.JCVariableDecl> list;

    public JavacParameterList(JavacList<UniVariable, JCTree.JCVariableDecl> list) {
        this.list = list;
    }

    @Override
    public @NotNull UniVariable @NotNull [] getParameters() {
        return list.get();
    }

    @Override
    public boolean hasParameters() {
        return !list.elements.isEmpty();
    }

    @Override
    public boolean hasParameter(@NotNull String name) {
        return list.elements.stream().anyMatch(param -> param.getName().equals(name));
    }

    @Override
    public void addParameter(@NotNull UniVariable parameter) {
        list.addLast(parameter);
    }

    @Override
    public int getParameterIndex(@NotNull String name) {
        int index = 0;

        for (UniVariable parameter : list.elements) {
            if (parameter.getName().equals(name))
                return index;
            index++;
        }
        return -1;
    }

    @Override
    public void removeParameter(int index) {
        if (index >= 0 && index < list.elements.size()) {
            list.elements.remove(index);
            list.update();
        }
    }

    @Override
    public void removeParameter(@NotNull UniVariable parameter) {
        removeParameter(list.elements.indexOf(parameter));
    }
}
