package org.galaxy.uniflow.intellij.psi.modules.directives;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiJavaModuleReferenceElement;
import com.intellij.psi.PsiPackageAccessibilityStatement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniOpens;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.lists.IJModuleNameList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJOpens extends IJDirective<PsiPackageAccessibilityStatement> implements UniOpens {

    public IJOpens(PsiPackageAccessibilityStatement element) {
        super(element);
    }

    @Override
    public void setPackageName(@NotNull UniExpression packageName) {
        PsiExpression expression = IntellijUnwrapper.unwrap(packageName);

        if (element.getPackageReference() != null)
            element.getPackageReference().replace(expression);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiPackageAccessibilityStatement newOpens = (PsiPackageAccessibilityStatement) factory.createModuleStatementFromText(
                    "opens a to b", null);

            assert newOpens.getPackageReference() != null;

            newOpens.getPackageReference().replace(expression);
            for (PsiJavaModuleReferenceElement moduleName : element.getModuleReferences())
                newOpens.add(moduleName);

            replace(newOpens);
        }
    }

    @Override
    public @NotNull UniExpression getPackageName() {
        return UniflowWrapper.wrap(element.getPackageReference());
    }

    @Override
    public @NotNull UniList<@NotNull String> getModuleNames() {
        return new IJModuleNameList(element);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.OPENS;
    }
}
