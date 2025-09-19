package org.galaxy.uniflow.javac.modules.directives;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniExports;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacExports extends JavacElement<JCTree.JCExports> implements UniExports {

    public JavacExports(JCTree.@NotNull JCExports tree) {
        super(tree);
    }

    @Override
    public void setPackageName(@NotNull UniExpression packageName) {
        tree.qualid = JavacUnwrapper.unwrap(packageName);
    }

    @Override
    public @NotNull UniExpression getPackageName() {
        return UniflowWrapper.wrap(tree.qualid);
    }

    @Override
    public @NotNull UniList<@NotNull UniExpression> getModuleNames() {
        return new JavacList<>(
                () -> tree.moduleNames,
                newList -> tree.moduleNames = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }
}
