package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiClassType;
import org.galaxy.uniflow.api.types.UniClassType;

public class IJClassType extends IJType<PsiClassType> implements UniClassType {

    public IJClassType(PsiClassType type) {
        super(type);
    }
}
