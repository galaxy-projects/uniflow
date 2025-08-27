package org.galaxy.uniflow.javac.modules.directives;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniUses;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacUses extends JavacElement<JCTree.JCUses> implements UniUses {

    public JavacUses(JCTree.@NotNull JCUses tree) {
        super(tree);
    }

    @Override
    public void setServiceName(@NotNull UniExpression serviceName) {
        tree.qualid = JavacUtils.javac(serviceName);
    }

    @Override
    public @NotNull UniExpression getServiceName() {
        return UniUtils.uni(tree.qualid);
    }
}
