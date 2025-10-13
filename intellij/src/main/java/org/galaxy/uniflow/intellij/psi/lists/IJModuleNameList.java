package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiJavaModuleReferenceElement;
import com.intellij.psi.PsiPackageAccessibilityStatement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class IJModuleNameList implements UniList<UniExpression> {

    private final PsiPackageAccessibilityStatement element;

    public IJModuleNameList(PsiPackageAccessibilityStatement element) {
        this.element = element;
    }

    @Override
    public boolean isEmpty() {
        return !element.getModuleReferences().iterator().hasNext();
    }

    @Override
    public @NotNull UniExpression @NotNull [] get() {
        return stream().toArray(UniExpression[]::new);
    }

    @Override
    public @NotNull Stream<UniExpression> stream() {
        List<UniExpression> list = new ArrayList<>();

        element.getModuleReferences().forEach(moduleName -> {
            list.add(UniflowWrapper.wrap(moduleName));
        });
        return list.stream();
    }

    @Override
    public void addFirst(@NotNull UniExpression value) {
        PsiExpression expression = IntellijUnwrapper.unwrap(value);

        if (element.getFirstChild() != null)
            element.addBefore(expression, element.getFirstChild());
        else
            element.add(expression);
    }

    @Override
    public void addAfter(@NotNull UniExpression value, @NotNull UniExpression target) {
        element.addAfter(IntellijUnwrapper.unwrap(value), IntellijUnwrapper.unwrap(target));
    }

    @Override
    public void addBefore(@NotNull UniExpression value, @NotNull UniExpression target) {
        element.addBefore(IntellijUnwrapper.unwrap(value), IntellijUnwrapper.unwrap(target));
    }

    @Override
    public void addLast(@NotNull UniExpression value) {
        element.add(IntellijUnwrapper.unwrap(value));
    }

    @Override
    public void remove(@NotNull UniExpression value) {
        IntellijUnwrapper.unwrap(value).delete();
    }

    @Override
    public int getIndex(@NotNull UniExpression element) {
        PsiExpression expression = IntellijUnwrapper.unwrap(element);
        int index = 0;

        for (PsiJavaModuleReferenceElement moduleReference : this.element.getModuleReferences()) {
            if (moduleReference.isEquivalentTo(expression))
                return index;
            index++;
        }
        return -1;
    }

    @Override
    public void remove(int index) {
        List<PsiJavaModuleReferenceElement> elements = originalList();

        if (index < 0 || index >= elements.size())
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elements.size());
        elements.get(index).delete();
    }

    @Override
    public void clear() {
        originalList().forEach(PsiElement::delete);
    }

    @Override
    public @NotNull Iterator<UniExpression> iterator() {
        return stream().iterator();
    }

    private List<PsiJavaModuleReferenceElement> originalList() {
        List<PsiJavaModuleReferenceElement> list = new ArrayList<>();

        element.getModuleReferences().forEach(list::add);
        return list;
    }
}
