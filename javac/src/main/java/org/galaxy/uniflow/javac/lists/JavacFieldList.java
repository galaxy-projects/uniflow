package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class JavacFieldList extends JavacList<UniVariable, JCTree.JCVariableDecl> implements UniFieldList {

    public JavacFieldList(Supplier<List<JCTree.JCVariableDecl>> elementsSupplier,
                          Consumer<List<JCTree.JCVariableDecl>> setter,
                          Function<JCTree.JCVariableDecl, UniVariable> wrapper,
                          Function<UniVariable, JCTree.JCVariableDecl> unwrapper) {
        super(elementsSupplier, setter, wrapper, unwrapper);
    }

    @Override
    public void removeField(@NotNull String name) {
        UniVariable field = getField(name);

        if (field != null)
            remove(field);
    }

    @Override
    public @Nullable UniVariable getField(@NotNull String name) {
        return elementsSupplier.get().stream()
                .filter(field -> field.name.contentEquals(name))
                .findFirst()
                .map(wrapper)
                .orElse(null);
    }

    public static JavacFieldList from(JavacList<UniVariable, JCTree.JCVariableDecl> fields) {
        return new JavacFieldList(
                fields.elementsSupplier,
                fields.setter,
                fields.wrapper,
                fields.unwrapper
        );
    }
}
