package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.UniField;
import org.galaxy.uniflow.api.lists.UniFieldList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

public class JavacFieldList extends JavacList<UniField, JCTree.JCVariableDecl> implements UniFieldList {

    private final Function<String, UniField> fieldFinder;

    public JavacFieldList(List<JCTree.JCVariableDecl> elements,
                          Consumer<List<JCTree.JCVariableDecl>> setter,
                          Function<JCTree.JCVariableDecl, UniField> invertConverter,
                          Function<UniField, JCTree.JCVariableDecl> converter,
                          Function<String, UniField> fieldFinder) {
        super(elements, setter, invertConverter, converter);
        this.fieldFinder = fieldFinder;
    }

    @Override
    public void removeField(@NotNull String name) {
        UniField field = getField(name);

        if (field != null)
            remove(field);
    }

    @Override
    public @Nullable UniField getField(@NotNull String name) {
        return fieldFinder.apply(name);
    }
}
