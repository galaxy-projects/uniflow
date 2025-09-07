package org.galaxy.uniflow.javac;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class JavacCompilationUnit extends JavacElement<JCTree.JCCompilationUnit> implements UniCompilationUnit {

    public JavacCompilationUnit(JCTree.@NotNull JCCompilationUnit tree) {
        super(tree);
    }

    @Override
    public void setModule(@Nullable UniModule module) {
        JCTree.JCModuleDecl current = tree.getModuleDecl();

        if (current != null)
            tree.defs = tree.defs.stream().filter(e -> e != current).collect(List.collector());
        tree.defs = tree.defs.append(JavacUnwrapper.unwrap(module));
    }

    @Override
    public @Nullable UniModule getModule() {
        JCTree.JCModuleDecl module = tree.getModuleDecl();

        return module != null ? UniflowWrapper.wrap(module) : null;
    }

    @Override
    public @Nullable String getPackageName() {
        return NameUtils.nameToString(tree.packge.name);
    }

    @Override
    public @Nullable UniPackage getPackage() {
        if (!tree.defs.isEmpty() && tree.defs.head.hasTag(JCTree.Tag.PACKAGEDEF))
            return UniflowWrapper.wrap((JCTree.JCPackageDecl) tree.defs.head);
        return null;
    }

    @Override
    public @NotNull UniList<UniImport> getImports() {
        return elements().partial(
                UniImport.class::isInstance,
                UniImport.class::cast,
                Function.identity(),
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<@NotNull UniClass> getClasses() {
        return elements().partial(
                UniClass.class::isInstance,
                UniClass.class::cast,
                Function.identity(),
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<UniElement> getOtherElements() {
        return elements().partial(
                e -> !(e instanceof UniModule) && !(e instanceof UniPackage) && !(e instanceof UniImport) && !(e instanceof UniClass),
                Function.identity(),
                Function.identity(),
                JavacUnwrapper::unwrap
        );
    }

    private JavacList<UniElement, JCTree> elements() {
        return new JavacList<>(
                tree.defs,
                newList -> tree.defs = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }
}
