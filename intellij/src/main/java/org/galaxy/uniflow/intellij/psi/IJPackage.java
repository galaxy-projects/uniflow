package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.PsiPackageStatement;
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

public class IJPackage extends IJElement<PsiPackageStatement> implements UniPackage {

    public IJPackage(PsiPackageStatement element) {
        super(element);
    }

    @Override
    public @NotNull String getPackageName() {
        return element.getPackageName();
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
        PsiElement resolved = element.getPackageReference().resolve();

        if (resolved instanceof PsiPackage pkg) {
            PsiAnnotation annotation = pkg.getAnnotation(IntellijUnwrapper.unwrapTypeName(type));

            return annotation != null ? UniflowWrapper.wrap(annotation) : null;
        }
        return null;
    }

    @Override
    public @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type) {
        PsiElement resolved = element.getPackageReference().resolve();

        if (resolved instanceof PsiPackage pkg) {
            String name = IntellijUnwrapper.unwrapTypeName(type);

            return Arrays.stream(pkg.getAnnotations())
                    .filter(annotation -> Objects.equals(annotation.getQualifiedName(), name))
                    .map(UniflowWrapper::wrap)
                    .toArray(UniAnnotation[]::new);
        }
        return new UniAnnotation[0];
    }

    @Override
    public boolean hasAnnotation(@NotNull UniClassType type) {
        PsiElement resolved = element.getPackageReference().resolve();

        if (resolved instanceof PsiPackage pkg) {
            return pkg.hasAnnotation(IntellijUnwrapper.unwrapTypeName(type));
        }
        return false;
    }
}
