package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.elements.UniDefaultCaseLabel;
import org.galaxy.uniflow.api.expressions.*;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.api.modules.directives.UniExports;
import org.galaxy.uniflow.api.modules.directives.UniProvides;
import org.galaxy.uniflow.api.modules.directives.UniRequires;
import org.galaxy.uniflow.api.modules.directives.UniUses;
import org.galaxy.uniflow.api.pattern.UniBindingPattern;
import org.galaxy.uniflow.api.pattern.UniGuardedPattern;
import org.galaxy.uniflow.api.pattern.UniParenthesizedPattern;
import org.galaxy.uniflow.api.statements.*;
import org.galaxy.uniflow.api.types.*;
import org.jetbrains.annotations.NotNull;

public interface UniElement {

    int getPosition();

    @NotNull Kind getKind();

    boolean hasTag(@NotNull Tag tag);

    Tag getTag();

    enum Tag {
        /**
         * For methods that return an invalid tag if a given condition is not met
         */
        NO_TAG,

        /**
         * Toplevel nodes, of type TopLevel, representing entire source files.
         */
        TOPLEVEL,

        /**
         * Package level definitions.
         */
        PACKAGEDEF,

        /**
         * Import clauses, of type Import.
         */
        IMPORT,

        /**
         * Class definitions, of type ClassDef.
         */
        CLASSDEF,

        /**
         * Method definitions, of type MethodDef.
         */
        METHODDEF,

        /**
         * Variable definitions, of type VarDef.
         */
        VARDEF,

        /**
         * The no-op statement ";", of type Skip
         */
        SKIP,

        /**
         * Blocks, of type Block.
         */
        BLOCK,

        /**
         * Do-while loops, of type DoLoop.
         */
        DOLOOP,

        /**
         * While-loops, of type WhileLoop.
         */
        WHILELOOP,

        /**
         * For-loops, of type ForLoop.
         */
        FORLOOP,

        /**
         * Foreach-loops, of type ForeachLoop.
         */
        FOREACHLOOP,

        /**
         * Labelled statements, of type Labelled.
         */
        LABELLED,

        /**
         * Switch statements, of type Switch.
         */
        SWITCH,

        /**
         * Case parts in switch statements/expressions, of type Case.
         */
        CASE,

        /**
         * Switch expression statements, of type Switch.
         */
        SWITCH_EXPRESSION,

        /**
         * Synchronized statements, of type Synchronized.
         */
        SYNCHRONIZED,

        /**
         * Try statements, of type Try.
         */
        TRY,

        /**
         * Catch clauses in try statements, of type Catch.
         */
        CATCH,

        /**
         * Conditional expressions, of type Conditional.
         */
        CONDEXPR,

        /**
         * Conditional statements, of type If.
         */
        IF,

        /**
         * Expression statements, of type Exec.
         */
        EXEC,

        /**
         * Break statements, of type Break.
         */
        BREAK,

        /**
         * Yield statements, of type Yield.
         */
        YIELD,

        /**
         * Continue statements, of type Continue.
         */
        CONTINUE,

        /**
         * Return statements, of type Return.
         */
        RETURN,

        /**
         * Throw statements, of type Throw.
         */
        THROW,

        /**
         * Assert statements, of type Assert.
         */
        ASSERT,

        /**
         * Method invocation expressions, of type Apply.
         */
        APPLY,

        /**
         * Class instance creation expressions, of type NewClass.
         */
        NEWCLASS,

        /**
         * Array creation expressions, of type NewArray.
         */
        NEWARRAY,

        /**
         * Lambda expression, of type Lambda.
         */
        LAMBDA,

        /**
         * Parenthesized subexpressions, of type Parens.
         */
        PARENS,

        /**
         * Assignment expressions, of type Assign.
         */
        ASSIGN,

        /**
         * Type cast expressions, of type TypeCast.
         */
        TYPECAST,

        /**
         * Type test expressions, of type TypeTest.
         */
        TYPETEST,

        /**
         * Patterns.
         */
        BINDINGPATTERN,
        DEFAULTCASELABEL,
        GUARDPATTERN,
        PARENTHESIZEDPATTERN,

        /**
         * Indexed array expressions, of type Indexed.
         */
        INDEXED,

        /**
         * Selections, of type Select.
         */
        SELECT,

        /**
         * Member references, of type Reference.
         */
        REFERENCE,

        /**
         * Simple identifiers, of type Ident.
         */
        IDENT,

        /**
         * Literals, of type Literal.
         */
        LITERAL,

        /**
         * Basic type identifiers, of type TypeIdent.
         */
        TYPEIDENT,

        /**
         * Array types, of type TypeArray.
         */
        TYPEARRAY,

        /**
         * Parameterized types, of type TypeApply.
         */
        TYPEAPPLY,

        /**
         * Union types, of type TypeUnion.
         */
        TYPEUNION,

        /**
         * Intersection types, of type TypeIntersection.
         */
        TYPEINTERSECTION,

        /**
         * Formal type parameters, of type TypeParameter.
         */
        TYPEPARAMETER,

        /**
         * Type argument.
         */
        WILDCARD,

        /**
         * Bound kind: extends, super, exact, or unbound
         */
        TYPEBOUNDKIND,

        /**
         * metadata: Annotation.
         */
        ANNOTATION,

        /**
         * metadata: Type annotation.
         */
        TYPE_ANNOTATION,

        /**
         * metadata: Modifiers
         */
        MODIFIERS,

        /**
         * An annotated type tree.
         */
        ANNOTATED_TYPE,

        /**
         * Error trees, of type Erroneous.
         */
        ERRONEOUS,

        /**
         * Unary operators, of type Unary.
         */
        POS,                             // +
        NEG,                             // -
        NOT,                             // !
        COMPL,                           // ~
        PREINC,                          // ++ _
        PREDEC,                          // -- _
        POSTINC,                         // _ ++
        POSTDEC,                         // _ --

        /**
         * unary operator for null reference checks, only used internally.
         */
        NULLCHK,

        /**
         * Binary operators, of type Binary.
         */
        OR,                              // ||
        AND,                             // &&
        BITOR,                           // |
        BITXOR,                          // ^
        BITAND,                          // &
        EQ,                              // ==
        NE,                              // !=
        LT,                              // <
        GT,                              // >
        LE,                              // <=
        GE,                              // >=
        SL,                              // <<
        SR,                              // >>
        USR,                             // >>>
        PLUS,                            // +
        MINUS,                           // -
        MUL,                             // *
        DIV,                             // /
        MOD,                             // %

        /**
         * Assignment operators, of type Assignop.
         */
        BITOR_ASG,                // |=
        BITXOR_ASG,              // ^=
        BITAND_ASG,              // &=

        SL_ASG,                      // <<=
        SR_ASG,                      // >>=
        USR_ASG,                    // >>>=
        PLUS_ASG,                  // +=
        MINUS_ASG,                // -=
        MUL_ASG,                    // *=
        DIV_ASG,                    // /=
        MOD_ASG,                    // %=

        MODULEDEF,
        EXPORTS,
        OPENS,
        PROVIDES,
        REQUIRES,
        USES,

        /**
         * A synthetic let expression, of type LetExpr.
         */
        LETEXPR
    }

    enum Kind {
        /**
         * Used for instances of {@link UniAnnotationHolder}
         * representing annotated types.
         */
        ANNOTATED_TYPE,

        /**
         * Used for instances of {@link UniAnnotation}
         * representing declaration annotations.
         */
        ANNOTATION,

        /**
         * Used for instances of {@link UniAnnotation}
         * representing type annotations.
         */
        TYPE_ANNOTATION,

        /**
         * Used for instances of {@link UniArrayAccess}.
         */
        ARRAY_ACCESS,

        /**
         * Used for instances of {@link UniArrayType}.
         */
        ARRAY_TYPE,

        /**
         * Used for instances of {@link UniAssert}.
         */
        ASSERT,

        /**
         * Used for instances of {@link UniAssignment}.
         */
        ASSIGNMENT,

        /**
         * Used for instances of {@link .UniBlock}.
         */
        BLOCK,

        /**
         * Used for instances of {@link UniBreak}.
         */
        BREAK,

        /**
         * Used for instances of {@link UniCase}.
         */
        CASE,

        /**
         * Used for instances of {@link UniCatch}.
         */
        CATCH,

        /**
         * Used for instances of {@link UniClass} representing classes.
         */
        CLASS,

        /**
         * Used for instances of {@link UniCompilationUnit}.
         */
        COMPILATION_UNIT,

        /**
         * Used for instances of {@link UniConditional}.
         */
        CONDITIONAL_EXPRESSION,

        /**
         * Used for instances of {@link UniContinue}.
         */
        CONTINUE,

        /**
         * Used for instances of {@link UniDoWhileLoop}.
         */
        DO_WHILE_LOOP,

        /**
         * Used for instances of {@link UniEnhancedForLoop}.
         */
        ENHANCED_FOR_LOOP,

        /**
         * Used for instances of {@link UniExpressionStatement}.
         */
        EXPRESSION_STATEMENT,

        MEMBER_SELECT,

        MEMBER_REFERENCE,

        /**
         * Used for instances of {@link UniForLoop}.
         */
        FOR_LOOP,

        /**
         * Used for instances of {@link UniIdentifier}.
         */
        IDENTIFIER,

        /**
         * Used for instances of {@link UniIf}.
         */
        IF,

        /**
         * Used for instances of {@link UniImport}.
         */
        IMPORT,

        /**
         * Used for instances of {@link UniInstanceOf}.
         */
        INSTANCE_OF,

        /**
         * Used for instances of {@link UniLabel}.
         */
        LABELED_STATEMENT,

        /**
         * Used for instances of {@link UniMethod}.
         */
        METHOD,

        /**
         * Used for instances of {@link UniMethodInvocation}.
         */
        METHOD_INVOCATION,

        /**
         * Used for instances of {@link UniModifiers}.
         */
        MODIFIERS,

        /**
         * Used for instances of {@link UniNewArray}.
         */
        NEW_ARRAY,

        /**
         * Used for instances of {@link UniNewClass}.
         */
        NEW_CLASS,

        /**
         * Used for instances of {@link UniLambda}.
         */
        LAMBDA_EXPRESSION,

        /**
         * Used for instances of {@link UniPackage}.
         *
         * @since 9
         */
        PACKAGE,

        /**
         * Used for instances of {@link UniParenthesized}.
         */
        PARENTHESIZED,

        /**
         * Used for instances of {@link UniBindingPattern}.
         *
         * @since 16
         */
        BINDING_PATTERN,

        /**
         * Used for instances of {@link UniGuardedPattern}.
         *
         * @since 17
         */
        GUARDED_PATTERN,

        /**
         * Used for instances of {@link UniParenthesizedPattern}.
         *
         * @since 17
         */
        PARENTHESIZED_PATTERN,

        /**
         * Used for instances of {@link UniDefaultCaseLabel}.
         *
         * @since 17
         */
        DEFAULT_CASE_LABEL,

        /**
         * Used for instances of {@link UniPrimitiveType}.
         */
        PRIMITIVE_TYPE,

        /**
         * Used for instances of {@link UniReturn}.
         */
        RETURN,

        /**
         * Used for instances of {@link UniEmpty}.
         */
        EMPTY_STATEMENT,

        /**
         * Used for instances of {@link UniSwitch}.
         */
        SWITCH,

        /**
         * Used for instances of {@link UniSwitchExpression}.
         *
         * @since 12
         */
        SWITCH_EXPRESSION,

        /**
         * Used for instances of {@link UniSynchronized}.
         */
        SYNCHRONIZED,

        /**
         * Used for instances of {@link UniThrow}.
         */
        THROW,

        /**
         * Used for instances of {@link UniTry}.
         */
        TRY,

        PARAMETERIZED_TYPE,

        /**
         * Used for instances of {@link UniUnionType}.
         */
        UNION_TYPE,

        /**
         * Used for instances of {@link UniIntersectionType}.
         */
        INTERSECTION_TYPE,

        /**
         * Used for instances of {@link UniTypeCast}.
         */
        TYPE_CAST,

        /**
         * Used for instances of {@link UniTypeParameter}.
         */
        TYPE_PARAMETER,

        /**
         * Used for instances of {@link UniVariable}.
         */
        VARIABLE,

        /**
         * Used for instances of {@link UniWhileLoop}.
         */
        WHILE_LOOP,

        /**
         * Used for instances of {@link UniUnary} representing postfix
         * increment operator {@code ++}.
         */
        POSTFIX_INCREMENT,

        /**
         * Used for instances of {@link UniUnary} representing postfix
         * decrement operator {@code --}.
         */
        POSTFIX_DECREMENT,

        /**
         * Used for instances of {@link UniUnary} representing prefix
         * increment operator {@code ++}.
         */
        PREFIX_INCREMENT,

        /**
         * Used for instances of {@link UniUnary} representing prefix
         * decrement operator {@code --}.
         */
        PREFIX_DECREMENT,

        /**
         * Used for instances of {@link UniUnary} representing unary plus
         * operator {@code +}.
         */
        UNARY_PLUS,

        /**
         * Used for instances of {@link UniUnary} representing unary minus
         * operator {@code -}.
         */
        UNARY_MINUS,

        /**
         * Used for instances of {@link UniUnary} representing bitwise
         * complement operator {@code ~}.
         */
        BITWISE_COMPLEMENT,

        /**
         * Used for instances of {@link UniUnary} representing logical
         * complement operator {@code !}.
         */
        LOGICAL_COMPLEMENT,

        /**
         * Used for instances of {@link UniBinary} representing
         * multiplication {@code *}.
         */
        MULTIPLY,

        /**
         * Used for instances of {@link UniBinary} representing
         * division {@code /}.
         */
        DIVIDE,

        /**
         * Used for instances of {@link UniBinary} representing
         * remainder {@code %}.
         */
        REMAINDER,

        /**
         * Used for instances of {@link UniBinary} representing
         * addition or string concatenation {@code +}.
         */
        PLUS,

        /**
         * Used for instances of {@link UniBinary} representing
         * subtraction {@code -}.
         */
        MINUS,

        /**
         * Used for instances of {@link UniBinary} representing
         * left shift {@code <<}.
         */
        LEFT_SHIFT,

        /**
         * Used for instances of {@link UniBinary} representing
         * right shift {@code >>}.
         */
        RIGHT_SHIFT,

        /**
         * Used for instances of {@link UniBinary} representing
         * unsigned right shift {@code >>>}.
         */
        UNSIGNED_RIGHT_SHIFT,

        /**
         * Used for instances of {@link UniBinary} representing
         * less-than {@code <}.
         */
        LESS_THAN,

        /**
         * Used for instances of {@link UniBinary} representing
         * greater-than {@code >}.
         */
        GREATER_THAN,

        /**
         * Used for instances of {@link UniBinary} representing
         * less-than-equal {@code <=}.
         */
        LESS_THAN_EQUAL,

        /**
         * Used for instances of {@link UniBinary} representing
         * greater-than-equal {@code >=}.
         */
        GREATER_THAN_EQUAL,

        /**
         * Used for instances of {@link UniBinary} representing
         * equal-to {@code ==}.
         */
        EQUAL_TO,

        /**
         * Used for instances of {@link UniBinary} representing
         * not-equal-to {@code !=}.
         */
        NOT_EQUAL_TO,

        /**
         * Used for instances of {@link UniBinary} representing
         * bitwise and logical "and" {@code &}.
         */
        AND,

        /**
         * Used for instances of {@link UniBinary} representing
         * bitwise and logical "xor" {@code ^}.
         */
        XOR,

        /**
         * Used for instances of {@link UniBinary} representing
         * bitwise and logical "or" {@code |}.
         */
        OR,

        /**
         * Used for instances of {@link UniBinary} representing
         * conditional-and {@code &&}.
         */
        CONDITIONAL_AND,

        /**
         * Used for instances of {@link UniBinary} representing
         * conditional-or {@code ||}.
         */
        CONDITIONAL_OR,

        /**
         * Used for instances of {@link UniCompoundAssignment} representing
         * multiplication assignment {@code *=}.
         */
        MULTIPLY_ASSIGNMENT,

        /**
         * Used for instances of {@link UniCompoundAssignment} representing
         * division assignment {@code /=}.
         */
        DIVIDE_ASSIGNMENT,

        /**
         * Used for instances of {@link UniCompoundAssignment} representing
         * remainder assignment {@code %=}.
         */
        REMAINDER_ASSIGNMENT,

        /**
         * Used for instances of {@link UniCompoundAssignment} representing
         * addition or string concatenation assignment {@code +=}.
         */
        PLUS_ASSIGNMENT,

        /**
         * Used for instances of {@link UniCompoundAssignment} representing
         * subtraction assignment {@code -=}.
         */
        MINUS_ASSIGNMENT,

        /**
         * Used for instances of {@link UniCompoundAssignment} representing
         * left shift assignment {@code <<=}.
         */
        LEFT_SHIFT_ASSIGNMENT,

        /**
         * Used for instances of {@link UniCompoundAssignment} representing
         * right shift assignment {@code >>=}.
         */
        RIGHT_SHIFT_ASSIGNMENT,

        /**
         * Used for instances of {@link UniCompoundAssignment} representing
         * unsigned right shift assignment {@code >>>=}.
         */
        UNSIGNED_RIGHT_SHIFT_ASSIGNMENT,

        /**
         * Used for instances of {@link UniCompoundAssignment} representing
         * bitwise and logical "and" assignment {@code &=}.
         */
        AND_ASSIGNMENT,

        /**
         * Used for instances of {@link UniCompoundAssignment} representing
         * bitwise and logical "xor" assignment {@code ^=}.
         */
        XOR_ASSIGNMENT,

        /**
         * Used for instances of {@link UniCompoundAssignment} representing
         * bitwise and logical "or" assignment {@code |=}.
         */
        OR_ASSIGNMENT,

        /**
         * Used for instances of {@link UniLiteral} representing
         * an integral literal expression of type {@code int}.
         */
        INT_LITERAL,

        /**
         * Used for instances of {@link UniLiteral} representing
         * an integral literal expression of type {@code long}.
         */
        LONG_LITERAL,

        /**
         * Used for instances of {@link UniLiteral} representing
         * a floating-point literal expression of type {@code float}.
         */
        FLOAT_LITERAL,

        /**
         * Used for instances of {@link UniLiteral} representing
         * a floating-point literal expression of type {@code double}.
         */
        DOUBLE_LITERAL,

        /**
         * Used for instances of {@link UniLiteral} representing
         * a boolean literal expression of type {@code boolean}.
         */
        BOOLEAN_LITERAL,

        /**
         * Used for instances of {@link UniLiteral} representing
         * a character literal expression of type {@code char}.
         */
        CHAR_LITERAL,

        /**
         * Used for instances of {@link UniLiteral} representing
         * a string literal expression of type {@link String}.
         */
        STRING_LITERAL,

        /**
         * Used for instances of {@link UniLiteral} representing
         * the use of {@code null}.
         */
        NULL_LITERAL,

        /**
         * Used for instances of {@link UniWildcardType} representing
         * an unbounded wildcard type argument.
         */
        UNBOUNDED_WILDCARD,

        /**
         * Used for instances of {@link UniWildcardType} representing
         * an extends bounded wildcard type argument.
         */
        EXTENDS_WILDCARD,

        /**
         * Used for instances of {@link UniWildcardType} representing
         * a super bounded wildcard type argument.
         */
        SUPER_WILDCARD,

        /**
         * Used for instances of {@link UniErroneous}.
         */
        ERRONEOUS,

        /**
         * Used for instances of {@link UniClass} representing interfaces.
         */
        INTERFACE,

        /**
         * Used for instances of {@link UniClass} representing enums.
         */
        ENUM,

        /**
         * Used for instances of {@link UniClass} representing annotation types.
         */
        ANNOTATION_TYPE,

        /**
         * Used for instances of {@link UniModule} representing module declarations.
         */
        MODULE,

        /**
         * Used for instances of {@link UniExports} representing
         * exports directives in a module declaration.
         */
        EXPORTS,

        /**
         * Used for instances of {@link UniExports} representing
         * opens directives in a module declaration.
         */
        OPENS,

        /**
         * Used for instances of {@link UniProvides} representing
         * provides directives in a module declaration.
         */
        PROVIDES,

        /**
         * Used for instances of {@link UniClass} representing records.
         *
         * @since 16
         */
        RECORD,

        /**
         * Used for instances of {@link UniRequires} representing
         * requires directives in a module declaration.
         */
        REQUIRES,

        /**
         * Used for instances of {@link UniUses} representing
         * uses directives in a module declaration.
         */
        USES,

        /**
         * An implementation-reserved node. This is the not the node
         * you are looking for.
         */
        OTHER,

        /**
         * Used for instances of {@link UniYield}.
         *
         * @since 13
         */
        YIELD
    }
}
