package org.galaxy.uniflow.javac.modules.directives;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniProvides;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacProvides extends JavacElement<JCTree.JCProvides> implements UniProvides {

    public JavacProvides(JCTree.@NotNull JCProvides tree) {
        super(tree);
    }

    @Override
    public void setServiceName(@NotNull UniExpression serviceName) {
        tree.serviceName = JavacUnwrapper.unwrap(serviceName);
    }

    @Override
    public @NotNull UniExpression getServiceName() {
        return UniflowWrapper.wrap(tree.serviceName);
    }

    @Override
    public @NotNull UniList<@NotNull UniExpression> getImplementationNames() {
        return new JavacList<>(
                tree.implNames,
                newList -> tree.implNames = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }
}
