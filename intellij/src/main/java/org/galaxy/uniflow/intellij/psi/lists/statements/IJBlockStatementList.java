package org.galaxy.uniflow.intellij.psi.lists.statements;

import com.intellij.psi.PsiCodeBlock;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;

import java.util.Arrays;

public class IJBlockStatementList extends IJStatementListBase<PsiCodeBlock> {

    public IJBlockStatementList(PsiCodeBlock codeBlock) {
        super(codeBlock, Arrays.asList(codeBlock.getStatements()));
    }

    @Override
    protected PsiCodeBlock createEmpty() {
        return IntellijUniflow.getInstance().factory.createCodeBlock();
    }
}
