package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiCodeBlock;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.IJElement;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.jetbrains.annotations.NotNull;

public class IJBlock extends IJElement<PsiCodeBlock> implements UniBlock {

    public IJBlock(PsiCodeBlock element) {
        super(element);
    }

    @Override
    public void setStatic(boolean isStatic) {}

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public @NotNull UniList<@NotNull UniStatement> getStatements() {
        return IJLists.block(element);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.BLOCK;
    }
}
