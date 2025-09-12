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
import org.galaxy.uniflow.api.modules.UniModule;
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

    @NotNull UniCompilationUnit createTopLevel(@NotNull UniPackage packageDecl,
                                               @NotNull List<@NotNull UniImport> imports,
                                               @NotNull List<@NotNull UniClass> classes);

    @NotNull UniCompilationUnit createTopLevel(@NotNull UniPackage packageDecl,
                                               @NotNull List<@NotNull UniModule> modules);

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

    @NotNull UniClass createClass(@NotNull UniModifiers modifiers,
                                  @NotNull String name,
                                  @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                  @Nullable UniType extending,
                                  @NotNull List<@NotNull UniType> implementing,
                                  @NotNull List<@NotNull UniExpression> permitting,
                                  @NotNull List<@NotNull UniVariable> fields,
                                  @NotNull List<@NotNull UniMethod> methods);

    @NotNull UniClass createClass(@NotNull UniModifiers modifiers,
                                  @NotNull String name,
                                  @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                  @Nullable UniType extending,
                                  @NotNull List<@NotNull UniType> implementing,
                                  @NotNull List<@NotNull UniExpression> permitting,
                                  @NotNull List<@NotNull UniVariable> fields,
                                  @NotNull List<@NotNull UniMethod> methods,
                                  @NotNull List<@NotNull UniClassInitializer> initializers);

    @NotNull UniMethod createMethod(@NotNull UniModifiers modifiers,
                                    @NotNull String name,
                                    @NotNull UniType returnType,
                                    @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                    @NotNull UniVariable receiveParam,
                                    @NotNull List<@NotNull UniVariable> parameters,
                                    @NotNull List<@NotNull UniExpression> thrown,
                                    @NotNull UniBlock body,
                                    @NotNull UniExpression defaultValue);

    @NotNull UniMethod createMethod(@NotNull UniModifiers modifiers,
                                    @NotNull String name,
                                    @NotNull Class<?> returnType,
                                    @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                    @NotNull UniVariable receiveParam,
                                    @NotNull List<@NotNull UniVariable> parameters,
                                    @NotNull List<@NotNull UniExpression> thrown,
                                    @NotNull UniBlock body,
                                    @NotNull UniExpression defaultValue);

    @NotNull UniVariable createField(@NotNull UniModifiers modifiers,
                                     @NotNull String name,
                                     @NotNull Class<?> type,
                                     @Nullable UniExpression init);

    @NotNull UniVariable createField(@NotNull UniModifiers modifiers,
                                     @NotNull String name,
                                     @NotNull UniType type,
                                     @Nullable UniExpression init);

    @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                        @NotNull String name,
                                        @NotNull Class<?> type,
                                        @Nullable UniExpression init,
                                        boolean useVar);

    @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                        @NotNull String name,
                                        @NotNull UniType type,
                                        @Nullable UniExpression init,
                                        boolean useVar);

    default @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                                @NotNull String name,
                                                @NotNull Class<?> type,
                                                @NotNull UniExpression init) {
        return createVariable(annotations, name, type, init, false);
    }

    default @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                                @NotNull String name,
                                                @NotNull UniType type,
                                                @NotNull UniExpression init) {
        return createVariable(annotations, name, type, init, false);
    }

    default @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                                @NotNull String name,
                                                @NotNull Class<?> type) {
        return createVariable(annotations, name, type, null, false);
    }

    default @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                                @NotNull String name,
                                                @NotNull UniType type) {
        return createVariable(annotations, name, type, null, false);
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

    @NotNull UniSwitchExpression createSwitchExpression(@NotNull UniExpression selector,
                                                        @NotNull List<@NotNull UniCase> cases);

    @NotNull UniCase createCase(@NotNull UniCase.CaseKind kind,
                                @NotNull List<@NotNull UniCaseLabel> labels,
                                @NotNull List<@NotNull UniStatement> statements,
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

    @NotNull UniIdentifier createIdentifier(@NotNull String name);

    @NotNull UniLiteral createNull();

    @NotNull UniLiteral createLiteral(@NotNull TypeTag tag, @NotNull Object value);

    @NotNull UniLiteral createStringLiteral(@NotNull String value);

    @NotNull UniAnnotation createAnnotation(@NotNull UniType annotationType,
                                            @NotNull List<@NotNull UniAnnotationAttribute> attributes);

    @NotNull UniAnnotationAttribute createAnnotationAttribute(@NotNull String name, @NotNull UniAnnotationValue value);

    @NotNull UniErroneous createErroneous(@NotNull List<? extends @NotNull UniElement> errors);

    @NotNull UniLet createLet(@NotNull List<@NotNull UniStatement> definitions,
                              @NotNull UniExpression expression);

    @NotNull UniFieldAccess createFieldAccess(@NotNull UniType selected, @NotNull String name);

    @NotNull UniFieldAccess createClassLiteral(@NotNull UniClassType type);

    @NotNull UniFieldAccess createClassLiteral(@NotNull Class<?> type);

}
