package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiType;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

public class IJParameterList implements UniList<UniVariable> {

    private final PsiParameterList list;

    public IJParameterList(PsiParameterList list) {
        this.list = list;
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public @NotNull UniVariable @NotNull [] get() {
        return stream().toArray(UniVariable[]::new);
    }

    @Override
    public @NotNull Stream<UniVariable> stream() {
        return Arrays.stream(list.getParameters()).map(UniflowWrapper::wrap);
    }

    @Override
    public void addFirst(@NotNull UniVariable value) {
        PsiElement first = list.getFirstChild();
        PsiElement unwrap = IntellijUnwrapper.unwrap(value);

        if (first instanceof PsiParameter) {
            list.addBefore(unwrap, first);
        } else {
            list.add(unwrap);
        }
    }

    @Override
    public void addAfter(@NotNull UniVariable value, @NotNull UniVariable target) {
        list.addAfter(IntellijUnwrapper.unwrap(value), IntellijUnwrapper.unwrap(target));
    }

    @Override
    public void addBefore(@NotNull UniVariable value, @NotNull UniVariable target) {
        list.addBefore(IntellijUnwrapper.unwrap(value), IntellijUnwrapper.unwrap(target));
    }

    @Override
    public void addLast(@NotNull UniVariable value) {
        list.add(IntellijUnwrapper.unwrap(value));
    }

    @Override
    public void remove(@NotNull UniVariable value) {
        PsiParameter parameter = IntellijUnwrapper.unwrap(value);

        parameter.delete();
    }

    @Override
    public int getIndex(@NotNull UniVariable element) {
        return list.getParameterIndex(IntellijUnwrapper.unwrap(element))
    }

    @Override
    public void remove(int index) {
        PsiParameter parameter = list.getParameter(index);

        if (parameter == null)
            throw new IllegalArgumentException("Index " + index + " does not exist");
        parameter.delete();
    }

    @Override
    public void clear() {
        list.replace(IntellijUniflow.getInstance().factory.createParameterList(new String[0], new PsiType[0]));
    }

    @Override
    public @NotNull Iterator<UniVariable> iterator() {
        return stream().iterator();
    }
}
