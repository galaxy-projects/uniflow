package org.galaxy.uniflow.javac;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.factories.UniConstants;
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
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    @Override
    public @Nullable UniModule getModule() {
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
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
    public @NotNull UniList<@NotNull UniImport> getImports() {
        return elements().partial(
                JCTree.JCImport.class::isInstance,
                JCTree.JCImport.class::cast,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<@NotNull UniClass> getClasses() {
        return elements().partial(
                JCTree.JCClassDecl.class::isInstance,
                JCTree.JCClassDecl.class::cast,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<@NotNull UniElement> getOtherElements() {
        return elements().partial(
                e -> !(e instanceof JCTree.JCModuleDecl) && !(e instanceof JCTree.JCPackageDecl) && !(e instanceof JCTree.JCImport) && !(e instanceof JCTree.JCClassDecl),
                Function.identity(),
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    private JavacList<UniElement, JCTree> elements() {
        return new JavacList<>(
                () -> tree.defs,
                newList -> tree.defs = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }
}
