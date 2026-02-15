package org.galaxy.uniflow.intellij.psi.elements.imports;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiImportStatement;
import org.galaxy.uniflow.api.elements.imports.UniImport;
import org.galaxy.uniflow.intellij.psi.IJElement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IJImport extends IJElement<PsiImportStatement> implements UniImport {

    public IJImport(PsiImportStatement element) {
        super(element);
    }

    @Override
    public void setClasses(@NotNull String qualifiedElement) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiImportStatement newImport = factory.createImportStatementOnDemand(qualifiedElement);

        replace(newImport);
    }

    @Override
    public @NotNull String getClasses() {
        return Objects.requireNonNull(element.getQualifiedName());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.IMPORT;
    }
}
