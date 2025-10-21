package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.PsiClassInitializer;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.jetbrains.annotations.NotNull;

public class IJClassInitializer extends IJElement<PsiClassInitializer> implements UniClassInitializer {

    public IJClassInitializer(PsiClassInitializer element) {
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
        return IJLists.block(element.getBody());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.CLASS_INITIALIZER;
    }
}
