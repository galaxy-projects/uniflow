package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniErroneous;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacErroneous extends JavacExpression<JCTree.JCErroneous> implements UniErroneous {

    public JavacErroneous(JCTree.@NotNull JCErroneous tree) {
        super(tree);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull UniList<UniElement> getErrors() {
        return new JavacList<>((List<JCTree>) tree.errs,
                newList -> tree.errs = newList,
                UniUtils::uni,
                JavacUtils::javac);
    }
}
