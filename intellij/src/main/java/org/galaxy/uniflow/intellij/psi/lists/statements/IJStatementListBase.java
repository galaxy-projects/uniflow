package org.galaxy.uniflow.intellij.psi.lists.statements;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiStatement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public abstract class IJStatementListBase<T extends PsiElement> implements UniList<UniStatement> {

    protected T parent;
    private final List<PsiStatement> statements;

    public IJStatementListBase(T parent, List<PsiStatement> statements) {
        this.parent = parent;
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
        if (parent.getFirstChild() != null)
            parent.addBefore(statement, parent.getFirstChild());
        else
            parent.add(statement);
    }

    @Override
    public void addAfter(@NotNull UniStatement value, @NotNull UniStatement target) {
        PsiStatement valueStatement = IntellijUnwrapper.unwrap(value);
        int index = getIndex(target);

        if (index == -1) {
            statements.add(valueStatement);
            parent.add(valueStatement);
        } else {
            PsiStatement targetStatement = IntellijUnwrapper.unwrap(target);

            statements.add(index + 1, valueStatement);
            parent.addAfter(valueStatement, targetStatement);
        }
    }

    @Override
    public void addBefore(@NotNull UniStatement value, @NotNull UniStatement target) {
        PsiStatement valueStatement = IntellijUnwrapper.unwrap(value);
        int index = getIndex(target);

        if (index < 0) {
            statements.add(valueStatement);
            parent.add(valueStatement);
        } else {
            PsiStatement targetStatement = IntellijUnwrapper.unwrap(target);

            statements.add(index, valueStatement);
            parent.addBefore(valueStatement, targetStatement);
        }
    }

    @Override
    public void addLast(@NotNull UniStatement value) {
        PsiStatement statement = IntellijUnwrapper.unwrap(value);

        statements.add(statement);
        parent.add(statement);
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
    @SuppressWarnings("unchecked")
    public void clear() {
        statements.clear();
        parent = (T) parent.replace(createEmpty());
    }

    @Override
    public @NotNull Iterator<UniStatement> iterator() {
        return stream().iterator();
    }

    protected abstract T createEmpty();
}