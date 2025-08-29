package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class JavacList<T, R> implements UniList<T> {

    protected final java.util.List<T> elements;
    protected final Consumer<List<R>> setter;
    protected final Function<T, R> converter;

    protected JavacList(java.util.List<T> elements, Consumer<List<R>> setter, Function<T, R> converter) {
        this.elements = elements;
        this.setter = setter;
        this.converter = converter;
    }

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
    public @NotNull Stream<T> stream() {
        return elements.stream();
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

    public <T1, R1> JavacList<T1, R1> partial(Predicate<T> predicate,
                                              Function<T, T1> mapper,
                                              Function<R1, R> invertMapper,
                                              Function<T1, R1> inverter) {
        java.util.List<T1> affected = new ArrayList<>();
        java.util.List<R1> notAffected = new ArrayList<>();

        elements.forEach(element -> {
            if (predicate.test(element))
                affected.add(mapper.apply(element));
            else
                notAffected.add(inverter.apply(mapper.apply(element)));
        });

        return new JavacList<>(
                affected,
                newList -> {
                    ListBuffer<R1> result = new ListBuffer<>();

                    result.addAll(notAffected);
                    result.addAll(newList);
                    setter.accept(result.toList().map(invertMapper));
                },
                inverter
        );
    }

    protected void update() {
        setter.accept(elements.stream().map(converter).collect(List.collector()));
    }
}
