package org.galaxy.uniflow.intellij.psi.modules.directives;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiRequiresStatement;
import org.galaxy.uniflow.api.modules.directives.UniRequires;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IJRequires extends IJDirective<PsiRequiresStatement> implements UniRequires {

    public IJRequires(PsiRequiresStatement element) {
        super(element);
    }

    @Override
    public void setStatic(boolean isStatic) {}

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public void setTransitive(boolean transitive) {}

    @Override
    public boolean isTransitive() {
        return false;
    }

    @Override
    public void setModuleName(@NotNull String moduleName) {
        if (element.getReferenceElement() != null)
            element.getReferenceElement().replace(IntellijUnwrapper.unwrapModuleReference(moduleName));
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiRequiresStatement newRequires = (PsiRequiresStatement) factory.createModuleStatementFromText(
                    "requires %s;".formatted(moduleName), null);

            replace(newRequires);
        }
    }

    @Override
    public @NotNull String getModuleName() {
        return Objects.requireNonNull(element.getModuleName());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.REQUIRES;
    }
}
