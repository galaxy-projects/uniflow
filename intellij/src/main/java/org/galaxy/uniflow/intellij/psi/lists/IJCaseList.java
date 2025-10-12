package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class IJCaseList implements UniList<UniCase> {

    private final PsiSwitchBlock switchBlock;

    public IJCaseList(PsiSwitchBlock switchBlock) {
        this.switchBlock = switchBlock;
    }

    @Override
    public boolean isEmpty() {
        return switchBlock.getBody() == null || switchBlock.getBody().getStatements().length == 0;
    }

    @Override
    public @NotNull UniCase @NotNull [] get() {
        return stream().toArray(UniCase[]::new);
    }

    @Override
    public @NotNull Stream<UniCase> stream() {
        return list().stream();
    }

    @Override
    public void addFirst(@NotNull UniCase value) {
        PsiSwitchLabelStatementBase caseLabel = IntellijUnwrapper.unwrap(value);

        if (switchBlock.getFirstChild() == null)
            switchBlock.add(caseLabel);
        else
            switchBlock.addBefore(caseLabel, switchBlock.getFirstChild());
    }

    @Override
    public void addAfter(@NotNull UniCase value, @NotNull UniCase target) {
        PsiSwitchLabelStatementBase caseLabelValue = IntellijUnwrapper.unwrap(value);
        PsiSwitchLabelStatementBase caseLabelTarget = IntellijUnwrapper.unwrap(target);

        switchBlock.addAfter(caseLabelValue, caseLabelTarget);
    }

    @Override
    public void addBefore(@NotNull UniCase value, @NotNull UniCase target) {
        PsiSwitchLabelStatementBase caseLabelValue = IntellijUnwrapper.unwrap(value);
        PsiSwitchLabelStatementBase caseLabelTarget = IntellijUnwrapper.unwrap(target);

        switchBlock.addBefore(caseLabelValue, caseLabelTarget);
    }

    @Override
    public void addLast(@NotNull UniCase value) {
        switchBlock.add(IntellijUnwrapper.unwrap(value));
    }

    @Override
    public void remove(@NotNull UniCase value) {
        IntellijUnwrapper.unwrap(value).delete();
    }

    @Override
    public int getIndex(@NotNull UniCase element) {
        return list().indexOf(element);
    }

    @Override
    public void remove(int index) {
        List<UniCase> list = list();

        if (index < 0 || index >= list.size())
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + list.size());
        IntellijUnwrapper.unwrap(list.get(index)).delete();
    }

    @Override
    public void clear() {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;

        if (switchBlock.getBody() != null)
            switchBlock.getBody().replace(factory.createCodeBlock());
        else {
            PsiSwitchStatement newSwitch = (PsiSwitchStatement) factory.createStatementFromText("switch (a) {}", null);

            assert newSwitch.getBody() != null;
            switchBlock.replace(newSwitch.getBody());
            switchBlock.replace(newSwitch);
        }
    }

    @Override
    public @NotNull Iterator<UniCase> iterator() {
        return list().iterator();
    }

    private List<UniCase> list() {
        PsiCodeBlock body = switchBlock.getBody();

        if (body == null) return Collections.emptyList();

        List<UniCase> cases = new ArrayList<>();
        List<PsiStatement> statements = null;
        PsiSwitchLabelStatementBase currentCase;

        for (PsiStatement statement : body.getStatements()) {
            if (statement instanceof PsiSwitchLabelStatementBase) {
                statements = new ArrayList<>();
                currentCase = (PsiSwitchLabelStatementBase) statement;
                cases.add(UniflowWrapper.wrap(currentCase, statements));
            } else if (statements != null) {
                statements.add(statement);
            } else
                throw new IllegalStateException("No case before statement " + statement.getText());
        }

        return cases;
    }
}
