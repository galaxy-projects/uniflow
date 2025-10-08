package org.galaxy.uniflow.intellij.psi.util;

import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiJavaToken;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.jetbrains.annotations.NotNull;

public class IntellijUnwrapper {

    public static @NotNull PsiJavaToken unwrap(UniOperatorSignature signature) {
        return null;
    }

    public static @NotNull PsiExpression unwrap(UniExpression expression) {
        return null;
    }

    public static @NotNull Object unwrap(UniAnnotationAttribute attribute) {
        return null;
    }

    public static @NotNull PsiElement unwrap(UniElement element) {
        return null;
    }

    public static @NotNull PsiClassType unwrap(UniClassType type) {
        return null;
    }
}
