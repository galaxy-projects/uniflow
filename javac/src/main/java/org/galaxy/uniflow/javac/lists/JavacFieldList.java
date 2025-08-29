package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

public class JavacFieldList extends JavacList<UniVariable, JCTree.JCVariableDecl> implements UniFieldList {

    public JavacFieldList(java.util.List<UniVariable> elements,
                          Consumer<List<JCTree.JCVariableDecl>> setter,
                          Function<UniVariable, JCTree.JCVariableDecl> converter) {
        super(elements, setter, converter);
    }

    @Override
    public void removeField(@NotNull String name) {
        UniVariable field = getField(name);

        if (field != null)
            remove(field);
    }

    @Override
    public @Nullable UniVariable getField(@NotNull String name) {
        return elements.stream().filter(field -> field.getName().equals(name)).findFirst().orElse(null);
    }

    public static JavacFieldList from(JavacList<UniVariable, JCTree.JCVariableDecl> fields) {
        return new JavacFieldList(
                fields.elements,
                fields.setter,
                fields.converter
        );
    }
}
