package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaModuleReferenceElement;
import com.intellij.psi.PsiPackageAccessibilityStatement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public record IJModuleNameList(PsiPackageAccessibilityStatement element) implements UniList<String> {

    @Override
    public boolean isEmpty() {
        return !element.getModuleReferences().iterator().hasNext();
    }

    @Override
    public @NotNull String @NotNull [] get() {
        return stream().toArray(String[]::new);
    }

    @Override
    public @NotNull Stream<String> stream() {
        List<String> list = new ArrayList<>();

        element.getModuleReferences().forEach(moduleRef -> {
            list.add(moduleRef.getReferenceText());
        });
        return list.stream();
    }

    @Override
    public void addFirst(@NotNull String value) {
        PsiJavaModuleReferenceElement moduleRef = IntellijUnwrapper.unwrapModuleReference(value);

        if (element.getFirstChild() != null)
            element.addBefore(moduleRef, element.getFirstChild());
        else
            element.add(moduleRef);
    }

    @Override
    public void addAfter(@NotNull String value, @NotNull String target) {
        element.addAfter(IntellijUnwrapper.unwrapModuleReference(value),
                IntellijUnwrapper.unwrapModuleReference(target));
    }

    @Override
    public void addBefore(@NotNull String value, @NotNull String target) {
        element.addBefore(IntellijUnwrapper.unwrapModuleReference(value),
                IntellijUnwrapper.unwrapModuleReference(target));
    }

    @Override
    public void addLast(@NotNull String value) {
        element.add(IntellijUnwrapper.unwrapModuleReference(value));
    }

    @Override
    public void remove(@NotNull String value) {
        originalList().stream().filter(element -> element.textMatches(value)).findFirst().ifPresent(PsiElement::delete);
    }

    @Override
    public int getIndex(@NotNull String element) {
        int index = 0;

        for (PsiJavaModuleReferenceElement moduleReference : this.element.getModuleReferences()) {
            if (moduleReference.textMatches(element))
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
    public @NotNull Iterator<String> iterator() {
        return stream().iterator();
    }

    private List<PsiJavaModuleReferenceElement> originalList() {
        List<PsiJavaModuleReferenceElement> list = new ArrayList<>();

        element.getModuleReferences().forEach(list::add);
        return list;
    }
}
