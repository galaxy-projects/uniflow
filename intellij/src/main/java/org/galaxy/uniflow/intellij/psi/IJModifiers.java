package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiModifierList;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.intellij.psi.lists.IJAnnotationList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class IJModifiers extends IJElement<PsiModifierList> implements UniModifiers {

    public IJModifiers(PsiModifierList element) {
        super(element);
    }

    @Override
    public @NotNull UniModifier @NotNull [] getModifiers() {
        return Arrays.stream(UniModifier.values()).filter(this::hasModifier).toArray(UniModifier[]::new);
    }

    @Override
    public boolean hasModifier(@NotNull UniModifier modifier) {
        return element.hasModifierProperty(Modifiers.getPsiModifier(modifier));
    }

    @Override
    public void addModifier(@NotNull UniModifier modifier) {
        element.setModifierProperty(Modifiers.getPsiModifier(modifier), true);
    }

    @Override
    public void removeModifier(@NotNull UniModifier modifier) {
        element.setModifierProperty(Modifiers.getPsiModifier(modifier), false);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.MODIFIERS;
    }

    @Override
    public @NotNull UniList<@NotNull UniAnnotation> getAnnotations() {
        return new IJAnnotationList(element);
    }

    @Override
    public @Nullable UniAnnotation getAnnotation(@NotNull UniClassType type) {
        PsiClassType psiType = IntellijUnwrapper.unwrap(type);

        return Arrays.stream(element.getAnnotations())
                .filter(annotation -> Objects.equals(annotation.getQualifiedName(), psiType.getClassName()))
                .findFirst()
                .map(UniflowWrapper::wrap)
                .orElse(null);
    }

    @Override
    public @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type) {
        PsiClassType psiType = IntellijUnwrapper.unwrap(type);

        return Arrays.stream(element.getAnnotations())
                .filter(annotation -> Objects.equals(annotation.getQualifiedName(), psiType.getClassName()))
                .map(UniflowWrapper::wrap)
                .toArray(UniAnnotation[]::new);
    }

    @Override
    public boolean hasAnnotation(@NotNull UniClassType type) {
        PsiClassType psiType = IntellijUnwrapper.unwrap(type);

        return Arrays.stream(element.getAnnotations())
                .anyMatch(annotation -> Objects.equals(annotation.getQualifiedName(), psiType.getClassName()));
    }
}
