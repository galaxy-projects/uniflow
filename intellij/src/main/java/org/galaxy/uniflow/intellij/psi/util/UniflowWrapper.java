package org.galaxy.uniflow.intellij.psi.util;

import com.intellij.psi.*;
import com.intellij.psi.tree.IElementType;
import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.elements.imports.UniImportBase;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.api.statements.*;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.intellij.psi.*;
import org.galaxy.uniflow.intellij.psi.elements.*;
import org.galaxy.uniflow.intellij.psi.elements.imports.IJImport;
import org.galaxy.uniflow.intellij.psi.elements.imports.IJModuleImport;
import org.galaxy.uniflow.intellij.psi.elements.imports.IJStaticImport;
import org.galaxy.uniflow.intellij.psi.expression.*;
import org.galaxy.uniflow.intellij.psi.modules.IJModule;
import org.galaxy.uniflow.intellij.psi.modules.directives.*;
import org.galaxy.uniflow.intellij.psi.pattern.IJAnyPattern;
import org.galaxy.uniflow.intellij.psi.pattern.IJBindingPattern;
import org.galaxy.uniflow.intellij.psi.pattern.IJRecordPattern;
import org.galaxy.uniflow.intellij.psi.signature.IJOperatorSignature;
import org.galaxy.uniflow.intellij.psi.statements.*;
import org.galaxy.uniflow.intellij.psi.types.*;
import org.galaxy.uniflow.intellij.psi.types.elements.IJExpressionType;
import org.galaxy.uniflow.intellij.psi.types.elements.IJTypeElementType;
import org.galaxy.uniflow.intellij.psi.types.elements.IJTypeParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class UniflowWrapper {

    // Globals

    public static @NotNull UniElement wrap(PsiElement element) {
        if (element instanceof PsiAnnotation annotation)
            return new IJAnnotation(annotation);
        else if (element instanceof PsiCatchSection catchSection)
            return new IJCatch(catchSection);
        else if (element instanceof PsiExpression expression)
            return wrap(expression);
        else if (element instanceof PsiStatement statement)
            return wrap(statement);
        else if (element instanceof PsiPattern pattern)
            return wrap(pattern);
        else if (element instanceof PsiCaseLabelElement caseLabel)
            return wrap(caseLabel);
        else if (element instanceof PsiJavaModule module)
            return new IJModule(module);
        else if (element instanceof PsiImportStatementBase importStatement)
            return wrap(importStatement);
        else if (element instanceof PsiMethod method)
            return wrap(method);
        else if (element instanceof PsiModifierList modifiers)
            return wrap(modifiers);
        else if (element instanceof PsiParameter parameter)
            return wrap(parameter);
        else if (element instanceof PsiVariable variable)
            return wrap(variable);
        else if (element instanceof PsiPackageStatement psiPackage)
            return wrap(psiPackage);
        throw new IllegalArgumentException("Unknown element: " + element);
    }

    public static @NotNull UniExpression wrap(PsiExpression expression) {
        Objects.requireNonNull(expression, "Expression is null");

        switch (expression) {
            case PsiArrayAccessExpression arrayAccess -> {
                return new IJArrayAccess(arrayAccess);
            }
            case PsiAssignmentExpression assignment -> {
                IElementType tokenType = assignment.getOperationSign().getTokenType();

                if (IJCompoundAssignment.ASSIGNMENT_KIND_MAP.containsKey(tokenType))
                    return new IJCompoundAssignment(assignment);
                return new IJAssignment(assignment);
            }
            case PsiConditionalExpression conditional -> {
                return new IJConditional(conditional);
            }
            case PsiReferenceExpression referenceExpression -> {
                return new IJFieldAccess(referenceExpression);
            }
            case PsiIdentifier identifier -> {
                return new IJIdentifier(identifier);
            }
            case PsiInstanceOfExpression instanceOf -> {
                return new IJInstanceOf(instanceOf);
            }
            case PsiLambdaExpression lambda -> {
                return new IJLambda(lambda);
            }
            case PsiLiteralExpression literal -> {
                return new IJLiteral(literal);
            }
            case PsiMethodCallExpression methodCall -> {
                return new IJMethodInvocation(methodCall);
            }
            case PsiNewExpression newExpression -> {
                if (newExpression.isArrayCreation())
                    return new IJNewArray(newExpression);
                return new IJNewClass(newExpression);
            }
            case PsiBinaryExpression binary -> {
                return new IJBinary(binary);
            }
            case PsiUnaryExpression unary -> {
                return new IJUnary(unary);
            }
            case PsiParenthesizedExpression parenthesized -> {
                return new IJParenthesized(parenthesized);
            }
            case PsiTypeCastExpression typeCast -> {
                return new IJTypeCast(typeCast);
            }
            case PsiSwitchExpression switchExpression -> {
                return new IJSwitchExpression(switchExpression);
            }
            default -> {
            }
        }

        return new IJExpression<>(expression) {
            @Override
            public @NotNull Kind getKind() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static @NotNull UniStatement wrap(PsiStatement statement) {
        if (statement instanceof PsiAssertStatement assertStatement)
            return new IJAssert(assertStatement);
        else if (statement instanceof PsiBlockStatement block)
            return new IJBlockStatement(block);
        else if (statement instanceof PsiBreakStatement breakStatement)
            return new IJBreak(breakStatement);
        else if (statement instanceof PsiSwitchLabelStatementBase switchCase)
            return wrap(switchCase);
        else if (statement instanceof PsiClass psiClass)
            return new IJClass(psiClass);
        else if (statement instanceof PsiContinueStatement continueStatement)
            return new IJContinue(continueStatement);
        else if (statement instanceof PsiDoWhileStatement doWhile)
            return new IJDoWhileLoop(doWhile);
        else if (statement instanceof PsiEmptyStatement empty)
            return new IJEmpty(empty);
        else if (statement instanceof PsiForeachStatement forEach)
            return new IJEnhancedForLoop(forEach);
        else if (statement instanceof PsiExpressionStatement expressionStatement)
            return new IJExpressionStatement(expressionStatement);
        else if (statement instanceof PsiForStatement forLoop)
            return new IJForLoop(forLoop);
        else if (statement instanceof PsiIfStatement ifStatement)
            return new IJIf(ifStatement);
        else if (statement instanceof PsiLabeledStatement label)
            return new IJLabel(label);
        else if (statement instanceof PsiReturnStatement returnStatement)
            return new IJReturn(returnStatement);
        else if (statement instanceof PsiSwitchStatement switchStatement)
            return new IJSwitchStatement(switchStatement);
        else if (statement instanceof PsiSynchronizedStatement synchronizedStatement)
            return new IJSynchronized(synchronizedStatement);
        else if (statement instanceof PsiThrowStatement throwStatement)
            return new IJThrow(throwStatement);
        else if (statement instanceof PsiTryStatement tryStatement)
            return new IJTry(tryStatement);
        else if (statement instanceof PsiWhileStatement whileLoop)
            return new IJWhileLoop(whileLoop);
        else if (statement instanceof PsiYieldStatement yield)
            return new IJYield(yield);
        else if (statement instanceof PsiRequiresStatement requires)
            return new IJRequires(requires);
        else if (statement instanceof PsiPackageAccessibilityStatement exportsOrOpens) {
            if (exportsOrOpens.getRole() == PsiPackageAccessibilityStatement.Role.EXPORTS)
                return new IJExports(exportsOrOpens);
            return new IJOpens(exportsOrOpens);
        } else if (statement instanceof PsiUsesStatement uses)
            return new IJUses(uses);
        else if (statement instanceof PsiProvidesStatement provides)
            return new IJProvides(provides);

        return new IJStatement<>(statement) {
            @Override
            public @NotNull Kind getKind() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // References
    public static @NotNull UniExpression wrap(PsiJavaCodeReferenceElement classReference) {
        return new IJReference(classReference);
    }

    // Specifics

    public static @NotNull UniModule wrap(PsiJavaModule module) {
        return new IJModule(module);
    }

    public static @NotNull UniPackage wrap(PsiPackageStatement psiPackage) {
        return new IJPackage(psiPackage);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static @NotNull UniImportBase wrap(PsiImportStatementBase importBase) {
        if (importBase instanceof PsiImportStaticStatement staticImport)
            return new IJStaticImport(staticImport);
        else if (importBase instanceof PsiImportStatement justImport)
            return new IJImport(justImport);
        else if (importBase instanceof PsiImportModuleStatement importModule) // jdk 25
            return new IJModuleImport(importModule);
        throw new IllegalArgumentException("Unknown import: " + importBase);
    }

    public static @NotNull UniCaseBase wrap(PsiSwitchLabelStatementBase switchCase) {
        if (switchCase instanceof PsiSwitchLabeledRuleStatement ruleCase)
            return new IJCase.IJRuleCase(ruleCase);
        else if (switchCase instanceof PsiSwitchLabelStatement statementCase)
            return new IJCase.IJStatementCase(statementCase, List.of());
        throw new IllegalArgumentException("Unknown switch case: " + switchCase);
    }

    public static @NotNull UniOperatorSignature wrap(PsiJavaToken token) {
        return new IJOperatorSignature(token);
    }

    public static @NotNull UniModifiers wrap(PsiModifierList modifiers) {
        return new IJModifiers(modifiers);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static @NotNull UniPattern wrap(PsiPattern pattern) {
        return switch (pattern) {
            case PsiUnnamedPattern unnamed -> new IJAnyPattern(unnamed);
            case PsiDeconstructionPattern deconstruction -> new IJRecordPattern(deconstruction);
            case PsiTypeTestPattern typeTest -> new IJBindingPattern(typeTest);
            case null -> throw new IllegalArgumentException("Null pattern");
            default -> throw new IllegalArgumentException("Unknown pattern: " + pattern);
        };
    }

    public static @NotNull UniAnnotation wrap(PsiAnnotation annotation) {
        return new IJAnnotation(annotation);
    }

    public static @NotNull UniJdk21Case wrap(PsiSwitchLabelStatementBase caseLabel, List<PsiStatement> statements) {
        return caseLabel instanceof PsiSwitchStatement ?
                new IJCase.IJStatementCase((PsiSwitchLabelStatement) caseLabel, statements) :
                new IJCase.IJRuleCase((PsiSwitchLabeledRuleStatement) caseLabel);
    }

    public static @NotNull UniTypeParameter wrap(PsiTypeParameter typeParameter) {
        return new IJTypeParameter(typeParameter);
    }

    public static @NotNull UniClass wrap(PsiClass psiClass) {
        return new IJClass(psiClass);
    }

    public static @NotNull UniCaseLabel wrap(PsiCaseLabelElement caseLabel) {
        return switch (caseLabel) {
            case PsiPattern pattern -> wrap(pattern);
            case PsiDefaultCaseLabelElement defaultCaseLabel -> new IJDefaultCaseLabel(defaultCaseLabel);
            case null -> throw new IllegalArgumentException("Null case label");
            default -> new IJCaseLabel(caseLabel);
        };
    }

    public static @NotNull UniBlock wrap(PsiCodeBlock block) {
        return new IJBlock(block);
    }

    public static @NotNull UniCatch wrap(PsiCatchSection section) {
        return new IJCatch(section);
    }

    public static @NotNull UniAnnotationValue wrap(PsiAnnotationMemberValue memberValue) {
        return switch (memberValue) {
            case PsiLiteral literal -> wrap(literal);
            case PsiAnnotation annotation -> wrap(annotation);
            case PsiReferenceExpression access -> new IJFieldAccess(access);
            case PsiNewExpression newExpr -> {
                if (newExpr.isArrayCreation()) yield new IJNewArray(newExpr);
                throw new IllegalArgumentException("new instance is not accepted in annotation values");
            }
            case null, default -> throw new IllegalArgumentException("Unknown annotation value: " + memberValue);
        };
    }

    public static @NotNull UniAnnotationAttribute wrap(PsiNameValuePair pair) {
        return new IJAnnotationAttribute(pair);
    }

    public static @NotNull UniMethod wrap(PsiMethod method) {
        return new IJMethod(method);
    }

    public static @NotNull UniClassInitializer wrap(PsiClassInitializer classInitializer) {
        return new IJClassInitializer(classInitializer);
    }

    public static @NotNull UniVariable wrap(PsiVariable variable) {
        return new IJVariable(variable);
    }

    public static @NotNull UniField wrap(PsiField field) {
        return new IJField(field);
    }

    public static @NotNull UniParameter wrap(PsiParameter parameter) {
        return new IJParameter(parameter);
    }

    // Types

    public static @NotNull UniType wrap(PsiType type) {
        if (type instanceof PsiPrimitiveType primitive)
            return new IJPrimitiveType(primitive);
        else if (type instanceof PsiArrayType array)
            return new IJArrayType(array);
        else if (type instanceof PsiWildcardType wildcard)
            return new IJWildcardType(wildcard);
        else if (type instanceof PsiClassType classType) {
            if (classType.isRaw())
                return new IJClassType(classType);
            return new IJParameterizedType(classType);
        } else if (type instanceof PsiDisjunctionType disjunction)
            return new IJUnionType(disjunction);
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    public static @NotNull UniType wrapAsType(@Nullable PsiExpression expression) {
        return new IJExpressionType<>(expression);
    }

    public static @NotNull UniType wrapAsType(PsiTypeElement type) {
        return new IJTypeElementType(type);
    }

    public static @NotNull UniClassType wrapClassType(@NotNull PsiClass psiClass) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;

        return new IJClassType(factory.createType(psiClass));
    }

    public static @NotNull UniClassType wrapClassType(@NotNull PsiClassType type) {
        return new IJClassType(type);
    }

    public static @NotNull UniClassType wrapClassType(@Nullable PsiJavaCodeReferenceElement codeReference) {
        if (codeReference == null) throw new IllegalArgumentException("Null code reference");
        PsiReference reference = codeReference.getReference();
        PsiElement resolved = reference != null ? reference.resolve() : null;

        if (resolved instanceof PsiClass psiClass)
            return wrapClassType(psiClass);
        throw new IllegalArgumentException("Unknown reference: " + codeReference);
    }
}
