package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElement;
import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public abstract class IJAbstractList<LIST extends PsiElement, ELEMENT extends PsiElement, T> implements UniList<T> {

    protected final @Nullable LIST list;
    protected final IntFunction<T[]> arrayGenerator;
    protected final Function<ELEMENT, T> wrapper;
    protected final Function<T, ELEMENT> unwrapper;

    public IJAbstractList(@Nullable LIST list,
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
        ELEMENT[] elements = getElements();

        return elements == null || elements.length == 0;
    }

    @Override
    public @NotNull T @NotNull [] get() {
        return stream().toArray(arrayGenerator);
    }

    @Override
    public @NotNull Stream<T> stream() {
        return elementStream().map(wrapper);
    }

    @Override
    public void addFirst(@NotNull T value) {
        ELEMENT[] elements = getElements();
        if (list == null || elements == null) return;
        ELEMENT newElement = unwrapper.apply(value);

        if (elements.length == 0)
            list.add(newElement);
        else
            list.addBefore(newElement, elements[0]);
    }

    @Override
    public void addAfter(@NotNull T value, @NotNull T target) {
        if (list != null)
            list.addAfter(unwrapper.apply(value), unwrapper.apply(target));
    }

    @Override
    public void addBefore(@NotNull T value, @NotNull T target) {
        if (list != null)
            list.addBefore(unwrapper.apply(value), unwrapper.apply(target));
    }

    @Override
    public void addLast(@NotNull T value) {
        if (list != null)
            list.add(unwrapper.apply(value));
    }

    @Override
    public void remove(@NotNull T value) {
        unwrapper.apply(value).delete();
    }

    @Override
    public int getIndex(@NotNull T element) {
        ELEMENT[] elements = getElements();
        if (list == null || elements == null) return -1;
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
        if (list == null || elements == null) return;

        if (index < 0 || index >= elements.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elements.length);
        elements[index].delete();
    }

    @Override
    public void clear() {
        if (list != null)
            list.deleteChildRange(list.getFirstChild(), list.getLastChild());
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return stream().iterator();
    }

    protected Stream<ELEMENT> elementStream() {
        ELEMENT[] elements = getElements();

        if (elements == null) return Stream.empty();
        return Arrays.stream(elements);
    }

    protected abstract ELEMENT @Nullable [] getElements();

}
