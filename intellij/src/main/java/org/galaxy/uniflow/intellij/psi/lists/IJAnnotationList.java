package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationOwner;
import com.intellij.psi.PsiClassType;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public class IJAnnotationList implements UniList<UniAnnotation> {

    private final PsiAnnotationOwner owner;

    public IJAnnotationList(PsiAnnotationOwner owner) {
        this.owner = owner;
    }

    @Override
    public boolean isEmpty() {
        return owner.getAnnotations().length == 0;
    }

    @Override
    public @NotNull UniAnnotation @NotNull [] get() {
        return stream().toArray(UniAnnotation[]::new);
    }

    @Override
    public @NotNull Stream<UniAnnotation> stream() {
        return Arrays.stream(owner.getAnnotations()).map(UniflowWrapper::wrap);
    }

    @Override
    public void addFirst(@NotNull UniAnnotation value) {
        PsiClassType type = IntellijUnwrapper.unwrap(value.getType());
        PsiAnnotation annotation = owner.addAnnotation(type.getClassName());

        for (UniAnnotationAttribute attribute : value.getAttributes()) {
            annotation.setDeclaredAttributeValue(attribute.getName(), IntellijUnwrapper.unwrap(attribute.getValue()));
        }
    }

    @Override
    public void addAfter(@NotNull UniAnnotation value, @NotNull UniAnnotation target) {
        addFirst(value);
    }

    @Override
    public void addBefore(@NotNull UniAnnotation value, @NotNull UniAnnotation target) {
        addFirst(value);
    }

    @Override
    public void addLast(@NotNull UniAnnotation value) {
        addFirst(value);
    }

    @Override
    public void remove(@NotNull UniAnnotation value) {
        PsiClassType type = IntellijUnwrapper.unwrap(value.getType());
        PsiAnnotation annotation = owner.findAnnotation(type.getClassName());

        if (annotation == null)
            throw new IllegalArgumentException("Annotation not found: " + type.getClassName());
        annotation.delete();
    }

    @Override
    public int getIndex(@NotNull UniAnnotation element) {
        PsiClassType type = IntellijUnwrapper.unwrap(element.getType());
        int index = 0;

        for (PsiAnnotation annotation : owner.getAnnotations()) {
            if (Objects.equals(annotation.getQualifiedName(), type.getClassName())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public void remove(int index) {
        PsiAnnotation[] annotations = owner.getAnnotations();

        if (index >= 0 && index < annotations.length)
            annotations[index].delete();
        else
            throw new IllegalArgumentException("Index out of bounds: " + index);
    }

    @Override
    public void clear() {
        for (PsiAnnotation annotation : owner.getAnnotations()) {
            annotation.delete();
        }
    }

    @Override
    public @NotNull Iterator<UniAnnotation> iterator() {
        return stream().iterator();
    }
}
