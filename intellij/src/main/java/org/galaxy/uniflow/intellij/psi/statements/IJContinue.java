package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiContinueStatement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiIdentifier;
import org.galaxy.uniflow.api.statements.UniContinue;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJContinue extends IJStatement<PsiContinueStatement> implements UniContinue {

    public IJContinue(PsiContinueStatement element) {
        super(element);
    }

    @Override
    public void setLabel(@Nullable String label) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;

        if (label != null) {
            if (element.getLabelIdentifier() != null)
                element.getLabelIdentifier().replace(factory.createIdentifier(label));
            else {
                PsiContinueStatement newContinue = (PsiContinueStatement) factory.createStatementFromText(
                        "continue " + label + ";", null);

                assert newContinue.getLabelIdentifier() != null;

                replace(newContinue);
            }
        } else if (element.getLabelIdentifier() != null) {
            element.getLabelIdentifier().delete();
        }
    }

    @Override
    public @Nullable String getLabel() {
        PsiIdentifier label = element.getLabelIdentifier();

        return label != null ? label.getText() : null;
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.CONTINUE;
    }
}
