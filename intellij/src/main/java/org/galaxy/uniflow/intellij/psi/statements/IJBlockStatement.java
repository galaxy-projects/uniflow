package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiBlockStatement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.jetbrains.annotations.NotNull;

public class IJBlockStatement extends IJStatement<PsiBlockStatement> implements UniBlock {

    public IJBlockStatement(PsiBlockStatement element) {
        super(element);
    }

    @Override
    public void setStatic(boolean isStatic) {}

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public @NotNull UniList<UniStatement> getStatements() {
        return IJLists.block(element.getCodeBlock());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.BLOCK;
    }
}
