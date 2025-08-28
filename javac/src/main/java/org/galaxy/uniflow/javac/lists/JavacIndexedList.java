package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.lists.UniIndexedList;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public class JavacIndexedList<T, R> extends JavacList<T, R> implements UniIndexedList<T> {

    public JavacIndexedList(List<R> elements,
                            Consumer<List<R>> setter,
                            Function<R, T> invertConverter,
                            Function<T, R> converter) {
        super(elements, setter, invertConverter, converter);
    }

    @Override
    public int getIndex(@NotNull T element) {
        return elements.indexOf(element);
    }

    @Override
    public void remove(int index) {
        if (index >= 0 && index < elements.size()) {
            elements.remove(index);
            update();
        }
    }
}
