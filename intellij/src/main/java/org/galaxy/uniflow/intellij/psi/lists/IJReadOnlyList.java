package org.galaxy.uniflow.intellij.psi.lists;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public record IJReadOnlyList<T>(List<T> elements, IntFunction<T[]> arrayGenerator) implements UniList<T> {

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public @NotNull T @NotNull [] get() {
        return elements.toArray(arrayGenerator);
    }

    @Override
    public @NotNull Stream<T> stream() {
        return elements.stream();
    }

    @Override
    public void addFirst(@NotNull T value) {}

    @Override
    public void addAfter(@NotNull T value, @NotNull T target) {}

    @Override
    public void addBefore(@NotNull T value, @NotNull T target) {}

    @Override
    public void addLast(@NotNull T value) {}

    @Override
    public void remove(@NotNull T value) {}

    @Override
    public int getIndex(@NotNull T element) {
        return elements.indexOf(element);
    }

    @Override
    public void remove(int index) {}

    @Override
    public void clear() {}

    @Override
    public @NotNull Iterator<T> iterator() {
        return elements.iterator();
    }
}
