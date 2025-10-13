package org.galaxy.uniflow.intellij.psi.modules.directives;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiRequiresStatement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniRequires;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

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
    public void setModuleName(@NotNull UniExpression moduleName) {
        PsiExpression expression = IntellijUnwrapper.unwrap(moduleName);

        if (element.getReferenceElement() != null)
            element.getReferenceElement().replace(expression);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiRequiresStatement newRequires = (PsiRequiresStatement) factory.createModuleStatementFromText(
                    "requires a;", null);

            assert element.getReferenceElement() != null;

            element.getReferenceElement().replace(newRequires);

            replace(newRequires);
        }
    }

    @Override
    public @NotNull UniExpression getModuleName() {
        return UniflowWrapper.wrap(element.getReferenceElement());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.REQUIRES;
    }
}
