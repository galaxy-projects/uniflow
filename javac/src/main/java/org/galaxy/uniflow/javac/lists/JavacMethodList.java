package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.lists.UniMethodList;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

public class JavacMethodList extends JavacList<UniMethod, JCTree.JCMethodDecl> implements UniMethodList {

    private final Function<UniMethodSignature, UniMethod> methodFinder;

    public JavacMethodList(List<JCTree.JCMethodDecl> elements,
                           Consumer<List<JCTree.JCMethodDecl>> setter,
                           Function<JCTree.JCMethodDecl, UniMethod> invertConverter,
                           Function<UniMethod, JCTree.JCMethodDecl> converter,
                           Function<UniMethodSignature, UniMethod> methodFinder) {
        super(elements, setter, invertConverter, converter);
        this.methodFinder = methodFinder;
    }

    @Override
    public void removeMethod(@NotNull UniMethodSignature signature) {
        UniMethod method = getMethod(signature);

        if (method != null)
            remove(method);
    }

    @Override
    public @NotNull UniMethod @NotNull [] getMethods(@NotNull String name) {
        return elements.stream().filter(method -> method.getName().equals(name)).toArray(UniMethod[]::new);
    }

    @Override
    public @Nullable UniMethod getMethod(@NotNull UniMethodSignature signature) {
        return methodFinder.apply(signature);
    }
}
