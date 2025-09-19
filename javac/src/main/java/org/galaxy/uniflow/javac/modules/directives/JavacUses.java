package org.galaxy.uniflow.javac.modules.directives;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniUses;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacUses extends JavacElement<JCTree.JCUses> implements UniUses {

    public JavacUses(JCTree.@NotNull JCUses tree) {
        super(tree);
    }

    @Override
    public void setServiceName(@NotNull UniExpression serviceName) {
        tree.qualid = JavacUnwrapper.unwrap(serviceName);
    }

    @Override
    public @NotNull UniExpression getServiceName() {
        return UniflowWrapper.wrap(tree.qualid);
    }
}
