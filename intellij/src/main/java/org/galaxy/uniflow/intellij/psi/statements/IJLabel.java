package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiLabeledStatement;
import org.galaxy.uniflow.api.statements.UniLabel;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJLabel extends IJStatement<PsiLabeledStatement> implements UniLabel {

    public IJLabel(PsiLabeledStatement element) {
        super(element);
    }

    @Override
    public void setLabel(@NotNull String label) {
        PsiIdentifier identifier = IntellijUniflow.getInstance().factory.createIdentifier(label);

        element.getLabelIdentifier().replace(identifier);
    }

    @Override
    public @NotNull String getLabel() {
        return element.getName();
    }

    @Override
    public void setBody(@NotNull UniStatement body) {
        if (element.getStatement() != null)
            element.getStatement().replace(IntellijUnwrapper.unwrap(body));
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiLabeledStatement newLabel = (PsiLabeledStatement) factory.createStatementFromText("label: String a;",
                    null);

            assert newLabel.getStatement() != null;

            newLabel.getLabelIdentifier().replace(element.getLabelIdentifier());
            newLabel.getStatement().replace(IntellijUnwrapper.unwrap(body));
            replace(newLabel);
        }
    }

    @Override
    public @NotNull UniStatement getBody() {
        return UniflowWrapper.wrap(element.getStatement());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.LABELED_STATEMENT;
    }
}
