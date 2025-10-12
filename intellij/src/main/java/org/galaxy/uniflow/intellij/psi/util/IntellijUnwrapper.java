package org.galaxy.uniflow.intellij.psi.util;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniParameter;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
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

    public static @NotNull PsiParameter unwrap(UniParameter parameter) {
        return null;
    }

    public static @NotNull PsiSwitchLabelStatementBase unwrap(UniCase uniCase) {
        return null;
    }

    public static @NotNull PsiCaseLabelElement unwrap(UniCaseLabel label) {
        return null;
    }

    public static @NotNull PsiCodeBlock unwrap(UniBlock block) {
        return null;
    }

    public static @NotNull PsiStatement unwrap(UniStatement statement) {
        return null;
    }

    public static @NotNull PsiElement unwrap(UniElement element) {
        return null;
    }

    public static @NotNull PsiClassType unwrap(UniClassType type) {
        return null;
    }

    public static @NotNull PsiTypeElement unwrap(UniType type) {
        return null;
    }
}
