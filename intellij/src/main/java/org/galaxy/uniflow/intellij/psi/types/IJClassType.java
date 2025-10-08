package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiClassType;
import org.galaxy.uniflow.api.types.UniClassType;

public class IJClassType implements UniClassType {

    private final PsiClassType type;

    public IJClassType(PsiClassType type) {
        this.type = type;
    }

    public PsiClassType getType() {
        return type;
    }
}
