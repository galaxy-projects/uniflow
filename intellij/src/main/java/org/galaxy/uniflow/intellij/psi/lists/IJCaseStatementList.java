package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiSwitchLabelStatement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class IJCaseStatementList implements UniList<UniStatement> {

    private PsiSwitchLabelStatement caseLabel;
    private final List<PsiStatement> statements;

    public IJCaseStatementList(PsiSwitchLabelStatement caseLabel, List<PsiStatement> statements) {
        this.caseLabel = caseLabel;
        this.statements = statements;
    }

    @Override
    public boolean isEmpty() {
        return statements.isEmpty();
    }

    @Override
    public @NotNull UniStatement @NotNull [] get() {
        return stream().toArray(UniStatement[]::new);
    }

    @Override
    public @NotNull Stream<UniStatement> stream() {
        return statements.stream().map(UniflowWrapper::wrap);
    }

    @Override
    public void addFirst(@NotNull UniStatement value) {
        PsiStatement statement = IntellijUnwrapper.unwrap(value);

        statements.add(0, statement);
        if (caseLabel.getFirstChild() != null)
            caseLabel.addBefore(statement, caseLabel.getFirstChild());
        else
            caseLabel.add(statement);
    }

    @Override
    public void addAfter(@NotNull UniStatement value, @NotNull UniStatement target) {
        PsiStatement valueStatement = IntellijUnwrapper.unwrap(value);
        int index = getIndex(target);

        if (index == -1) {
            statements.add(valueStatement);
            caseLabel.add(valueStatement);
        } else {
            PsiStatement targetStatement = IntellijUnwrapper.unwrap(target);

            statements.add(index + 1, valueStatement);
            caseLabel.addAfter(valueStatement, targetStatement);
        }
    }

    @Override
    public void addBefore(@NotNull UniStatement value, @NotNull UniStatement target) {
        PsiStatement valueStatement = IntellijUnwrapper.unwrap(value);
        int index = getIndex(target);

        if (index < 0) {
            statements.add(valueStatement);
            caseLabel.add(valueStatement);
        } else {
            PsiStatement targetStatement = IntellijUnwrapper.unwrap(target);

            statements.add(index, valueStatement);
            caseLabel.addBefore(valueStatement, targetStatement);
        }
    }

    @Override
    public void addLast(@NotNull UniStatement value) {
        PsiStatement statement = IntellijUnwrapper.unwrap(value);

        statements.add(statement);
        caseLabel.add(statement);
    }

    @Override
    public void remove(@NotNull UniStatement value) {
        PsiStatement statement = IntellijUnwrapper.unwrap(value);


        statement.delete();
        statements.remove(statement);
    }

    @Override
    public int getIndex(@NotNull UniStatement element) {
        return statements.indexOf(IntellijUnwrapper.unwrap(element));
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= statements.size())
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + statements.size());
        PsiStatement statement = statements.remove(index);

        statement.delete();
    }

    @Override
    public void clear() {
        statements.clear();
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;

        PsiSwitchLabelStatement newCase;
        if (!caseLabel.isDefaultCase()) {
            newCase = (PsiSwitchLabelStatement) factory.createStatementFromText("case 1:", null);

            assert newCase.getCaseLabelElementList() != null;
            assert caseLabel.getCaseLabelElementList() != null;
            newCase.getCaseLabelElementList().replace(caseLabel.getCaseLabelElementList());
        } else
            newCase = (PsiSwitchLabelStatement) factory.createStatementFromText("default:", null);
        caseLabel = (PsiSwitchLabelStatement) caseLabel.replace(newCase);
    }

    @Override
    public @NotNull Iterator<UniStatement> iterator() {
        return stream().iterator();
    }
}
