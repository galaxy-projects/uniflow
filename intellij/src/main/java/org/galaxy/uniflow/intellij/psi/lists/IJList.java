package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElement;
import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public abstract class IJList<LIST extends PsiElement, ELEMENT extends PsiElement, T> implements UniList<T> {

    protected LIST list;
    private final IntFunction<T[]> arrayGenerator;
    private final Function<ELEMENT, T> wrapper;
    private final Function<T, ELEMENT> unwrapper;

    public IJList(LIST list,
                  IntFunction<T[]> arrayGenerator,
                  Function<ELEMENT, T> wrapper,
                  Function<T, ELEMENT> unwrapper) {
        this.list = list;
        this.arrayGenerator = arrayGenerator;
        this.wrapper = wrapper;
        this.unwrapper = unwrapper;
    }

    @Override
    public boolean isEmpty() {
        return getElements().length == 0;
    }

    @Override
    public @NotNull T @NotNull [] get() {
        return stream().toArray(arrayGenerator);
    }

    @Override
    public @NotNull Stream<T> stream() {
        return Arrays.stream(getElements()).map(wrapper);
    }

    @Override
    public void addFirst(@NotNull T value) {
        ELEMENT[] elements = getElements();
        ELEMENT newElement = unwrapper.apply(value);

        if (elements.length == 0)
            list.add(newElement);
        else
            list.addBefore(newElement, elements[0]);
    }

    @Override
    public void addAfter(@NotNull T value, @NotNull T target) {
        list.addAfter(unwrapper.apply(value), unwrapper.apply(target));
    }

    @Override
    public void addBefore(@NotNull T value, @NotNull T target) {
        list.addBefore(unwrapper.apply(value), unwrapper.apply(target));
    }

    @Override
    public void addLast(@NotNull T value) {
        list.add(unwrapper.apply(value));
    }

    @Override
    public void remove(@NotNull T value) {
        unwrapper.apply(value).delete();
    }

    @Override
    public int getIndex(@NotNull T element) {
        ELEMENT[] elements = getElements();
        ELEMENT target = unwrapper.apply(element);

        for (int i = 0; i < elements.length; i++) {
            if (Objects.equals(elements[i], target))
                return i;
        }
        return -1;
    }

    @Override
    public void remove(int index) {
        ELEMENT[] elements = getElements();

        if (index < 0 || index >= elements.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elements.length);
        elements[index].delete();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        list = (LIST) list.replace(createEmptyList());
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return stream().iterator();
    }

    protected abstract ELEMENT[] getElements();

    protected abstract LIST createEmptyList();

}
