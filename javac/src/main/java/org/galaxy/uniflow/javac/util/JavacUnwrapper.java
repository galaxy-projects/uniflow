package org.galaxy.uniflow.javac.util;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniExpressionStatement;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.JavacModifiers;
import org.galaxy.uniflow.javac.JavacPackage;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.expression.JavacNewArray;
import org.galaxy.uniflow.javac.signatures.JavacFieldSignature;
import org.galaxy.uniflow.javac.signatures.JavacMethodSignature;
import org.galaxy.uniflow.javac.signatures.JavacOperatorSignature;
import org.galaxy.uniflow.javac.types.JavacExpressionType;
import org.galaxy.uniflow.javac.types.JavacType;
import org.galaxy.uniflow.javac.types.JavacTypeParameter;
import org.jetbrains.annotations.NotNull;

public class JavacUnwrapper {

    // Types

    public static @NotNull Type unwrap(UniType type) {
        if (type instanceof JavacType<?, ?>)
            return ((JavacType<?, ?>) type).getRawType();
        throw new IllegalArgumentException("Type not supported: " + type);
    }

    public static @NotNull Type.JCPrimitiveType tagToPrimitiveType(@NotNull TypeTag typeTag) {
        Symtab symtab = JavacUniflow.getInstance().symtab;

        switch (typeTag) {
            case BYTE:
                return symtab.byteType;
            case SHORT:
                return symtab.shortType;
            case INT:
                return symtab.intType;
            case LONG:
                return symtab.longType;
            case FLOAT:
                return symtab.floatType;
            case DOUBLE:
                return symtab.doubleType;
            case BOOLEAN:
                return symtab.booleanType;
            case CHAR:
                return symtab.charType;

        }
        throw new IllegalArgumentException("Tag not supported: " + typeTag);
    }

    public static @NotNull Type tagToType(@NotNull TypeTag typeTag) {
        if (typeTag == TypeTag.VOID)
            return JavacUniflow.getInstance().symtab.voidType;
        return tagToPrimitiveType(typeTag);
    }

    public static JCTree.JCExpression typeToTree(@NotNull UniType type) {
        if (type instanceof JavacExpressionType<?, ?>)
            return ((JavacExpressionType<?, ?>) type).getExpression();
        throw new IllegalArgumentException("Type not supported: " + type);
    }

    public static @NotNull JCTree.JCTypeParameter unwrap(UniTypeParameter typeParameter) {
        return (JCTree.JCTypeParameter) unwrap((UniElement) typeParameter);
    }

    // Elements

    public static @NotNull JCTree unwrap(UniElement element) {
        if (element instanceof JavacElement)
            return ((JavacElement<? extends JCTree>) element).getTree();
        throw new IllegalArgumentException("Element not supported: " + element);
    }

    public static @NotNull JCTree.JCVariableDecl unwrap(UniVariable variable) {
        return (JCTree.JCVariableDecl) unwrap((UniElement) variable);
    }

    public static @NotNull JCTree.JCCaseLabel unwrap(UniCaseLabel caseLabel) {
        return (JCTree.JCCaseLabel) unwrap((UniElement) caseLabel);
    }

    public static @NotNull JCTree.JCCatch unwrap(UniCatch catcher) {
        return (JCTree.JCCatch) unwrap((UniElement) catcher);
    }

    public static @NotNull JCTree.JCBlock unwrap(UniBlock block) {
        return (JCTree.JCBlock) unwrap((UniElement) block);
    }

    public static @NotNull JCTree.JCStatement unwrap(UniStatement statement) {
        return (JCTree.JCStatement) unwrap((UniElement) statement);
    }

    public static @NotNull JCTree.JCExpressionStatement unwrap(UniExpressionStatement statement) {
        return (JCTree.JCExpressionStatement) unwrap((UniElement) statement);
    }

    public static @NotNull JCTree.JCDirective unwrap(UniDirective directive) {
        return (JCTree.JCDirective) unwrap((UniElement) directive);
    }

    public static @NotNull JCTree.JCPattern unwrap(UniPattern pattern) {
        return (JCTree.JCPattern) unwrap((UniElement) pattern);
    }

    public static @NotNull JCTree.JCCase unwrap(UniCase uniCase) {
        return (JCTree.JCCase) unwrap((UniElement) uniCase);
    }

    public static @NotNull JCTree.JCAnnotation unwrap(UniAnnotation annotation) {
        return (JCTree.JCAnnotation) unwrap((UniElement) annotation);
    }

    public static @NotNull List<JCTree.JCAnnotation> unwrap(UniAnnotationHolder holder) {
        if (holder instanceof JavacModifiers) {
            return ((JavacModifiers) holder).getTree().annotations;
        } else if (holder instanceof JavacNewArray) {
            return ((JavacNewArray) holder).getTree().annotations;
        } else if (holder instanceof JavacPackage) {
            return ((JavacPackage) holder).getTree().annotations;
        } else if (holder instanceof JavacTypeParameter) {
            return ((JavacTypeParameter) holder).getTree().annotations;
        }
        throw new IllegalArgumentException("Element not supported: " + holder);
    }

    public static @NotNull JCTree.JCExpression unwrap(UniExpression type) {
        return (JCTree.JCExpression) unwrap((UniElement) type);
    }

    public static JCTree.JCMethodDecl unwrap(UniMethod uniMethod) {
        return (JCTree.JCMethodDecl) unwrap((UniElement) uniMethod);
    }

    public static JCTree.JCBlock unwrap(UniClassInitializer block) {
        return (JCTree.JCBlock) unwrap((UniElement) block);
    }

    public static JCTree.JCClassDecl unwrap(UniClass uniClass) {
        return (JCTree.JCClassDecl) unwrap((UniElement) uniClass);
    }

    public static JCTree.JCModifiers unwrap(UniModifiers modifiers) {
        return (JCTree.JCModifiers) unwrap((UniElement) modifiers);
    }

    // Signatures

    public static @NotNull Symbol.OperatorSymbol unwrap(@NotNull UniOperatorSignature signature) {
        if (signature instanceof JavacOperatorSignature)
            return ((JavacOperatorSignature) signature).getSymbol();
        throw new IllegalArgumentException("Signature not supported: " + signature);
    }

    public static @NotNull Symbol.MethodSymbol unwrap(@NotNull UniMethodSignature signature) {
        if (signature instanceof JavacMethodSignature)
            return ((JavacMethodSignature) signature).getSymbol();
        throw new IllegalArgumentException("Signature not supported: " + signature);
    }

    public static @NotNull Symbol.VarSymbol unwrap(@NotNull UniFieldSignature signature) {
        if (signature instanceof JavacFieldSignature)
            return ((JavacFieldSignature) signature).getSymbol();
        throw new IllegalArgumentException("Signature not supported: " + signature);
    }
}
