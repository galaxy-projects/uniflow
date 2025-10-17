package org.galaxy.uniflow.javac.lists;

import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class JavacList<T, R> implements UniList<T> {

    protected final Supplier<List<R>> elementsSupplier;
    protected final Consumer<List<R>> setter;
    protected final Function<R, T> wrapper;
    protected final Function<T, R> unwrapper;

    public JavacList(Supplier<List<R>> elementsSupplier,
                     Consumer<List<R>> setter,
                     Function<R, T> wrapper,
                     Function<T, R> unwrapper) {
        this.elementsSupplier = elementsSupplier;
        this.setter = setter;
        this.wrapper = wrapper;
        this.unwrapper = unwrapper;
    }

    @Override
    public boolean isEmpty() {
        return elementsSupplier.get().isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull T @NotNull [] get() {
        return (T[]) elementsSupplier.get().map(wrapper).toArray(new Object[0]);
    }

    @Override
    public @NotNull Stream<T> stream() {
        return elementsSupplier.get().stream().map(wrapper);
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return elementsSupplier.get().map(wrapper).iterator();
    }

    @Override
    public void addFirst(@NotNull T value) {
        update(() -> {
            R unwrapped = unwrapper.apply(value);

            onAdded(unwrapped);
            return elementsSupplier.get().prepend(unwrapped);
        });
    }

    @Override
    public void addAfter(@NotNull T value, @NotNull T target) {
        List<R> elements = elementsSupplier.get();
        R targetUnwrapped = unwrapper.apply(target);
        ListBuffer<R> buffer = new ListBuffer<>();
        boolean added = false;
        R unwrapped = unwrapper.apply(value);

        for (R element : elements) {
            buffer.append(element);
            if (Objects.equals(targetUnwrapped, element)) {
                onAdded(unwrapped);
                buffer.add(unwrapped);
                added = true;
            }
        }
        if (!added) {
            onAdded(unwrapped);
            buffer.append(unwrapped);
        }

        update(buffer::toList);
    }

    @Override
    public void addBefore(@NotNull T value, @NotNull T target) {
        List<R> elements = elementsSupplier.get();
        R targetUnwrapped = unwrapper.apply(target);
        ListBuffer<R> buffer = new ListBuffer<>();
        boolean added = false;
        R unwrapped = unwrapper.apply(value);

        for (R element : elements) {
            if (Objects.equals(targetUnwrapped, element)) {
                onAdded(unwrapped);
                buffer.add(unwrapped);
                added = true;
            }
            buffer.append(element);
        }
        if (!added) {
            onAdded(unwrapped);
            buffer.append(unwrapped);
        }

        update(buffer::toList);
    }

    @Override
    public void addLast(@NotNull T value) {
        update(() -> {
            R unwrapped = unwrapper.apply(value);

            onAdded(unwrapped);
            return elementsSupplier.get().append(unwrapped);
        });
    }

    @Override
    public void remove(@NotNull T value) {
        R unwrappedValue = unwrapper.apply(value);

        update(() -> elementsSupplier.get().stream()
                .filter(element -> element != unwrappedValue)
                .collect(List.collector()));
    }

    @Override
    public int getIndex(@NotNull T element) {
        return elementsSupplier.get().indexOf(unwrapper.apply(element));
    }

    @Override
    public void remove(int index) {
        update(() -> {
            ListBuffer<R> newElements = new ListBuffer<>();
            Iterator<R> elements = elementsSupplier.get().iterator();
            int currentIndex = 0;

            while (elements.hasNext() && currentIndex < index) {
                newElements.append(elements.next());
                currentIndex++;
            }
            if (elements.hasNext())
                elements.next(); // skip 'index' element
            while (elements.hasNext())
                newElements.append(elements.next());
            return newElements.toList();
        });
    }

    @Override
    public void clear() {
        setter.accept(List.nil());
    }

    public <T1 extends T, R1 extends R> JavacList<T1, R1> partial(Predicate<R> predicate,
                                                                  Function<R, R1> mapper,
                                                                  Function<R1, T1> wrapper,
                                                                  Function<T1, R1> unwrapper) {
        return new JavacList<>(
                () -> elementsSupplier.get().stream().filter(predicate).map(mapper).collect(List.collector()),
                newList -> {
                    ListBuffer<R> result = new ListBuffer<>();

                    elementsSupplier.get().stream().filter(predicate.negate()).forEach(result::append);
                    result.addAll(newList);
                    setter.accept(result.toList());
                },
                wrapper,
                unwrapper
        );
    }

    protected void onAdded(R element) {}

    protected void update(Supplier<List<R>> newElements) {
        setter.accept(newElements.get());
    }
}
