package org.galaxy.uniflow.intellij.psi.modules;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiJavaModule;
import com.intellij.psi.PsiJavaModuleReferenceElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.galaxy.uniflow.intellij.psi.IJElement;
import org.galaxy.uniflow.intellij.psi.IJModifiers;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.lists.IJDirectiveList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJModule extends IJElement<PsiJavaModule> implements UniModule {

    public IJModule(PsiJavaModule element) {
        super(element);
    }

    @Override
    public @NotNull ModuleKind getModuleKind() {
        if (element.doNotResolveByDefault())
            return ModuleKind.STRONG;
        return ModuleKind.OPEN;
    }

    @Override
    public void setName(@NotNull UniExpression name) {
        PsiExpression expression = IntellijUnwrapper.unwrap(name);
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiJavaModuleReferenceElement newName = factory.createModuleReferenceFromText(expression.getText(), expression);

        element.getNameIdentifier().replace(newName);
    }

    @Override
    public @NotNull UniExpression getName() {
        return UniflowWrapper.wrap(element.getNameIdentifier().getReference());
    }

    @Override
    public @NotNull UniList<@NotNull UniDirective> getDirectives() {
        return new IJDirectiveList(element);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.MODULE;
    }

    @Override
    public @NotNull UniModifiers getModifiers() {
        return new IJModifiers(element.getModifierList());
    }
}
