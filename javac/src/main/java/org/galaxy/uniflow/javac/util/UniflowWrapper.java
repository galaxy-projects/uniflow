package org.galaxy.uniflow.javac.util;

import com.sun.tools.javac.code.Source;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniOperatorExpression;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.api.statements.*;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.javac.*;
import org.galaxy.uniflow.javac.annotations.JavacAnnotation;
import org.galaxy.uniflow.javac.annotations.JavacSimpleAnnotationHolder;
import org.galaxy.uniflow.javac.elements.JavacCaseLabel;
import org.galaxy.uniflow.javac.elements.JavacCatch;
import org.galaxy.uniflow.javac.elements.JavacDefaultCaseLabel;
import org.galaxy.uniflow.javac.expression.*;
import org.galaxy.uniflow.javac.signatures.JavacOperatorSignature;
import org.galaxy.uniflow.javac.statements.*;
import org.galaxy.uniflow.javac.types.*;
import org.galaxy.uniflow.javac12.statements.JavacYield;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class UniflowWrapper {

    public static @NotNull String expressionToString(JCTree.JCExpression expression) {
        if (expression instanceof JCTree.JCIdent)
            return NameUtils.nameToString(((JCTree.JCIdent) expression).name);
        throw new IllegalArgumentException("Unknown expression: " + expression);
    }

    // Globals

    public static @NotNull UniElement wrap(JCTree element) {
        VersionedWrapper wrapper = JavacUniflow.getInstance().getVersionedWrapper();

        if (wrapper != null) {
            UniElement result = wrapper.wrap(element);

            if (result != null)
                return result;
        }
        if (element instanceof JCTree.JCAnnotation)
            return new JavacAnnotation((JCTree.JCAnnotation) element);
        else if (element instanceof JCTree.JCCatch)
            return new JavacCatch((JCTree.JCCatch) element);
        else if (element instanceof JCTree.JCExpression)
            return wrap((JCTree.JCExpression) element);
        else if (element instanceof JCTree.JCStatement)
            return wrap((JCTree.JCStatement) element);
        else if (element instanceof JCTree.JCPattern)
            return wrap((JCTree.JCPattern) element);
        else if (element instanceof JCTree.JCCaseLabel)
            return wrap((JCTree.JCCaseLabel) element);
        else if (element instanceof JCTree.JCDirective)
            return wrap((JCTree.JCDirective) element);
        else if (element instanceof JCTree.JCCompilationUnit)
            return new JavacCompilationUnit((JCTree.JCCompilationUnit) element);
        else if (element instanceof JCTree.JCImport)
            return new JavacImport((JCTree.JCImport) element);
        else if (element instanceof JCTree.JCMethodDecl)
            return new JavacMethod((JCTree.JCMethodDecl) element);
        else if (element instanceof JCTree.JCModifiers)
            return new JavacModifiers((JCTree.JCModifiers) element);
        else if (element instanceof JCTree.JCPackageDecl)
            return new JavacPackage((JCTree.JCPackageDecl) element);
        throw new IllegalArgumentException("Unknown element: " + element);
    }

    public static @NotNull UniOperatorExpression wrap(JCTree.JCOperatorExpression expression) {
        VersionedWrapper wrapper = JavacUniflow.getInstance().getVersionedWrapper();

        if (wrapper != null) {
            UniElement result = wrapper.wrap(expression);

            if (result != null)
                return (UniOperatorExpression) result;
        }
        if (expression instanceof JCTree.JCBinary)
            return new JavacBinary((JCTree.JCBinary) expression);
        else if (expression instanceof JCTree.JCAssignOp)
            return new JavacCompoundAssignment((JCTree.JCAssignOp) expression);
        else if (expression instanceof JCTree.JCUnary)
            return new JavacUnary((JCTree.JCUnary) expression);
        throw new IllegalArgumentException("Unknown operator: " + expression);
    }

    public static @NotNull UniExpression wrap(JCTree.JCExpression expression) {
        VersionedWrapper wrapper = JavacUniflow.getInstance().getVersionedWrapper();

        if (wrapper != null) {
            UniElement result = wrapper.wrap(expression);

            if (result != null)
                return (UniExpression) result;
        }
        if (expression instanceof JCTree.JCArrayAccess)
            return new JavacArrayAccess((JCTree.JCArrayAccess) expression);
        else if (expression instanceof JCTree.JCAssign)
            return new JavacAssignment((JCTree.JCAssign) expression);
        else if (expression instanceof JCTree.JCConditional)
            return new JavacConditional((JCTree.JCConditional) expression);
        else if (expression instanceof JCTree.JCErroneous)
            return new JavacErroneous((JCTree.JCErroneous) expression);
        else if (expression instanceof JCTree.JCFieldAccess)
            return new JavacFieldAccess((JCTree.JCFieldAccess) expression);
        else if (expression instanceof JCTree.JCIdent)
            return new JavacIdentifier((JCTree.JCIdent) expression);
        else if (expression instanceof JCTree.JCInstanceOf)
            return new JavacInstanceOf((JCTree.JCInstanceOf) expression);
        else if (expression instanceof JCTree.JCLambda)
            return new JavacLambda((JCTree.JCLambda) expression);
        else if (expression instanceof JCTree.JCLiteral)
            return new JavacLiteral((JCTree.JCLiteral) expression);
        else if (expression instanceof JCTree.JCMethodInvocation)
            return new JavacMethodInvocation((JCTree.JCMethodInvocation) expression);
        else if (expression instanceof JCTree.JCNewArray)
            return new JavacNewArray((JCTree.JCNewArray) expression);
        else if (expression instanceof JCTree.JCNewClass)
            return new JavacNewClass((JCTree.JCNewClass) expression);
        else if (expression instanceof JCTree.JCOperatorExpression)
            return wrap((JCTree.JCOperatorExpression) expression);
        else if (expression instanceof JCTree.JCParens)
            return new JavacParenthesized((JCTree.JCParens) expression);
        else if (expression instanceof JCTree.JCTypeCast)
            return new JavacTypeCast((JCTree.JCTypeCast) expression);
        return new JavacExpression<>(expression);
    }

    public static @NotNull UniStatement wrap(JCTree.JCStatement statement) {
        VersionedWrapper wrapper = JavacUniflow.getInstance().getVersionedWrapper();

        if (wrapper != null) {
            UniElement result = wrapper.wrap(statement);

            if (result != null)
                return (UniStatement) result;
        }
        if (statement instanceof JCTree.JCAssert)
            return new JavacAssert((JCTree.JCAssert) statement);
        else if (statement instanceof JCTree.JCBlock)
            return new JavacBlock((JCTree.JCBlock) statement);
        else if (statement instanceof JCTree.JCBreak)
            return new JavacBreak((JCTree.JCBreak) statement);
        else if (statement instanceof JCTree.JCCase) {
            return wrap((JCTree.JCCase) statement);
        } else if (statement instanceof JCTree.JCClassDecl)
            return new JavacClass((JCTree.JCClassDecl) statement);
        else if (statement instanceof JCTree.JCContinue)
            return new JavacContinue((JCTree.JCContinue) statement);
        else if (statement instanceof JCTree.JCDoWhileLoop)
            return new JavacDoWhileLoop((JCTree.JCDoWhileLoop) statement);
        else if (statement instanceof JCTree.JCSkip)
            return new JavacEmpty((JCTree.JCSkip) statement);
        else if (statement instanceof JCTree.JCEnhancedForLoop)
            return new JavacEnhancedForLoop((JCTree.JCEnhancedForLoop) statement);
        else if (statement instanceof JCTree.JCExpressionStatement)
            return new JavacExpressionStatement((JCTree.JCExpressionStatement) statement);
        else if (statement instanceof JCTree.JCForLoop)
            return new JavacForLoop((JCTree.JCForLoop) statement);
        else if (statement instanceof JCTree.JCIf)
            return new JavacIf((JCTree.JCIf) statement);
        else if (statement instanceof JCTree.JCLabeledStatement)
            return new JavacLabel((JCTree.JCLabeledStatement) statement);
        else if (statement instanceof JCTree.JCReturn)
            return new JavacReturn((JCTree.JCReturn) statement);
        else if (statement instanceof JCTree.JCSwitch)
            return new JavacSwitch((JCTree.JCSwitch) statement);
        else if (statement instanceof JCTree.JCSynchronized)
            return new JavacSynchronized((JCTree.JCSynchronized) statement);
        else if (statement instanceof JCTree.JCThrow)
            return new JavacThrow((JCTree.JCThrow) statement);
        else if (statement instanceof JCTree.JCTry)
            return new JavacTry((JCTree.JCTry) statement);
        else if (statement instanceof JCTree.JCVariableDecl) {
            JCTree.JCVariableDecl var = (JCTree.JCVariableDecl) statement;

            if (var.sym != null && var.sym.owner instanceof Symbol.ClassSymbol)
                return new JavacField(var);
            return new JavacVariable(var);
        } else if (statement instanceof JCTree.JCWhileLoop)
            return new JavacWhileLoop((JCTree.JCWhileLoop) statement);
        else if (statement instanceof JCTree.JCYield)
            return new JavacYield((JCTree.JCYield) statement);
        return new JavacStatement<>(statement);
    }

    // Specifics

    public static @NotNull UniAnnotation wrap(JCTree.JCAnnotation annotation) {
        return new JavacAnnotation(annotation);
    }

    public static @NotNull UniOperatorSignature wrap(Symbol.OperatorSymbol operator) {
        return new JavacOperatorSignature(operator);
    }

    public static @NotNull UniVariable wrap(JCTree.JCVariableDecl variable) {
        return new JavacVariable(variable);
    }

    public static @NotNull UniParameter wrapParameter(JCTree.JCVariableDecl variable) {
        return new JavacParameter(variable);
    }

    public static @NotNull UniField wrapField(JCTree.JCVariableDecl field) {
        return new JavacField(field);
    }

    public static @NotNull UniAnnotationHolder wrap(Consumer<List<JCTree.JCAnnotation>> updater,
                                                    List<JCTree.JCAnnotation> annotations) {
        return new JavacSimpleAnnotationHolder(updater, annotations);
    }

    public static @NotNull UniCaseBase wrap(JCTree.JCCase jcCase) {
        if (JavacUniflow.getInstance().source.compareTo(Source.JDK12) >= 0) {
            VersionedWrapper wrapper = JavacUniflow.getInstance().getVersionedWrapper();
            UniElement result = wrapper != null ? wrapper.wrap(jcCase) : null;

            if (result != null)
                return (UniCaseBase) result;
            throw new IllegalArgumentException("Unknown statement: " + jcCase);
        }
        return new JavacCase(jcCase);
    }

    public static @NotNull UniBlock wrap(JCTree.JCBlock block) {
        return new JavacBlock(block);
    }

    public static @NotNull UniClassInitializer blockToInitializer(JCTree.JCBlock block) {
        return new JavacClassInitializer(block);
    }

    public static @NotNull UniExpressionStatement wrap(JCTree.JCExpressionStatement statement) {
        return new JavacExpressionStatement(statement);
    }

    public static @NotNull UniCatch wrap(JCTree.JCCatch catcher) {
        return new JavacCatch(catcher);
    }

    public static @NotNull UniCaseLabel wrap(JCTree.JCCaseLabel caseLabel) {
        if (caseLabel instanceof JCTree.JCDefaultCaseLabel)
            return new JavacDefaultCaseLabel((JCTree.JCDefaultCaseLabel) caseLabel);
        else if (Reflection.EXPRESSION_TYPE.isInstance(caseLabel))
            return wrap(Reflection.EXPRESSION_TYPE.cast(caseLabel));
        return new JavacCaseLabel(caseLabel);
    }

    public static @NotNull UniTypeParameter wrap(JCTree.JCTypeParameter typeParameter) {
        return new JavacTypeParameter(typeParameter);
    }

    public static @NotNull UniPackage wrap(JCTree.JCPackageDecl pkg) {
        return new JavacPackage(pkg);
    }

    public static @NotNull UniImport wrap(JCTree.JCImport jcImport) {
        return new JavacImport(jcImport);
    }

    public static @NotNull UniClass wrap(JCTree.JCClassDecl clazz) {
        return new JavacClass(clazz);
    }

    public static @NotNull UniMethod wrap(JCTree.JCMethodDecl method) {
        return new JavacMethod(method);
    }

    // Types

    public static @NotNull UniType type(Symbol symbol) {
        JCTree tree = JavacUniflow.getInstance().trees.getTree(symbol);

        if (tree != null)
            return typeFromTree(tree);
        return type(symbol.type);
    }

    public static @NotNull UniType type(Type type) {
        if (type instanceof Type.ArrayType)
            return new JavacArrayType(null, (Type.ArrayType) type);
        else if (type instanceof Type.IntersectionClassType)
            return new JavacIntersectionType(null, (Type.IntersectionClassType) type);
        else if (type instanceof Type.UnionClassType)
            return new JavacUnionType(null, (Type.UnionClassType) type);
        else if (type instanceof Type.ClassType) {
            Type.ClassType ct = (Type.ClassType) type;

            if (ct.isParameterized())
                return new JavacParameterizedType(null, ct);
            return new JavacClassType((JCTree.JCIdent) null, (Type.ClassType) type);
        } else if (type instanceof Type.JCPrimitiveType)
            return new JavacPrimitiveType(null, (Type.JCPrimitiveType) type);
        else if (type instanceof Type.WildcardType)
            return new JavacWildcardType(null, (Type.WildcardType) type);
        return new JavacType<>(null, type);
    }

    public static @NotNull UniType typeFromTree(JCTree tree) {
        if (tree instanceof JCTree.JCArrayTypeTree)
            return new JavacArrayType((JCTree.JCArrayTypeTree) tree, (Type.ArrayType) tree.type);
        else if (tree instanceof JCTree.JCTypeIntersection)
            return new JavacIntersectionType((JCTree.JCTypeIntersection) tree, (Type.IntersectionClassType) tree.type);
        else if (tree instanceof JCTree.JCTypeUnion)
            return new JavacUnionType((JCTree.JCTypeUnion) tree, (Type.UnionClassType) tree.type);
        else if (tree instanceof JCTree.JCTypeApply)
            return new JavacParameterizedType((JCTree.JCTypeApply) tree, (Type.ClassType) tree.type);
        else if (tree instanceof JCTree.JCIdent)
            return new JavacClassType((JCTree.JCIdent) tree, (Type.ClassType) tree.type);
        else if (tree instanceof JCTree.JCPrimitiveTypeTree) {
            if (tree.type instanceof Type.JCPrimitiveType)
                return new JavacPrimitiveType((JCTree.JCPrimitiveTypeTree) tree, (Type.JCPrimitiveType) tree.type);
            else if (tree.type instanceof Type.JCVoidType)
                return new JavacExpressionType<>((JCTree.JCPrimitiveTypeTree) tree, tree.type);
        } else if (tree instanceof JCTree.JCWildcard)
            return new JavacWildcardType((JCTree.JCWildcard) tree, (Type.WildcardType) tree.type);
        return new JavacType<>(tree, tree.type);
    }

    public static @NotNull UniClassType symbolToType(Symbol owner) {
        return (UniClassType) typeFromTree(JavacUniflow.getInstance().trees.getTree(owner));
    }
}
