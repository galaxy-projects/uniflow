package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiPackage;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniPackage;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class IJPackage extends IJElement<PsiPackage> implements UniPackage {

    public IJPackage(PsiPackage element) {
        super(element);
    }

    @Override
    public @NotNull String getPackageName() {
        return element.getQualifiedName();
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.PACKAGE;
    }

    @Override
    public @NotNull UniList<@NotNull UniAnnotation> getAnnotations() {
        return IJLists.annotations(element.getAnnotationList());
    }

    @Override
    public @Nullable UniAnnotation getAnnotation(@NotNull UniClassType type) {
        PsiAnnotation annotation = element.getAnnotation(IntellijUnwrapper.unwrapTypeName(type));

        return annotation != null ? UniflowWrapper.wrap(annotation) : null;
    }

    @Override
    public @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type) {
        String name = IntellijUnwrapper.unwrapTypeName(type);

        return Arrays.stream(element.getAnnotations())
                .filter(annotation -> Objects.equals(annotation.getQualifiedName(), name))
                .map(UniflowWrapper::wrap)
                .toArray(UniAnnotation[]::new);
    }

    @Override
    public boolean hasAnnotation(@NotNull UniClassType type) {
        return element.hasAnnotation(IntellijUnwrapper.unwrapTypeName(type));
    }
}
