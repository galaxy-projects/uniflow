package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiTypeParameter;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class IJTypeParameter extends IJType<PsiTypeParameter> implements UniTypeParameter {

    public IJTypeParameter(PsiTypeParameter typeParameter) {
        super(typeParameter);
    }

    @Override
    public void setName(@NotNull String name) {
        element.setName(name);
    }

    @Override
    public @NotNull String getName() {
        return Objects.requireNonNull(element.getName());
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getExtends() {
        return IJLists.referenceTypeList(element.getExtendsList());
    }

    @Override
    public @NotNull UniList<@NotNull UniAnnotation> getAnnotations() {
        return IJLists.annotations(element);
    }

    @Override
    public @Nullable UniAnnotation getAnnotation(@NotNull UniClassType type) {
        PsiJavaCodeReferenceElement ref = IntellijUnwrapper.unwrapReference(type);

        return Arrays.stream(element.getAnnotations())
                .filter(annotation -> annotation.getNameReferenceElement() != null)
                .filter(annotation -> annotation.getNameReferenceElement().isEquivalentTo(ref))
                .findFirst()
                .map(UniflowWrapper::wrap)
                .orElse(null);
    }

    @Override
    public @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type) {
        PsiJavaCodeReferenceElement ref = IntellijUnwrapper.unwrapReference(type);

        return Arrays.stream(element.getAnnotations())
                .filter(annotation -> annotation.getNameReferenceElement() != null)
                .filter(annotation -> annotation.getNameReferenceElement().isEquivalentTo(ref))
                .map(UniflowWrapper::wrap)
                .toArray(UniAnnotation[]::new);
    }

    @Override
    public boolean hasAnnotation(@NotNull UniClassType type) {
        return element.hasAnnotation(IntellijUnwrapper.unwrapTypeName(type));
    }

    @Override
    public int getPosition() {
        return element.getStartOffsetInParent();
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.TYPE_PARAMETER;
    }
}
