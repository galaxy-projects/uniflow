package org.galaxy.uniflow.intellij.psi.lists.statements;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IJForStatementList<T> implements UniList<T> {

    private final IntFunction<T[]> arrayGenerator;
    private final Consumer<List<T>> updater;

    private final List<T> elements;

    public IJForStatementList(Stream<T> elements,
                              IntFunction<T[]> arrayGenerator,
                              Consumer<List<T>> updater) {
        this.arrayGenerator = arrayGenerator;
        this.updater = updater;
        this.elements = new ArrayList<T>(elements.collect(Collectors.toList())) {
            private static final long serialVersionUID = 7184756141798928010L;

            @Override
            public boolean add(T t) {
                boolean result = super.add(t);
                update();
                return result;
            }

            @Override
            public void add(int index, T element) {
                super.add(index, element);
                update();
            }

            @Override
            public T remove(int index) {
                T res = super.remove(index);
                update();
                return res;
            }

            @Override
            public boolean remove(Object o) {
                if (super.remove(o)) {
                    update();
                    return true;
                }
                return false;
            }

            @Override
            public void clear() {
                super.clear();
                update();
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public @NotNull T @NotNull [] get() {
        return stream().toArray(arrayGenerator);
    }

    @Override
    public @NotNull Stream<T> stream() {
        return elements.stream();
    }

    @Override
    public void addFirst(@NotNull T value) {
        elements.add(0, value);
    }

    @Override
    public void addAfter(@NotNull T value, @NotNull T target) {
        int targetIndex = getIndex(target);

        if (targetIndex == -1)
            elements.add(target);
        else
            elements.add(targetIndex + 1, value);
    }

    @Override
    public void addBefore(@NotNull T value, @NotNull T target) {
        int targetIndex = getIndex(target);

        if (targetIndex == -1)
            elements.add(value);
        else
            elements.add(targetIndex, value);
    }

    @Override
    public void addLast(@NotNull T value) {
        elements.add(value);
    }

    @Override
    public void remove(@NotNull T value) {
        elements.remove(value);
    }

    @Override
    public int getIndex(@NotNull T element) {
        return elements.indexOf(element);
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= elements.size())
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elements.size());
        elements.remove(index);
    }

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return elements.iterator();
    }

    public void update() {
        updater.accept(elements);
    }
}
