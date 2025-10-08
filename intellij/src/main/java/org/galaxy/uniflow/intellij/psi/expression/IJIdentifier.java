package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiIdentifier;
import org.galaxy.uniflow.api.expressions.UniIdentifier;
import org.galaxy.uniflow.intellij.psi.IJElement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.jetbrains.annotations.NotNull;

public class IJIdentifier extends IJElement<PsiIdentifier> implements UniIdentifier {

    public IJIdentifier(PsiIdentifier element) {
        super(element);
    }

    @Override
    public void setName(@NotNull String name) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;

        replace(factory.createIdentifier(name));
    }

    @Override
    public @NotNull String getName() {
        return element.getText();
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.IDENTIFIER;
    }
}
