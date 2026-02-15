package org.galaxy.uniflow.intellij.psi.elements;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.intellij.psi.IJElement;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class IJAnnotation extends IJElement<PsiAnnotation> implements UniAnnotation {

    public IJAnnotation(PsiAnnotation element) {
        super(element);
    }

    @Override
    public @NotNull UniClassType getType() {
        return UniflowWrapper.wrapClassType(element.getNameReferenceElement());
    }

    @Override
    public @NotNull UniAnnotationAttribute @NotNull [] getAttributes() {
        return Arrays.stream(element.getParameterList().getAttributes())
                .map(UniflowWrapper::wrap)
                .toArray(UniAnnotationAttribute[]::new);
    }

    @Override
    public boolean hasAttribute(@NotNull String name) {
        return element.findDeclaredAttributeValue(name) != null;
    }

    @Override
    public @Nullable UniAnnotationValue getAttribute(@NotNull String name) {
        return UniflowWrapper.wrap(element.findDeclaredAttributeValue(name));
    }

    @Override
    public void addAttribute(@NotNull String name, @NotNull UniAnnotationValue value) {
        element.setDeclaredAttributeValue(name, IntellijUnwrapper.unwrap(value));
    }

    @Override
    public void addAttribute(@NotNull UniAnnotationAttribute attribute) {
        addAttribute(attribute.getName(), attribute.getValue());
    }

    @Override
    public void removeAttribute(@NotNull String name) {
        PsiAnnotationMemberValue annotationMemberValue = element.findDeclaredAttributeValue(name);

        if (annotationMemberValue != null)
            annotationMemberValue.delete();
    }

    @Override
    public void removeAttribute(@NotNull UniAnnotationAttribute attribute) {
        removeAttribute(attribute.getName());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.ANNOTATION;
    }
}
