package org.galaxy.uniflow.intellij.psi.util;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.api.statements.UniParameter;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.statements.IJCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class UniflowWrapper {

    public static @NotNull UniOperatorSignature wrap(PsiJavaToken token) {
        return null;
    }

    public static @NotNull UniModifiers wrap(PsiModifierList modifierList) {
        return null;
    }

    public static @NotNull UniPattern wrap(PsiPattern pattern) {
        return null;
    }

    public static @NotNull UniAnnotation wrap(PsiAnnotation annotation) {
        return null;
    }

    public static @NotNull UniExpression wrap(PsiExpression expression) {
        Objects.requireNonNull(expression, "Expression is null");
        return null;
    }

    public static @NotNull UniExpression wrap(PsiJavaCodeReferenceElement classReference) {
        return null;
    }

    public static @NotNull UniCase wrap(PsiSwitchLabelStatementBase caseLabel, List<PsiStatement> statements) {
        return caseLabel instanceof PsiSwitchStatement ?
                new IJCase.IJStatementCase((PsiSwitchLabelStatement) caseLabel, statements) :
                new IJCase.IJRuleCase((PsiSwitchLabeledRuleStatement) caseLabel);
    }

    public static @NotNull UniCaseLabel wrap(PsiCaseLabelElement caseLabel) {
        return null;
    }

    public static @NotNull UniStatement wrap(PsiStatement statement) {
        return null;
    }

    public static @NotNull UniElement wrap(PsiElement element) {
        return null;
    }

    public static @NotNull UniVariable wrap(PsiVariable variable) {
        return null;
    }

    public static @NotNull UniParameter wrap(PsiParameter parameter) {
        return null;
    }

    public static @NotNull UniType wrap(PsiType type) {
        return null;
    }

    public static @NotNull UniType wrapAsType(@Nullable PsiExpression expression) {
        return null;
    }

    public static @NotNull UniType wrapAsType(PsiTypeElement type) {
        return null;
    }
}
