package org.galaxy.uniflow.javac.modules;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.JavacModifiers;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacModule extends JavacElement<JCTree.JCModuleDecl> implements UniModule {

    public JavacModule(JCTree.@NotNull JCModuleDecl tree) {
        super(tree);
    }

    @Override
    public @NotNull UniModifiers getModifiers() {
        return new JavacModifiers(tree.mods);
    }

    @Override
    public @NotNull ModuleKind getModuleKind() {
        return EnumUtils.convert(ModuleKind.class, tree.getModuleType());
    }

    @Override
    public void setName(@NotNull UniExpression name) {
        tree.qualId = JavacUnwrapper.unwrap(name);
    }

    @Override
    public @NotNull UniExpression getName() {
        return UniflowWrapper.wrap(tree.qualId);
    }

    @Override
    public @NotNull UniList<UniDirective> getDirectives() {
        return new JavacList<>(
                tree.directives,
                newList -> tree.directives = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }
}
