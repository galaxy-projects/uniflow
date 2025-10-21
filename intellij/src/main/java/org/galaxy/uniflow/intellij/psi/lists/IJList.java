package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.IntFunction;

public class IJList<LIST extends PsiElement, ELEMENT extends PsiElement, T> extends IJAbstractList<LIST, ELEMENT, T> {

    private final Function<@NotNull LIST, ELEMENT[]> getter;

    public IJList(@Nullable LIST list,
                  Function<@NotNull LIST, ELEMENT[]> getter,
                  IntFunction<T[]> arrayGenerator,
                  Function<ELEMENT, T> wrapper,
                  Function<T, ELEMENT> unwrapper) {
        super(list, arrayGenerator, wrapper, unwrapper);
        this.getter = getter;
    }

    @Override
    protected ELEMENT @Nullable [] getElements() {
        return list != null ? getter.apply(list) : null;
    }
}
