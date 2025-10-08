package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.expressions.UniLambda;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.lists.JavacParameterList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacLambda extends JavacExpression<JCTree.JCLambda> implements UniLambda {

    public JavacLambda(JCTree.@NotNull JCLambda tree) {
        super(tree);
    }

    @Override
    public @NotNull UniParameterList getParameters() {
        return new JavacParameterList(
                () -> tree.params,
                newList -> tree.params = newList,
                UniflowWrapper::wrapParameter,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniElement getBody() {
        return UniflowWrapper.wrap(tree.body);
    }

    @Override
    public @NotNull BodyKind getBodyKind() {
        return EnumUtils.convert(BodyKind.class, tree.getBodyKind());
    }
}
