package org.galaxy.uniflow.intellij.psi.elements.imports;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiImportModuleStatement;
import org.galaxy.uniflow.api.elements.imports.UniModuleImport;
import org.galaxy.uniflow.intellij.psi.IJElement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class IJModuleImport extends IJElement<PsiImportModuleStatement> implements UniModuleImport {

    public IJModuleImport(PsiImportModuleStatement element) {
        super(element);
    }

    @Override
    public void setModuleName(@NotNull String moduleName) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiImportModuleStatement newImport = factory.createImportModuleStatementFromText(moduleName);

        replace(newImport);
    }

    @Override
    public @NotNull String getModuleName() {
        return Objects.requireNonNull(element.getReferenceName());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.IMPORT_MODULE;
    }
}
