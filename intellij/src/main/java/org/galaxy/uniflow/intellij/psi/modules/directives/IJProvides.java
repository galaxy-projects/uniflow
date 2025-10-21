package org.galaxy.uniflow.intellij.psi.modules.directives;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiProvidesStatement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniProvides;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJProvides extends IJDirective<PsiProvidesStatement> implements UniProvides {

    public IJProvides(PsiProvidesStatement element) {
        super(element);
    }

    @Override
    public void setServiceName(@NotNull UniExpression serviceName) {
        PsiExpression expression = IntellijUnwrapper.unwrap(serviceName);

        if (element.getInterfaceReference() != null)
            element.getInterfaceReference().replace(expression);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiProvidesStatement newProvides = (PsiProvidesStatement) factory.createModuleStatementFromText(
                    "provides a with b;", null);

            assert newProvides.getInterfaceReference() != null;
            assert newProvides.getImplementationList() != null;
            assert element.getImplementationList() != null;

            newProvides.getInterfaceReference().replace(expression);
            newProvides.getImplementationList().replace(element.getImplementationList());

            replace(newProvides);
        }
    }

    @Override
    public @NotNull UniExpression getServiceName() {
        return UniflowWrapper.wrap(element.getInterfaceReference());
    }

    @Override
    public @NotNull UniList<@NotNull UniExpression> getImplementationNames() {
        return IJLists.referenceList(element.getImplementationList());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.PROVIDES;
    }
}
