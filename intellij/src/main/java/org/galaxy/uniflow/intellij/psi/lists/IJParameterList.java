package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiType;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.api.statements.UniParameter;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

public class IJParameterList implements UniParameterList {

    private final PsiParameterList list;

    public IJParameterList(PsiParameterList list) {
        this.list = list;
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public @NotNull UniParameter @NotNull [] get() {
        return stream().toArray(UniParameter[]::new);
    }

    @Override
    public @NotNull Stream<UniParameter> stream() {
        return Arrays.stream(list.getParameters()).map(UniflowWrapper::wrap);
    }

    @Override
    public void addFirst(@NotNull UniParameter value) {
        PsiElement first = list.getFirstChild();
        PsiElement unwrap = IntellijUnwrapper.unwrap(value);

        if (first instanceof PsiParameter) {
            list.addBefore(unwrap, first);
        } else {
            list.add(unwrap);
        }
    }

    @Override
    public void addAfter(@NotNull UniParameter value, @NotNull UniParameter target) {
        list.addAfter(IntellijUnwrapper.unwrap(value), IntellijUnwrapper.unwrap(target));
    }

    @Override
    public void addBefore(@NotNull UniParameter value, @NotNull UniParameter target) {
        list.addBefore(IntellijUnwrapper.unwrap(value), IntellijUnwrapper.unwrap(target));
    }

    @Override
    public void addLast(@NotNull UniParameter value) {
        list.add(IntellijUnwrapper.unwrap(value));
    }

    @Override
    public void remove(@NotNull UniParameter value) {
        PsiParameter parameter = IntellijUnwrapper.unwrap(value);

        parameter.delete();
    }

    @Override
    public int getIndex(@NotNull UniParameter element) {
        return list.getParameterIndex(IntellijUnwrapper.unwrap(element));
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
    public @NotNull Iterator<UniParameter> iterator() {
        return stream().iterator();
    }

    @Override
    public boolean hasParameter(@NotNull String name) {
        return stream().anyMatch(parameter -> parameter.getName().equals(name));
    }

    @Override
    public int getParameterIndex(@NotNull String name) {
        int index = 0;

        for (PsiParameter parameter : list.getParameters()) {
            if (parameter.getName().equals(name))
                return index;
            index++;
        }
        return -1;
    }
}
