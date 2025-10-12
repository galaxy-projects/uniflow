package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiEmptyStatement;
import org.galaxy.uniflow.api.statements.UniEmpty;
import org.jetbrains.annotations.NotNull;

public class IJEmpty extends IJStatement<PsiEmptyStatement> implements UniEmpty {

    public IJEmpty(PsiEmptyStatement element) {
        super(element);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.EMPTY_STATEMENT;
    }
}
