package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.statements.UniField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class JavacFieldList extends JavacList<UniField, JCTree.JCVariableDecl> implements UniFieldList {

    private final JCTree.JCClassDecl owner;

    public JavacFieldList(JCTree.JCClassDecl owner,
                          Supplier<List<JCTree.JCVariableDecl>> elementsSupplier,
                          Consumer<List<JCTree.JCVariableDecl>> setter,
                          Function<JCTree.JCVariableDecl, UniField> wrapper,
                          Function<UniField, JCTree.JCVariableDecl> unwrapper) {
        super(elementsSupplier, setter, wrapper, unwrapper);
        this.owner = owner;
    }

    @Override
    public void removeField(@NotNull String name) {
        UniField field = getField(name);

        if (field != null)
            remove(field);
    }

    @Override
    public @Nullable UniField getField(@NotNull String name) {
        return elementsSupplier.get().stream()
                .filter(field -> field.name.contentEquals(name))
                .findFirst()
                .map(wrapper)
                .orElse(null);
    }

    @Override
    protected void onAdded(JCTree.JCVariableDecl element) {
        if (element.sym != null)
            element.sym.owner = owner.sym;
    }

    public static JavacFieldList from(JCTree.JCClassDecl owner, JavacList<UniField, JCTree.JCVariableDecl> fields) {
        return new JavacFieldList(
                owner,
                fields.elementsSupplier,
                fields.setter,
                fields.wrapper,
                fields.unwrapper
        );
    }
}
