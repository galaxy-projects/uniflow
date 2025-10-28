package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.PsiJavaFile;
import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.elements.imports.UniImportBase;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.intellij.psi.lists.IJList;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class IJCompilationUnit extends IJElement<PsiJavaFile> implements UniCompilationUnit {

    public IJCompilationUnit(PsiJavaFile element) {
        super(element);
    }

    @Override
    public @Nullable UniModule getModule() {
        return UniflowWrapper.wrap(element.getModuleDeclaration());
    }

    @Override
    public @Nullable String getPackageName() {
        return element.getPackageName();
    }

    @Override
    public @Nullable UniPackage getPackage() {
        return null;
    }

    @Override
    public @NotNull UniList<@NotNull UniImportBase> getImports() {
        return IJLists.imports(element.getImportList());
    }

    @Override
    public @NotNull UniList<@NotNull UniClass> getClasses() {
        return IJLists.classes(element);
    }

    @Override
    public @NotNull UniList<@NotNull UniElement> getOtherElements() {
        return new IJList<>(
                element,
                file -> Arrays.stream(file.getChildren())
                        .filter(el -> !(el instanceof PsiImportStatement) && !(el instanceof PsiClass))
                        .toArray(PsiElement[]::new),
                UniElement[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.COMPILATION_UNIT;
    }
}
