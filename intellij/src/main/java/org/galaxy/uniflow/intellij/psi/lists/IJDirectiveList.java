package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaModule;
import com.intellij.psi.PsiStatement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.modules.directives.*;
import org.galaxy.uniflow.intellij.psi.modules.directives.*;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public record IJDirectiveList(PsiJavaModule module) implements UniList<UniDirective> {

    @Override
    public boolean isEmpty() {
        return get().length == 0;
    }

    @Override
    public @NotNull UniDirective @NotNull [] get() {
        return stream().toArray(UniDirective[]::new);
    }

    @Override
    public @NotNull Stream<UniDirective> stream() {
        Stream<UniRequires> requires = streamOf(PsiJavaModule::getRequires, IJRequires::new);
        Stream<UniExports> exports = streamOf(PsiJavaModule::getExports, IJExports::new);
        Stream<UniOpens> opens = streamOf(PsiJavaModule::getOpens, IJOpens::new);
        Stream<UniUses> uses = streamOf(PsiJavaModule::getUses, IJUses::new);
        Stream<UniProvides> provides = streamOf(PsiJavaModule::getProvides, IJProvides::new);

        return Stream.concat(requires,
                Stream.concat(exports,
                        Stream.concat(opens,
                                Stream.concat(uses, provides))));
    }

    @Override
    public void addFirst(@NotNull UniDirective value) {
        PsiStatement statement = IntellijUnwrapper.unwrap(value);

        if (module.getFirstChild() != null)
            module.addBefore(statement, module.getFirstChild());
        else
            module.add(statement);
    }

    @Override
    public void addAfter(@NotNull UniDirective value, @NotNull UniDirective target) {
        module.addAfter(IntellijUnwrapper.unwrap(value), IntellijUnwrapper.unwrap(target));
    }

    @Override
    public void addBefore(@NotNull UniDirective value, @NotNull UniDirective target) {
        module.addBefore(IntellijUnwrapper.unwrap(value), IntellijUnwrapper.unwrap(target));
    }

    @Override
    public void addLast(@NotNull UniDirective value) {
        module.add(IntellijUnwrapper.unwrap(value));
    }

    @Override
    public void remove(@NotNull UniDirective value) {
        IntellijUnwrapper.unwrap(value).delete();
    }

    @Override
    public int getIndex(@NotNull UniDirective element) {
        PsiStatement directive = IntellijUnwrapper.unwrap(element);
        int index = 0;

        for (PsiElement child : module.getChildren()) {
            if (child.isEquivalentTo(directive))
                return index;
            index++;
        }
        return -1;
    }

    @Override
    public void remove(int index) {
        PsiElement[] children = module.getChildren();

        if (index < 0 || index >= children.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + children.length);
        children[index].delete();
    }

    @Override
    public void clear() {
        for (PsiElement child : module.getChildren())
            child.delete();
    }

    @Override
    public @NotNull Iterator<UniDirective> iterator() {
        return stream().iterator();
    }

    private <F, T> Stream<T> streamOf(Function<PsiJavaModule, Iterable<F>> getter, Function<F, T> wrapper) {
        Iterable<F> iterable = getter.apply(module);

        return StreamSupport.stream(iterable.spliterator(), false).map(wrapper);
    }
}