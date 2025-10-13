package org.galaxy.uniflow.intellij.psi.elements;

import com.intellij.psi.PsiNameValuePair;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.intellij.psi.IJElement;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJAnnotationAttribute extends IJElement<PsiNameValuePair> implements UniAnnotationAttribute {

    public IJAnnotationAttribute(PsiNameValuePair element) {
        super(element);
    }

    @Override
    public @NotNull String getName() {
        return element.getAttributeName();
    }

    @Override
    public @NotNull UniAnnotationValue getValue() {
        return UniflowWrapper.wrap(element.getValue());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.ANNOTATION;
    }
}
