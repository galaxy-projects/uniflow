package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiCatchSection;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiTryStatement;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniTry;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJTry extends IJStatement<PsiTryStatement> implements UniTry {

    public IJTry(PsiTryStatement element) {
        super(element);
    }

    @Override
    public void setBody(@NotNull UniBlock body) {
        replace(IntellijUnwrapper.unwrap(body), element.getFinallyBlock());
    }

    @Override
    public @NotNull UniBlock getBody() {
        return UniflowWrapper.wrap(element.getTryBlock());
    }

    @Override
    public @NotNull UniList<@NotNull UniCatch> getCatches() {
        return IJLists.catches(element);
    }

    @Override
    public void setFinally(@Nullable UniBlock finallyBody) {
        replace(element.getTryBlock(), IntellijUnwrapper.unwrap(finallyBody));
    }

    @Override
    public @Nullable UniBlock getFinallyBody() {
        return UniflowWrapper.wrap(element.getFinallyBlock());
    }

    @Override
    public @NotNull UniList<@NotNull UniElement> getResources() {
        return IJLists.resources(element.getResourceList());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.TRY;
    }

    private void replace(PsiCodeBlock tryBlock, PsiCodeBlock finallyBlock) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiTryStatement newTry = (PsiTryStatement) factory.createStatementFromText("try {} finally {}", null);

        assert newTry.getTryBlock() != null;
        assert newTry.getFinallyBlock() != null;

        newTry.getTryBlock().replace(tryBlock);
        for (PsiCatchSection catchSection : element.getCatchSections())
            tryBlock.add(catchSection);
        if (finallyBlock != null)
            newTry.getFinallyBlock().replace(finallyBlock);
        else
            newTry.getTryBlock().delete();

        replace(newTry);
    }
}
