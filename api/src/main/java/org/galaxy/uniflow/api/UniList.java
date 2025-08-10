package org.galaxy.uniflow.api;

import org.jetbrains.annotations.NotNull;

public interface UniList<T extends UniElement> extends UniElement {

    @NotNull T @NotNull [] get();

    void addFirst(@NotNull T statement);

    void addAfter(@NotNull T statement, @NotNull T target);

    void addBefore(@NotNull T statement, @NotNull T target);

    void addLast(@NotNull T statement);

    void remove(@NotNull T statement);

}
