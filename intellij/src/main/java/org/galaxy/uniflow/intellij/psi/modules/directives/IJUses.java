package org.galaxy.uniflow.intellij.psi.modules.directives;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiUsesStatement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniUses;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJUses extends IJDirective<PsiUsesStatement> implements UniUses {

    public IJUses(PsiUsesStatement element) {
        super(element);
    }

    @Override
    public void setServiceName(@NotNull UniExpression serviceName) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiUsesStatement newUses = (PsiUsesStatement) factory.createModuleStatementFromText("uses a;", null);

        assert newUses.getClassReference() != null;

        newUses.getClassReference().replace(IntellijUnwrapper.unwrap(serviceName));

        replace(newUses);
    }

    @Override
    public @NotNull UniExpression getServiceName() {
        return UniflowWrapper.wrap(element.getClassReference());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.USES;
    }
}
