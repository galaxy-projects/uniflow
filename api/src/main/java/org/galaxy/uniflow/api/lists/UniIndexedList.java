package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniIndexedList<T> extends UniList<T> {

    int getIndex(@NotNull T element);

    void remove(int index);

}
