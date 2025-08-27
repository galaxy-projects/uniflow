package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniTry;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacTry extends JavacElement<JCTree.JCTry> implements UniTry {

    public JavacTry(JCTree.@NotNull JCTry tree) {
        super(tree);
    }

    @Override
    public void setBody(@NotNull UniBlock body) {
        tree.body = JavacUtils.javac(body);
    }

    @Override
    public @NotNull UniBlock getBody() {
        return UniUtils.uni(tree.body);
    }

    @Override
    public @NotNull UniList<UniCatch> getCatches() {
        return new JavacList<>(
                tree.catchers,
                newList -> tree.catchers = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public void setFinally(@Nullable UniBlock finallyBody) {
        tree.finalizer = JavacUtils.javac(finallyBody);
    }

    @Override
    public @Nullable UniBlock getFinallyBody() {
        return UniUtils.uni(tree.finalizer);
    }

    @Override
    public @NotNull UniList<UniElement> getResources() {
        return new JavacList<>(
                tree.resources,
                newList -> tree.resources = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }
}
