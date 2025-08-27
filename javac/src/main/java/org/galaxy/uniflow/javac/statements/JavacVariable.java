package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacVariable extends JavacElement<JCTree.JCVariableDecl> implements UniVariable {

    public JavacVariable(JCTree.@NotNull JCVariableDecl tree) {
        super(tree);
    }

    @Override
    public @NotNull String getName() {
        return NameUtils.nameToString(tree.name);
    }

    @Override
    public @NotNull UniType getType() {
        return UniUtils.typeFromTree(tree.vartype);
    }

    @Override
    public void setInitializer(@Nullable UniExpression expression) {
        tree.init = JavacUtils.javac(expression);
    }

    @Override
    public @Nullable UniExpression getInitializer() {
        return UniUtils.uni(tree.init);
    }

    @Override
    public @NotNull UniModifier @NotNull [] getModifiers() {
        return tree.mods.getFlags().stream()
                .map(flag -> EnumUtils.convert(UniModifier.class, flag))
                .toArray(UniModifier[]::new);
    }

    @Override
    public boolean hasModifier(@NotNull UniModifier modifier) {
        return modifier.hasModifier(tree.mods.flags);
    }

    @Override
    public void addModifier(@NotNull UniModifier modifier) {
        tree.mods.flags |= modifier.getMask();
    }

    @Override
    public void removeModifier(@NotNull UniModifier modifier) {
        tree.mods.flags &= ~modifier.getMask();
    }
}
