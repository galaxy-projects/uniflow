package org.galaxy.uniflow.intellij.psi.lists;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

public class IJEmptyList<T> implements UniList<T> {

    private final Class<T> componentType;

    public IJEmptyList(Class<T> componentType) {
        this.componentType = componentType;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public @NotNull T @NotNull [] get() {
        return Array.newInstance(componentType, 0);
    }

    @Override
    public @NotNull Stream<T> stream() {
        return Stream.empty();
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
        return -1;
    }

    @Override
    public void remove(int index) {}

    @Override
    public void clear() {}

    @Override
    public @NotNull Iterator<T> iterator() {
        return Collections.emptyIterator();
    }

    public static <T> UniList<T> create(Class<T> componentType) {
        return new IJEmptyList<>(componentType);
    }
}
