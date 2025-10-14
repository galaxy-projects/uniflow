package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.expressions.*;
import org.galaxy.uniflow.api.pattern.UniBindingPattern;
import org.galaxy.uniflow.api.pattern.UniGuardedPattern;
import org.galaxy.uniflow.api.pattern.UniParenthesizedPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.statements.*;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UniElementFactory {

    default boolean supportsJigsaw() {
        return this instanceof UniJigsawElementFactory;
    }

    default @NotNull UniJigsawElementFactory asJigsaw() {
        if (supportsJigsaw())
            return (UniJigsawElementFactory) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    default boolean supportsJdk10() {
        return this instanceof UniJdk10ElementFactory;
    }

    default @NotNull UniJdk10ElementFactory asJdk10() {
        if (supportsJdk10())
            return (UniJdk10ElementFactory) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    default boolean supportsJdk12() {
        return this instanceof UniJdk12ElementFactory;
    }

    default @NotNull UniJdk12ElementFactory asJdk12() {
        if (supportsJdk12())
            return (UniJdk12ElementFactory) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    default boolean supportsJdk15() {
        return this instanceof UniJdk15ElementFactory;
    }

    default @NotNull UniJdk15ElementFactory asJdk15() {
        if (supportsJdk15())
            return (UniJdk15ElementFactory) this;
        throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE);
    }

    @NotNull UniCompilationUnit createTopLevel(@NotNull UniPackage packageDecl,
                                               @NotNull List<@NotNull UniImport> imports,
                                               @NotNull List<@NotNull UniClass> classes);

    @NotNull UniCompilationUnit createTopLevel(@NotNull List<@NotNull UniElement> elements);

    @NotNull UniPackage createPackage(@NotNull List<@NotNull UniAnnotation> annotations,
                                      @NotNull String name);

    @NotNull UniModifiers createModifiers(@NotNull List<@NotNull UniModifier> modifiers,
                                          @NotNull List<@NotNull UniAnnotation> annotations);

    @NotNull UniClass createClass(@NotNull UniModifiers modifiers,
                                  @NotNull String name,
                                  @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                  @Nullable UniType extending,
                                  @NotNull List<@NotNull UniType> implementing,
                                  @NotNull List<@NotNull UniVariable> fields,
                                  @NotNull List<@NotNull UniMethod> methods);

    UniClass createClass(@NotNull UniModifiers modifiers,
                         @NotNull String name,
                         @NotNull List<@NotNull UniTypeParameter> typeParameters,
                         @Nullable UniType extending,
                         @NotNull List<@NotNull UniType> implementing,
                         @NotNull List<@NotNull UniVariable> fields,
                         @NotNull List<@NotNull UniMethod> methods,
                         @NotNull List<@NotNull UniClassInitializer> initializers);

    @NotNull UniMethod createMethod(@NotNull UniModifiers modifiers,
                                    @NotNull String name,
                                    @NotNull Class<?> returnType,
                                    @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                    @Nullable UniVariable receiveParam,
                                    @NotNull List<@NotNull UniVariable> parameters,
                                    @NotNull List<@NotNull UniExpression> thrown,
                                    @NotNull UniBlock body);

    @NotNull UniMethod createMethod(@NotNull UniModifiers modifiers,
                                    @NotNull String name,
                                    @NotNull UniType returnType,
                                    @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                    @Nullable UniVariable receiveParam,
                                    @NotNull List<@NotNull UniVariable> parameters,
                                    @NotNull List<@NotNull UniExpression> thrown,
                                    @NotNull UniBlock body);

    @NotNull UniMethod createAnnotationAttribute(@NotNull UniModifiers modifiers,
                                                 @NotNull String name,
                                                 @NotNull Class<?> returnType,
                                                 @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                                 @NotNull UniVariable receiveParam,
                                                 @NotNull List<@NotNull UniVariable> parameters,
                                                 @NotNull List<@NotNull UniExpression> thrown,
                                                 @Nullable UniExpression defaultValue);

    @NotNull UniMethod createAnnotationAttribute(@NotNull UniModifiers modifiers,
                                                 @NotNull String name,
                                                 @NotNull UniType returnType,
                                                 @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                                 @NotNull UniVariable receiveParam,
                                                 @NotNull List<@NotNull UniVariable> parameters,
                                                 @NotNull List<@NotNull UniExpression> thrown,
                                                 @Nullable UniExpression defaultValue);

    @NotNull UniField createField(@NotNull UniModifiers modifiers,
                                  @NotNull String name,
                                  @NotNull Class<?> type,
                                  @Nullable UniExpression init);

    @NotNull UniField createField(@NotNull UniModifiers modifiers,
                                  @NotNull String name,
                                  @NotNull UniType type,
                                  @Nullable UniExpression init);

    @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                        @NotNull String name,
                                        @NotNull Class<?> type,
                                        @Nullable UniExpression init);

    @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                        @NotNull String name,
                                        @NotNull UniType type,
                                        @Nullable UniExpression init);

    default @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                                @NotNull String name,
                                                @NotNull Class<?> type) {
        return createVariable(annotations, name, type, null);
    }

    default @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                                @NotNull String name,
                                                @NotNull UniType type) {
        return createVariable(annotations, name, type, null);
    }

    @NotNull UniEmpty createSkip();

    @NotNull UniBlock createBlock(boolean isStatic, @NotNull List<@NotNull UniStatement> statements);

    @NotNull UniDoWhileLoop createDoWhileLoop(@NotNull UniStatement body, @NotNull UniExpression condition);

    @NotNull UniWhileLoop createWhileLoop(@NotNull UniExpression condition, @NotNull UniStatement body);

    @NotNull UniForLoop createForLoop(@NotNull List<@NotNull UniStatement> init,
                                      @NotNull UniExpression condition,
                                      @NotNull List<@NotNull UniExpressionStatement> step,
                                      @NotNull UniStatement body);

    @NotNull UniEnhancedForLoop createForEachLoop(@NotNull UniVariable variable,
                                                  @NotNull UniExpression iterable,
                                                  @NotNull UniStatement body);

    @NotNull UniLabel createLabel(@NotNull String name, @NotNull UniStatement body);

    @NotNull UniSwitch createSwitch(@NotNull UniExpression selector,
                                    @NotNull List<@NotNull UniCase> cases);

    @NotNull UniCase createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                @NotNull List<@NotNull UniStatement> statements);

    @NotNull UniCase createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                @NotNull UniElement body);

    @NotNull UniSynchronized createSynchronized(@NotNull UniExpression lock, @NotNull UniBlock body);

    @NotNull UniTry createTry(@NotNull UniBlock body,
                              @NotNull List<@NotNull UniCatch> catches,
                              @Nullable UniBlock finallyBlock);

    @NotNull UniTry createTry(@NotNull List<@NotNull UniElement> resources,
                              @NotNull UniBlock body,
                              @NotNull List<@NotNull UniCatch> catches,
                              @Nullable UniBlock finallyBlock);

    @NotNull UniCatch createCatch(@NotNull UniVariable variable, @NotNull UniBlock body);

    @NotNull UniConditional createTernary(@NotNull UniExpression condition,
                                          @NotNull UniExpression thenBlock,
                                          @NotNull UniExpression elseBlock);

    @NotNull UniIf createIf(@NotNull UniExpression condition,
                            @NotNull UniStatement thenBlock,
                            @Nullable UniStatement elseBlock);

    @NotNull UniExpressionStatement createExecution(@NotNull UniExpression expression);

    @NotNull UniBreak createBreak(@Nullable String label);

    @NotNull UniYield createYield(@NotNull UniExpression value);

    @NotNull UniContinue createContinue(@Nullable String label);

    @NotNull UniReturn createReturn(@NotNull UniExpression value);

    @NotNull UniThrow createThrow(@NotNull UniExpression value);

    @NotNull UniAssert createAssert(@NotNull UniExpression condition, @Nullable UniExpression details);

    @NotNull UniMethodInvocation createMethodInvocation(@NotNull UniExpression method,
                                                        @NotNull List<@NotNull UniType> argumentTypes,
                                                        @NotNull List<@NotNull UniExpression> args);

    @NotNull UniNewClass createNewClass(@NotNull UniExpression enclosing,
                                        @NotNull List<@NotNull UniType> argumentTypes,
                                        @NotNull List<@NotNull UniExpression> args,
                                        @NotNull UniType classType);

    @NotNull UniNewArray createNewArrayWithDimension(@NotNull UniType elementType,
                                                     @NotNull List<@NotNull UniExpression> dimensions);

    @NotNull UniNewArray createNewArrayWithElements(@NotNull UniType elementType,
                                                    @NotNull List<@NotNull UniExpression> elements);

    @NotNull UniParenthesized createParenthesized(@NotNull UniExpression expression);

    @NotNull UniAssignment createAssignment(@NotNull UniExpression lhs, @NotNull UniExpression rhs);

    @NotNull UniCompoundAssignment createCompoundAssignment(@NotNull UniElement.Tag opcode,
                                                            @NotNull UniExpression lhs,
                                                            @NotNull UniExpression rhs);

    @NotNull UniUnary createUnary(@NotNull UniElement.Tag opcode,
                                  @NotNull UniExpression argument);

    @NotNull UniBinary createBinary(@NotNull UniElement.Tag opcode,
                                    @NotNull UniExpression lhs,
                                    @NotNull UniExpression rhs);

    @NotNull UniTypeCast createTypeCast(@NotNull UniType type, @NotNull UniExpression expression);

    @NotNull UniInstanceOf createInstanceOf(@NotNull UniExpression expression, @NotNull UniType type);

    @NotNull UniInstanceOf createInstanceOf(@NotNull UniExpression expression, @NotNull UniPattern pattern);

    @NotNull UniBindingPattern createBindingPattern(@NotNull UniVariable variable);

    @NotNull UniGuardedPattern createGuardedPattern(@NotNull UniPattern pattern, @NotNull UniExpression expression);

    @NotNull UniParenthesizedPattern createParenthesizedPattern(@NotNull UniPattern pattern);

    @NotNull UniArrayAccess createArrayAccess(@NotNull UniExpression array, @NotNull UniExpression index);

    @NotNull UniIdentifier createThis();

    @NotNull UniIdentifier createIdentifier(@NotNull String name);

    @NotNull UniLiteral createNull();

    @NotNull UniLiteral createLiteral(@NotNull TypeTag tag, @NotNull Object value);

    @NotNull UniLiteral createStringLiteral(@NotNull String value);

    @NotNull UniAnnotation createAnnotation(@NotNull Class<?> annotationType,
                                            @NotNull List<@NotNull UniAnnotationAttribute> attributes);

    @NotNull UniAnnotation createAnnotation(@NotNull UniType annotationType,
                                            @NotNull List<@NotNull UniAnnotationAttribute> attributes);

    @NotNull UniAnnotationAttribute createAnnotationAttribute(@NotNull String name, @NotNull UniAnnotationValue value);

    @NotNull UniErroneous createErroneous(@NotNull List<? extends @NotNull UniElement> errors);

    @NotNull UniLet createLet(@NotNull List<@NotNull UniStatement> definitions,
                              @NotNull UniExpression expression);

    @NotNull UniFieldAccess createFieldAccess(@NotNull Class<?> selected, @NotNull String name);

    @NotNull UniFieldAccess createFieldAccess(@NotNull UniType selected, @NotNull String name);

    @NotNull UniFieldAccess createFieldAccess(@NotNull UniExpression expression, @NotNull String name);

    @NotNull UniFieldAccess createClassLiteral(@NotNull UniClassType type);

    @NotNull UniFieldAccess createClassLiteral(@NotNull Class<?> type);

}
