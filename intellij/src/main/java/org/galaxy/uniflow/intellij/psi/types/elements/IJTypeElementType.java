package org.galaxy.uniflow.intellij.psi.types.elements;

import com.intellij.psi.PsiTypeElement;
import org.galaxy.uniflow.api.types.UniType;

public class IJTypeElementType extends IJElementType<PsiTypeElement> implements UniType {

    public IJTypeElementType(PsiTypeElement element) {
        super(element);
    }
}
