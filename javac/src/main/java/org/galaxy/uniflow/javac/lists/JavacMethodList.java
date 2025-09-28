package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.lists.UniMethodList;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.javac.signatures.JavacMethodSignature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class JavacMethodList extends JavacList<UniMethod, JCTree.JCMethodDecl> implements UniMethodList {

    private final JCTree.JCClassDecl owner;

    public JavacMethodList(JCTree.JCClassDecl owner,
                           Supplier<List<JCTree.JCMethodDecl>> elementsSupplier,
                           Consumer<List<JCTree.JCMethodDecl>> setter,
                           Function<JCTree.JCMethodDecl, UniMethod> wrapper,
                           Function<UniMethod, JCTree.JCMethodDecl> unwrapper) {
        super(elementsSupplier, setter, wrapper, unwrapper);
        this.owner = owner;
    }

    @Override
    public void removeMethod(@NotNull UniMethodSignature signature) {
        UniMethod method = getMethod(signature);

        if (method != null)
            remove(method);
    }

    @Override
    public @NotNull UniMethod @NotNull [] getMethods(@NotNull String name) {
        return elementsSupplier.get().stream()
                .filter(method -> method.name.contentEquals(name))
                .map(wrapper)
                .toArray(UniMethod[]::new);
    }

    @Override
    public @Nullable UniMethod getMethod(@NotNull UniMethodSignature signature) {
        return elementsSupplier.get().stream()
                .filter(method -> new JavacMethodSignature(method.sym).equals(signature))
                .findFirst()
                .map(wrapper)
                .orElse(null);
    }

    @Override
    protected void onAdded(JCTree.JCMethodDecl element) {
        if (element.sym != null)
            element.sym.owner = owner.sym;
    }

    public static JavacMethodList from(JCTree.JCClassDecl owner, JavacList<UniMethod, JCTree.JCMethodDecl> methods) {
        return new JavacMethodList(
                owner,
                methods.elementsSupplier,
                methods.setter,
                methods.wrapper,
                methods.unwrapper
        );
    }
}
