package org.galaxy.uniflow.api;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface UniList<T> extends Iterable<T> {

    boolean isEmpty();

    @NotNull T @NotNull [] get();

    @NotNull Stream<T> stream();

    void addFirst(@NotNull T value);

    void addAfter(@NotNull T value, @NotNull T target);

    void addBefore(@NotNull T value, @NotNull T target);

    void addLast(@NotNull T value);

    void remove(@NotNull T value);

    int getIndex(@NotNull T element);

    void remove(int index);

    void clear();

}
