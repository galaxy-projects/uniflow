package org.galaxy.uniflow.intellij.psi.elements.imports;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiImportStaticStatement;
import org.galaxy.uniflow.api.elements.imports.UniStaticImport;
import org.galaxy.uniflow.intellij.psi.IJElement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IJStaticImport extends IJElement<PsiImportStaticStatement> implements UniStaticImport {

    public IJStaticImport(PsiImportStaticStatement element) {
        super(element);
    }

    @Override
    public void setTarget(@NotNull String className, @NotNull String qualifiedElement) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiImportStaticStatement newImport = factory.createImportStaticStatementFromText(className, qualifiedElement);

        replace(newImport);
    }

    @Override
    public @NotNull String getTargetClass() {
        return Objects.requireNonNull(element.getImportReference()).getQualifiedName();
    }

    @Override
    public @NotNull String getTargetElement() {
        return Objects.requireNonNull(element.getReferenceName());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.IMPORT_STATIC;
    }
}
