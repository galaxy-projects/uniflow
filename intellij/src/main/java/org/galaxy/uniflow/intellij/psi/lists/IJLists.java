package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.elements.imports.UniImportBase;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IJLists {

    public static <T extends PsiAnnotationOwner & PsiElement> UniList<@NotNull UniAnnotation> annotations(T list) {
        return new IJList<>(
                list,
                PsiAnnotationOwner::getAnnotations,
                UniAnnotation[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    public static UniList<@NotNull UniExpression> expressions(@Nullable PsiExpressionList list) {
        return new IJList<@Nullable PsiExpressionList, PsiExpression, UniExpression>(
                list,
                PsiExpressionList::getExpressions,
                UniExpression[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    public static UniList<@NotNull UniExpression> arrayInitializers(@Nullable PsiArrayInitializerExpression list) {
        return new IJList<@Nullable PsiArrayInitializerExpression, PsiExpression, UniExpression>(
                list,
                PsiArrayInitializerExpression::getInitializers,
                UniExpression[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    public static UniList<@NotNull UniStatement> block(@NotNull PsiCodeBlock body) {
        return new IJList<>(
                body,
                PsiCodeBlock::getStatements,
                UniStatement[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    public static UniList<@NotNull UniClassInitializer> classInitializers(PsiClass element) {
        return new IJList<>(
                element,
                PsiClass::getInitializers,
                UniClassInitializer[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    public static UniList<@NotNull UniCaseLabel> caseLabels(PsiCaseLabelElementList labels) {
        return new IJList<>(
                labels,
                PsiCaseLabelElementList::getElements,
                UniCaseLabel[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    public static UniList<@NotNull UniCatch> catches(PsiTryStatement element) {
        return new IJList<>(
                element,
                PsiTryStatement::getCatchSections,
                UniCatch[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    public static UniList<@NotNull UniClass> innerClasses(PsiClass element) {
        return new IJList<>(
                element,
                PsiClass::getInnerClasses,
                UniClass[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    public static UniList<@NotNull UniExpression> referenceList(PsiReferenceList referenceList) {
        return new IJList<>(
                referenceList,
                PsiReferenceList::getReferenceElements,
                UniExpression[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrapReference
        );
    }

    public static UniList<@NotNull UniType> referenceTypeList(PsiReferenceList referenceList) {
        return new IJList<>(
                referenceList,
                PsiReferenceList::getReferenceElements,
                UniType[]::new,
                UniflowWrapper::wrapClassType,
                IntellijUnwrapper::unwrapType
        );
    }

    public static UniList<@NotNull UniElement> resources(PsiResourceList resources) {
        return new IJList<>(
                resources,
                list -> {
                    List<PsiResourceListElement> elements = new ArrayList<>();

                    list.forEach(elements::add);
                    return elements.toArray(PsiResourceListElement[]::new);
                },
                UniElement[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrapResource
        );
    }

    public static UniList<@NotNull UniType> types(PsiReferenceParameterList list) {
        return new IJList<>(
                list,
                PsiReferenceParameterList::getTypeParameterElements,
                UniType[]::new,
                UniflowWrapper::wrapAsType,
                IntellijUnwrapper::unwrap
        );
    }

    public static UniList<@NotNull UniTypeParameter> typeParameters(PsiTypeParameterList typeParameters) {
        return new IJList<>(
                typeParameters,
                PsiTypeParameterList::getTypeParameters,
                UniTypeParameter[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    public static UniList<@NotNull UniImportBase> imports(PsiImportList imports) {
        return new IJList<>(
                imports,
                PsiImportList::getAllImportStatements,
                UniImportBase[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    public static @NotNull UniList<@NotNull UniClass> classes(PsiJavaFile element) {
        return new IJList<>(
                element,
                PsiJavaFile::getClasses,
                UniClass[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }
}
