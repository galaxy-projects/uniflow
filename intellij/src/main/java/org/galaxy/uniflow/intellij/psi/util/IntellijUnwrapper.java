package org.galaxy.uniflow.intellij.psi.util;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.elements.imports.UniImportBase;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.elements.resources.UniResource;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.api.statements.*;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.intellij.psi.IJElement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.elements.resources.IJResource;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class IntellijUnwrapper {

    public static @NotNull PsiElement unwrap(UniElement element) {
        if (element instanceof IJElement<?> ijElement)
            return ijElement.getElement();
        throw new IllegalArgumentException("Element must be an IJElement");
    }

    public static @NotNull PsiExpression unwrap(UniExpression expression) {
        return (PsiExpression) unwrap((UniElement) expression);
    }

    public static @NotNull PsiStatement unwrap(UniStatement statement) {
        return (PsiStatement) unwrap((UniElement) statement);
    }

    public static @NotNull PsiAnnotation unwrap(UniAnnotation annotation) {
        return (PsiAnnotation) unwrap((UniElement) annotation);
    }

    public static @NotNull PsiAnnotationMemberValue unwrap(UniAnnotationValue value) {
        return (PsiAnnotationMemberValue) unwrap((UniElement) value);
    }

    public static @NotNull PsiPattern unwrap(UniPattern pattern) {
        return (PsiPattern) unwrap((UniElement) pattern);
    }

    public static @NotNull Object unwrap(UniAnnotationAttribute attribute) {
        return null;
    }

    public static @NotNull PsiParameter unwrap(UniParameter parameter) {
        return (PsiParameter) unwrap((UniElement) parameter);
    }

    public static @NotNull PsiVariable unwrap(UniVariable variable) {
        return (PsiVariable) unwrap((UniElement) variable);
    }

    public static @NotNull PsiField unwrap(UniField field) {
        return (PsiField) unwrap((UniElement) field);
    }

    public static @NotNull PsiSwitchLabelStatementBase unwrap(UniCaseBase uniCase) {
        return (PsiSwitchLabelStatementBase) unwrap((UniElement) uniCase);
    }

    public static @NotNull PsiCaseLabelElement unwrap(UniCaseLabel label) {
        return (PsiCaseLabelElement) unwrap((UniElement) label);
    }

    public static @NotNull PsiClassInitializer unwrap(UniClassInitializer classInitializer) {
        return (PsiClassInitializer) unwrap((UniElement) classInitializer);
    }

    public static @NotNull PsiCodeBlock unwrap(UniBlock block) {
        return (PsiCodeBlock) unwrap((UniElement) block);
    }

    public static @NotNull PsiCatchSection unwrap(UniCatch section) {
        return (PsiCatchSection) unwrap((UniElement) section);
    }

    public static @NotNull PsiClass unwrap(UniClass uniClass) {
        return (PsiClass) unwrap((UniElement) uniClass);
    }

    public static @NotNull PsiStatement unwrap(UniDirective directive) {
        return unwrap((UniStatement) directive);
    }

    public static @NotNull PsiTypeParameter unwrap(UniTypeParameter typeParameter) {
        return (PsiTypeParameter) unwrap((UniElement) typeParameter);
    }

    public static @NotNull PsiMethod unwrap(UniMethod method) {
        return (PsiMethod) unwrap((UniElement) method);
    }

    public static @NotNull PsiClassType unwrap(UniClassType type) {
        return null;
    }

    public static @NotNull PsiTypeElement unwrapType(UniExpression expression) {
        return null;
    }

    // Types

    public static @NotNull PsiTypeElement unwrap(UniType type) {
        return null;
    }

    public static @NotNull PsiJavaCodeReferenceElement unwrapType(UniType type) {
        return null;
    }

    public static @NotNull PsiResourceListElement unwrapResource(UniResource element) {
        if (element instanceof IJResource<?> ijResource)
            return ijResource.getResourceElement();
        throw new IllegalArgumentException("Element must be an IJResource");
    }

    public static @NotNull PsiJavaCodeReferenceElement unwrapReference(UniExpression expression) {
        return null;
    }

    public static @NotNull PsiJavaCodeReferenceElement unwrapReference(UniClassType type) {
        return null;
    }

    public static @NotNull PsiJavaCodeReferenceElement unwrapReferenceFromType(PsiClassType type) {
        return null;
    }

    public static @NotNull PsiJavaModuleReferenceElement unwrapModuleReference(String name) {
        return IntellijUniflow.getInstance().factory.createModuleReferenceFromText(name, null);
    }

    public static @NotNull @NonNls String unwrapTypeName(@NotNull UniClassType type) {
        PsiClassType classType = unwrap(type);

        return classType.getClassName();
    }

    public static @NotNull PsiJavaToken unwrap(UniOperatorSignature signature) {
        return null;
    }

    public static @NotNull PsiImportStatementBase unwrap(UniImportBase importBase) {
        return null;
    }
}
