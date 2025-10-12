package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiSynchronizedStatement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniSynchronized;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJSynchronized extends IJStatement<PsiSynchronizedStatement> implements UniSynchronized {

    public IJSynchronized(PsiSynchronizedStatement element) {
        super(element);
    }

    @Override
    public void setLock(@NotNull UniExpression lock) {
        replace(IntellijUnwrapper.unwrap(lock), element.getBody());
    }

    @Override
    public @NotNull UniExpression getLock() {
        return UniflowWrapper.wrap(element.getLockExpression());
    }

    @Override
    public void setBody(@NotNull UniBlock body) {
        replace(element.getLockExpression(), IntellijUnwrapper.unwrap(body));
    }

    @Override
    public @NotNull UniBlock getBody() {
        return UniflowWrapper.wrap(element.getBody());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.SYNCHRONIZED;
    }

    private void replace(PsiExpression lock, PsiCodeBlock body) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiSynchronizedStatement newSync = (PsiSynchronizedStatement) factory.createStatementFromText(
                "synchronized (lock) {}", null);

        assert newSync.getLockExpression() != null;
        assert newSync.getBody() != null;

        newSync.getLockExpression().replace(lock);
        newSync.getBody().replace(body);

        replace(newSync);
    }
}
