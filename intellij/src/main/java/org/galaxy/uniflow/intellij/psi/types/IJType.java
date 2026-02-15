package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiType;
import org.galaxy.uniflow.api.types.UniType;

public class IJType<T extends PsiType> implements UniType {

    protected final T type;

    public IJType(T type) {
        this.type = type;
    }

    public T getRawType() {
        return type;
    }
}
