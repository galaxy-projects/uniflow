package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiImportModuleStatement;
import com.intellij.psi.PsiJavaParserFacade;
import org.galaxy.uniflow.api.elements.imports.UniModuleImport;
import org.galaxy.uniflow.api.factories.UniJdk25ElementFactory;
import org.galaxy.uniflow.intellij.psi.elements.imports.IJModuleImport;
import org.jetbrains.annotations.NotNull;

public class IntellijJava25ElementFactory extends IntellijJava21ElementFactory implements UniJdk25ElementFactory {

    public IntellijJava25ElementFactory(PsiElementFactory factory, PsiJavaParserFacade parser, PsiFileFactory files) {
        super(factory, parser, files);
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public @NotNull UniModuleImport createModuleImport(@NotNull String moduleName) {
        PsiImportModuleStatement importModule = factory.createImportModuleStatementFromText(
                "import module %s;".formatted(moduleName));

        return new IJModuleImport(importModule);
    }
}
