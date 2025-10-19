package org.galaxy.uniflow.intellij.psi.util;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.api.statements.*;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.jetbrains.annotations.NotNull;

public class IntellijUnwrapper {

    public static @NotNull PsiJavaToken unwrap(UniOperatorSignature signature) {
        return null;
    }

    public static @NotNull PsiAnnotationMemberValue unwrap(UniAnnotationValue value) {
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

    public static @NotNull PsiVariable unwrap(UniVariable variable) {
        return null;
    }

    public static @NotNull PsiField unwrap(UniField field) {
        return null;
    }

    public static @NotNull PsiSwitchLabelStatementBase unwrap(UniCaseBase uniCase) {
        return null;
    }

    public static @NotNull PsiCaseLabelElement unwrap(UniCaseLabel label) {
        return null;
    }

    public static @NotNull PsiClassInitializer unwrap(UniClassInitializer classInitializer) {
        return null;
    }

    public static @NotNull PsiCodeBlock unwrap(UniBlock block) {
        return null;
    }

    public static @NotNull PsiCatchSection unwrap(UniCatch section) {
        return null;
    }

    public static @NotNull PsiClass unwrap(UniClass uniClass) {
        return null;
    }

    public static @NotNull PsiStatement unwrap(UniStatement statement) {
        return null;
    }

    public static @NotNull PsiStatement unwrap(UniDirective directive) {
        return null;
    }

    public static @NotNull PsiTypeParameter unwrap(UniTypeParameter typeParameter) {
        return null;
    }

    public static @NotNull PsiMethod unwrap(UniMethod method) {
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

    public static @NotNull PsiJavaCodeReferenceElement unwrapType(UniType type) {
        return null;
    }

    public static @NotNull PsiResourceListElement unwrapResource(UniElement element) {
        return null;
    }

    public static @NotNull PsiJavaCodeReferenceElement unwrapReference(UniExpression expression) {
        return null;
    }
}
