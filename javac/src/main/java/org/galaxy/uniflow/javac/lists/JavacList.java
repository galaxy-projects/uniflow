package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class JavacList<T extends UniElement, R> implements UniList<T> {

    private java.util.List<T> elements;
    private final Consumer<List<R>> setter;
    private final Function<T, R> converter;

    public JavacList(List<R> elements,
                     Consumer<List<R>> setter,
                     Function<R, T> invertConverter,
                     Function<T, R> converter) {
        this.setter = setter;
        this.elements = new ArrayList<>(elements.map(invertConverter));
        this.converter = converter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull T @NotNull [] get() {
        return (T[]) elements.toArray(new Object[0]);
    }

    @Override
    public void addFirst(@NotNull T value) {
        elements.add(0, value);
        update();
    }

    @Override
    public void addAfter(@NotNull T value, @NotNull T target) {
        int index = elements.indexOf(target);

        if (index >= 0)
            elements.add(index + 1, value);
        else
            elements.add(value);
        update();
    }

    @Override
    public void addBefore(@NotNull T value, @NotNull T target) {
        int index = elements.indexOf(target);

        if (index >= 0)
            elements.add(index, value);
        else
            elements.add(value);
        update();
    }

    @Override
    public void addLast(@NotNull T value) {
        elements.add(value);
        update();
    }

    @Override
    public void remove(@NotNull T value) {
        elements.remove(value);
        update();
    }

    @Override
    public void clear() {
        elements.clear();
        setter.accept(List.nil());
    }

    private void update() {
        setter.accept(elements.stream().map(converter).collect(List.collector()));
    }
}
