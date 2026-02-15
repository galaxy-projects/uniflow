package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiBreakStatement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiIdentifier;
import org.galaxy.uniflow.api.statements.UniBreak;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJBreak extends IJStatement<PsiBreakStatement> implements UniBreak {

    public IJBreak(PsiBreakStatement element) {
        super(element);
    }

    @Override
    public void setLabel(@Nullable String label) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;

        if (label != null) {
            if (element.getLabelIdentifier() != null)
                element.getLabelIdentifier().replace(factory.createIdentifier(label));
            else {
                PsiBreakStatement newBreak = (PsiBreakStatement) factory.createStatementFromText("break " + label + ";",
                        null);

                assert newBreak.getLabelIdentifier() != null;

                replace(newBreak);
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
        return Kind.BREAK;
    }
}
