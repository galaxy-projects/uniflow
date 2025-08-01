package org.galaxy.uniflow.api;

import org.jetbrains.annotations.NotNull;

public interface UniStatement extends UniElement {

    @NotNull UniStatement @NotNull [] getStatements();

    void addFirst(@NotNull UniStatement statement);

    void addAfter(@NotNull UniStatement statement, @NotNull UniStatement target);

    void addBefore(@NotNull UniStatement statement, @NotNull UniStatement target);

    void addLast(@NotNull UniStatement statement);

    void removeStatement(@NotNull UniStatement statement);

}
