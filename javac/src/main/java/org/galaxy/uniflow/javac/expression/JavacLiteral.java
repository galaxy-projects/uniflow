package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniLiteral;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.common.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacLiteral extends JavacExpression<JCTree.JCLiteral> implements UniLiteral {

    public JavacLiteral(JCTree.@NotNull JCLiteral tree) {
        super(tree);
    }

    @Override
    public @NotNull TypeTag getTypeTag() {
        return EnumUtils.convert(TypeTag.class, tree.typetag);
    }

    @Override
    public void setValue(@Nullable Object value) {
        tree.value = value;
    }

    @Override
    public @Nullable Object getValue() {
        return tree.value;
    }
}
