package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.java.PsiEmptyStatementImpl;
import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.elements.labels.UniDefaultCaseLabel;
import org.galaxy.uniflow.api.elements.resources.UniExpressionResource;
import org.galaxy.uniflow.api.elements.resources.UniResource;
import org.galaxy.uniflow.api.elements.resources.UniVariableResource;
import org.galaxy.uniflow.api.expressions.*;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.api.factories.UniTypeFactory;
import org.galaxy.uniflow.api.statements.*;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.intellij.psi.*;
import org.galaxy.uniflow.intellij.psi.elements.IJAnnotation;
import org.galaxy.uniflow.intellij.psi.elements.IJCatch;
import org.galaxy.uniflow.intellij.psi.elements.IJDefaultCaseLabel;
import org.galaxy.uniflow.intellij.psi.elements.resources.IJExpressionResource;
import org.galaxy.uniflow.intellij.psi.elements.resources.IJResource;
import org.galaxy.uniflow.intellij.psi.elements.resources.IJVariableResource;
import org.galaxy.uniflow.intellij.psi.expression.IJExpression;
import org.galaxy.uniflow.intellij.psi.statements.*;
import org.galaxy.uniflow.intellij.psi.types.IJType;
import org.galaxy.uniflow.intellij.psi.types.elements.IJTypeParameter;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public record IntellijElementFactory(PsiElementFactory factory, PsiParserFacade parser, PsiFileFactory files)
        implements UniElementFactory {

    // Jdk 8


    @Override
    public @NotNull UniPackage createPackage(@NotNull String name) {
        return new IJPackage(factory.createPackageStatement(name));
    }

    @Override
    public @NotNull UniModifiers createModifiers(@NotNull List<@NotNull UniModifier> modifiers,
                                                 @NotNull List<@NotNull UniAnnotation> annotations) {
        Stream<IJAnnotation> ijAnnotations = checkList(annotations, IJAnnotation.class);
        PsiModifierList modifierList = factory.createClassInitializer().getModifierList();
        @PsiModifier.ModifierConstant String name;

        assert modifierList != null;
        for (UniModifier modifier : modifiers) {
            name = Modifiers.getPsiModifier(modifier);

            if (name != null)
                modifierList.setModifierProperty(name, true);
        }
        ijAnnotations.map(IJAnnotation::getElement).forEach(modifierList::add);
        return new IJModifiers(modifierList);
    }

    @Override
    public @NotNull UniClass createClass(@NotNull UniModifiers modifiers,
                                         @NotNull String name,
                                         @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                         @Nullable UniType extending,
                                         @NotNull List<@NotNull UniType> implementing,
                                         @NotNull List<@NotNull UniField> fields,
                                         @NotNull List<@NotNull UniMethod> methods) {
        return createClass(modifiers, name, typeParameters, extending, implementing, fields, methods,
                Collections.emptyList());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniClass createClass(@NotNull UniModifiers modifiers,
                                         @NotNull String name,
                                         @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                         @Nullable UniType extending,
                                         @NotNull List<@NotNull UniType> implementing,
                                         @NotNull List<@NotNull UniField> fields,
                                         @NotNull List<@NotNull UniMethod> methods,
                                         @NotNull List<@NotNull UniClassInitializer> initializers) {
        IJModifiers ijModifiers = check(modifiers, IJModifiers.class);
        Stream<IJTypeParameter> ijTypeParameters = checkList(typeParameters, IJTypeParameter.class);
        IJType<?> ijExtending = check(extending, IJType.class);
        Stream<IJType> ijImplementing = checkList(implementing, IJType.class);
        Stream<IJField> ijFields = checkList(fields, IJField.class);
        Stream<IJMethod> ijMethods = checkList(methods, IJMethod.class);
        Stream<IJClassInitializer> ijInitializers = checkList(initializers, IJClassInitializer.class);

        PsiTypeParameterList typeParameterList = factory.createTypeParameterList();
        PsiClass result = factory.createClass(name);

        ijTypeParameters.map(IJTypeParameter::getElement).forEach(typeParameterList::add);

        if (result.getModifierList() != null)
            result.getModifierList().replace(ijModifiers.getElement());
        else result.add(ijModifiers.getElement());

        if (result.getTypeParameterList() != null)
            result.getTypeParameterList().replace(typeParameterList);
        else result.add(typeParameterList);

        if (ijExtending != null && ijExtending.getRawType() instanceof PsiClassType type) {
            PsiReferenceList extendsList = factory.createReferenceList(
                    new PsiJavaCodeReferenceElement[] { IntellijUnwrapper.unwrapReferenceFromType(type) });

            if (result.getExtendsList() != null)
                result.getExtendsList().replace(extendsList);
            else result.add(extendsList);
        }

        PsiReferenceList implementationList = factory.createReferenceList(
                ijImplementing.filter(type -> type.getRawType() instanceof PsiClassType)
                        .map(IJType::getRawType)
                        .map(PsiClassType.class::cast)
                        .map(IntellijUnwrapper::unwrapReferenceFromType)
                        .toArray(PsiJavaCodeReferenceElement[]::new)
        );

        if (result.getImplementsList() != null)
            result.getImplementsList().replace(implementationList);
        else result.add(implementationList);

        ijFields.map(IJField::getElement).forEach(result::add);
        ijMethods.map(IJMethod::getElement).forEach(result::add);
        ijInitializers.map(IJClassInitializer::getElement).forEach(result::add);

        return new IJClass(result);
    }

    @Override
    public @NotNull UniMethod createMethod(@NotNull UniModifiers modifiers,
                                           @NotNull String name,
                                           @NotNull Class<?> returnType,
                                           @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                           @Nullable UniVariable receiveParam,
                                           @NotNull List<@NotNull UniParameter> parameters,
                                           @NotNull List<@NotNull UniExpression> thrown,
                                           @NotNull UniBlock body) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createMethod(modifiers, name, typeFactory.createClassType(returnType), typeParameters, receiveParam,
                parameters, thrown, body);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniMethod createMethod(@NotNull UniModifiers modifiers,
                                           @NotNull String name,
                                           @NotNull UniType returnType,
                                           @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                           @Nullable UniVariable receiveParam,
                                           @NotNull List<@NotNull UniParameter> parameters,
                                           @NotNull List<@NotNull UniExpression> thrown,
                                           @NotNull UniBlock body) {
        IJModifiers ijModifiers = check(modifiers, IJModifiers.class);
        IJType<?> ijReturnType = check(returnType, IJType.class);
        Stream<IJTypeParameter> ijTypeParameters = checkList(typeParameters, IJTypeParameter.class);
        Stream<IJParameter> ijParameters = checkList(parameters, IJParameter.class);
        Stream<IJExpression> ijThrown = checkList(thrown, IJExpression.class);
        IJBlock ijBody = check(body, IJBlock.class);

        PsiMethod method = factory.createMethod(name, ijReturnType.getRawType());
        PsiTypeParameterList typeParameterList = factory.createTypeParameterList();
        PsiParameterList parameterList = method.getParameterList();
        PsiReferenceList throwsList = method.getThrowsList();

        ijTypeParameters.map(IJTypeParameter::getElement).forEach(typeParameterList::add);

        method.getModifierList().replace(ijModifiers.getElement());

        if (method.getTypeParameterList() != null)
            method.getTypeParameterList().replace(typeParameterList);
        else method.add(typeParameterList);

        ijParameters.map(IJParameter::getElement).forEach(parameterList::add);
        ijThrown.map(IntellijUnwrapper::unwrapReference).forEach(throwsList::add);

        if (method.getBody() != null)
            method.getBody().replace(ijBody.getElement());
        else method.add(ijBody.getElement());

        return new IJMethod(method);
    }

    @Override
    public @NotNull UniMethod createMethod(@NotNull UniModifiers modifiers,
                                           @NotNull String name,
                                           @NotNull Class<?> returnType,
                                           @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                           @NotNull UniVariable receiveParam,
                                           @NotNull List<@NotNull UniParameter> parameters,
                                           @NotNull List<@NotNull UniExpression> thrown,
                                           @Nullable UniExpression defaultValue) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createMethod(modifiers, name, typeFactory.createClassType(returnType), typeParameters,
                receiveParam, parameters, thrown, defaultValue);
    }

    @Override
    public @NotNull UniMethod createMethod(@NotNull UniModifiers modifiers,
                                           @NotNull String name,
                                           @NotNull UniType returnType,
                                           @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                           @NotNull UniVariable receiveParam,
                                           @NotNull List<@NotNull UniParameter> parameters,
                                           @NotNull List<@NotNull UniExpression> thrown,
                                           @Nullable UniExpression defaultValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public @NotNull UniField createField(@NotNull UniModifiers modifiers,
                                         @NotNull String name,
                                         @NotNull Class<?> type,
                                         @Nullable UniExpression init) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createField(modifiers, name, typeFactory.createClassType(type), init);
    }

    @Override
    public @NotNull UniField createField(@NotNull UniModifiers modifiers,
                                         @NotNull String name,
                                         @NotNull UniType type,
                                         @Nullable UniExpression init) {
        IJModifiers ijModifiers = check(modifiers, IJModifiers.class);
        IJType<?> ijType = check(type, IJType.class);
        IJExpression<?> ijInit = check(init, IJExpression.class);

        PsiField field = factory.createField(name, ijType.getRawType());

        if (field.getModifierList() != null)
            field.getModifierList().replace(ijModifiers.getElement());
        else field.add(ijModifiers.getElement());

        if (ijInit != null)
            field.setInitializer(ijInit.getElement());

        return new IJField(field);
    }

    @Override
    public @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                               @NotNull String name,
                                               @NotNull Class<?> type,
                                               @Nullable UniExpression init) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createVariable(annotations, name, typeFactory.createClassType(type), init);
    }

    @Override
    public @NotNull UniVariable createVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                               @NotNull String name,
                                               @NotNull UniType type,
                                               @Nullable UniExpression init) {
        Stream<IJAnnotation> ijAnnotations = checkList(annotations, IJAnnotation.class);
        IJType<?> ijType = check(type, IJType.class);
        IJExpression<?> ijInit = check(init, IJExpression.class);

        PsiDeclarationStatement declaration = factory.createVariableDeclarationStatement(name,
                ijType.getRawType(), ijInit != null ? ijInit.getElement() : null);
        PsiVariable variable = (PsiVariable) declaration.getDeclaredElements()[0];
        PsiModifierList modifierList = factory.createClassInitializer().getModifierList();

        assert modifierList != null;
        ijAnnotations.map(IJAnnotation::getElement).forEach(modifierList::add);

        if (variable.getModifierList() != null)
            variable.getModifierList().replace(modifierList);
        else variable.add(modifierList);

        return new IJVariable(variable);
    }

    @Override
    public @NotNull UniParameter createParameter(@NotNull List<@NotNull UniAnnotation> annotations,
                                                 @NotNull String name, @NotNull Class<?> type) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createParameter(annotations, name, typeFactory.createClassType(type));
    }

    @Override
    public @NotNull UniParameter createParameter(@NotNull List<@NotNull UniAnnotation> annotations,
                                                 @NotNull String name,
                                                 @NotNull UniType type) {
        Stream<IJAnnotation> ijAnnotations = checkList(annotations, IJAnnotation.class);
        IJType<?> ijType = check(type, IJType.class);

        PsiParameter parameter = factory.createParameter(name, ijType.getRawType());
        PsiModifierList modifierList = factory.createClassInitializer().getModifierList();

        assert modifierList != null;
        ijAnnotations.map(IJAnnotation::getElement).forEach(modifierList::add);

        if (parameter.getModifierList() != null)
            parameter.getModifierList().replace(modifierList);
        else parameter.add(modifierList);

        return new IJParameter(parameter);
    }

    @Override
    public @NotNull UniEmpty createSkip() {
        return new IJEmpty(new PsiEmptyStatementImpl());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniBlock createBlock(boolean isStatic, @NotNull List<@NotNull UniStatement> statements) {
        Stream<IJStatement> ijStatements = checkList(statements, IJStatement.class);

        PsiCodeBlock block = isStatic ? factory.createCodeBlockFromText("static {}", null) : factory.createCodeBlock();

        ijStatements.map(IJStatement::getElement).forEach(block::add);

        return new IJBlock(block);
    }

    @Override
    public @NotNull UniDoWhileLoop createDoWhileLoop(@NotNull UniStatement body, @NotNull UniExpression condition) {
        IJStatement<?> ijBody = check(body, IJStatement.class);
        IJExpression<?> ijCondition = check(condition, IJExpression.class);

        PsiDoWhileStatement doWhile = (PsiDoWhileStatement) factory.createStatementFromText(
                "do {} while (cond);", null);

        assert doWhile.getCondition() != null;
        assert doWhile.getBody() != null;

        doWhile.getCondition().replace(ijCondition.getElement());
        doWhile.getBody().replace(ijBody.getElement());

        return new IJDoWhileLoop(doWhile);
    }

    @Override
    public @NotNull UniWhileLoop createWhileLoop(@NotNull UniExpression condition, @NotNull UniStatement body) {
        IJExpression<?> ijCondition = check(condition, IJExpression.class);
        IJStatement<?> ijBody = check(body, IJStatement.class);

        PsiWhileStatement whileLoop = (PsiWhileStatement) factory.createStatementFromText("while (cond) {}", null);

        assert whileLoop.getCondition() != null;
        assert whileLoop.getBody() != null;

        whileLoop.getCondition().replace(ijCondition.getElement());
        whileLoop.getBody().replace(ijBody.getElement());

        return new IJWhileLoop(whileLoop);
    }

    @Override
    public @NotNull UniForLoop createForLoop(@NotNull List<@NotNull UniStatement> init,
                                             @NotNull UniExpression condition,
                                             @NotNull List<@NotNull UniExpressionStatement> step,
                                             @NotNull UniStatement body) {
        IJExpression<?> ijCondition = check(condition, IJExpression.class);
        IJStatement<?> ijBody = check(body, IJStatement.class);
        List<UniStatement> stepStatements = step.stream().map(UniStatement.class::cast).toList();

        PsiForStatement forLoop = (PsiForStatement) factory.createStatementFromText("for (;;;) {}", null);

        assert forLoop.getInitialization() != null;
        assert forLoop.getCondition() != null;
        assert forLoop.getUpdate() != null;
        assert forLoop.getBody() != null;

        forLoop.getCondition().replace(ijCondition.getElement());
        IJForLoop.createConsumer(forLoop.getInitialization()::replace).accept(init);
        IJForLoop.createConsumer(forLoop.getUpdate()::replace).accept(stepStatements);
        forLoop.getBody().replace(ijBody.getElement());

        return new IJForLoop(forLoop);
    }

    @Override
    public @NotNull UniEnhancedForLoop createForEachLoop(@NotNull UniParameter parameter,
                                                         @NotNull UniExpression iterable,
                                                         @NotNull UniStatement body) {
        IJParameter ijParameter = check(parameter, IJParameter.class);
        IJExpression<?> ijIterable = check(iterable, IJExpression.class);
        IJStatement<?> ijBody = check(body, IJStatement.class);

        PsiForeachStatement forEach = (PsiForeachStatement) factory.createStatementFromText("for (a : b) {}", null);

        assert forEach.getIteratedValue() != null;
        assert forEach.getBody() != null;

        forEach.getIterationParameter().replace(ijParameter.getElement());
        forEach.getIteratedValue().replace(ijIterable.getElement());
        forEach.getBody().replace(ijBody.getElement());

        return new IJEnhancedForLoop(forEach);
    }

    @Override
    public @NotNull UniLabel createLabel(@NotNull String name, @NotNull UniStatement body) {
        IJStatement<?> ijBody = check(body, IJStatement.class);

        PsiLabeledStatement label = (PsiLabeledStatement) factory.createStatementFromText(name + ": {}", null);

        if (label.getStatement() != null)
            label.getStatement().replace(ijBody.getElement());
        else label.add(ijBody.getElement());

        return new IJLabel(label);
    }

    @Override
    public @NotNull UniSwitch createSwitch(@NotNull UniExpression selector,
                                           @NotNull List<@NotNull UniJdk8Case> cases) {
        return null;
    }

    @Override
    public @NotNull UniJdk8Case createCase(@NotNull UniCaseLabel label,
                                           @NotNull List<@NotNull UniStatement> statements) {
        return null;
    }

    @Override
    public @NotNull UniDefaultCaseLabel createDefaultCase() {
        PsiSwitchLabelStatement caseLabel = (PsiSwitchLabelStatement) factory.createStatementFromText(
                "default: return null;", null);
        PsiCaseLabelElementList list = caseLabel.getCaseLabelElementList();

        if (list == null) throw new RuntimeException("caseLabel element list is null");
        PsiCaseLabelElement element = list.getElements()[0];

        if (!(element instanceof PsiDefaultCaseLabelElement defaultCase))
            throw new RuntimeException("Case label must be default");
        return new IJDefaultCaseLabel((PsiDefaultCaseLabelElement) defaultCase.copy());
    }

    @Override
    public @NotNull UniSynchronized createSynchronized(@NotNull UniExpression lock, @NotNull UniBlock body) {
        IJExpression<?> ijLock = check(lock, IJExpression.class);
        IJStatement<?> ijBody = check(body, IJStatement.class);

        PsiSynchronizedStatement sync = (PsiSynchronizedStatement) factory.createStatementFromText(
                "synchronized (lock) {}", null);

        assert sync.getLockExpression() != null;
        assert sync.getBody() != null;

        sync.getLockExpression().replace(ijLock.getElement());
        sync.getBody().replace(ijBody.getElement());

        return new IJSynchronized(sync);
    }

    @Override
    public @NotNull UniTry createTry(@NotNull UniBlock body,
                                     @NotNull List<@NotNull UniCatch> catches,
                                     @Nullable UniBlock finallyBlock) {
        return createTry(List.of(), body, catches, finallyBlock);
    }

    @Override
    public @NotNull UniExpressionResource createResource(@NotNull UniExpression expression) {
        return new IJExpressionResource(expression);
    }

    @Override
    public @NotNull UniVariableResource createResource(@NotNull UniVariable variable) {
        return new IJVariableResource(variable);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniTry createTry(@NotNull List<@NotNull UniResource> resources,
                                     @NotNull UniBlock body,
                                     @NotNull List<@NotNull UniCatch> catches,
                                     @Nullable UniBlock finallyBlock) {
        Stream<IJResource> ijResources = checkList(resources, IJResource.class);
        IJBlock ijBody = check(body, IJBlock.class);
        Stream<IJCatch> ijCatches = checkList(catches, IJCatch.class);
        IJBlock ijFinally = check(finallyBlock, IJBlock.class);

        PsiTryStatement result = (PsiTryStatement) factory.createStatementFromText(
                "try(resources) {} finally {}", null);
        PsiResourceList resourceList = result.getResourceList();

        assert resourceList != null;
        assert result.getTryBlock() != null;
        assert result.getFinallyBlock() != null;

        resourceList.forEach(PsiResourceListElement::delete);
        ijResources.map(IJResource::getResourceElement).forEach(resourceList::add);

        result.getTryBlock().replace(ijBody.getElement());
        ijCatches.map(IJCatch::getElement).forEach(element -> result.addBefore(element, result.getFinallyBlock()));

        if (ijFinally != null)
            result.getFinallyBlock().replace(ijFinally.getElement());
        else result.getFinallyBlock().delete();

        return new IJTry(result);
    }

    @Override
    public @NotNull UniCatch createCatch(@NotNull UniVariable variable, @NotNull UniBlock body) {
        return null;
    }

    @Override
    public @NotNull UniConditional createTernary(@NotNull UniExpression condition, @NotNull UniExpression thenBlock,
                                                 @NotNull UniExpression elseBlock) {
        return null;
    }

    @Override
    public @NotNull UniIf createIf(@NotNull UniExpression condition, @NotNull UniStatement thenBlock,
                                   @Nullable UniStatement elseBlock) {
        return null;
    }

    @Override
    public @NotNull UniExpressionStatement createExecution(@NotNull UniExpression expression) {
        return null;
    }

    @Override
    public @NotNull UniBreak createBreak(@Nullable String label) {
        return null;
    }

    @Override
    public @NotNull UniContinue createContinue(@Nullable String label) {
        return null;
    }

    @Override
    public @NotNull UniReturn createReturn(@NotNull UniExpression value) {
        return null;
    }

    @Override
    public @NotNull UniThrow createThrow(@NotNull UniExpression value) {
        return null;
    }

    @Override
    public @NotNull UniAssert createAssert(@NotNull UniExpression condition, @Nullable UniExpression details) {
        return null;
    }

    @Override
    public @NotNull UniMethodInvocation createMethodInvocation(@NotNull UniExpression method,
                                                               @NotNull List<@NotNull UniType> argumentTypes,
                                                               @NotNull List<@NotNull UniExpression> args) {
        return null;
    }

    @Override
    public @NotNull UniNewClass createNewClass(@NotNull UniExpression enclosing,
                                               @NotNull List<@NotNull UniType> argumentTypes,
                                               @NotNull List<@NotNull UniExpression> args, @NotNull UniType classType) {
        return null;
    }

    @Override
    public @NotNull UniNewArray createNewArrayWithDimension(@NotNull UniType elementType,
                                                            @NotNull List<@NotNull UniExpression> dimensions) {
        return null;
    }

    @Override
    public @NotNull UniNewArray createNewArrayWithElements(@NotNull UniType elementType,
                                                           @NotNull List<@NotNull UniExpression> elements) {
        return null;
    }

    @Override
    public @NotNull UniParenthesized createParenthesized(@NotNull UniExpression expression) {
        return null;
    }

    @Override
    public @NotNull UniAssignment createAssignment(@NotNull UniExpression lhs, @NotNull UniExpression rhs) {
        return null;
    }

    @Override
    public @NotNull UniCompoundAssignment createCompoundAssignment(UniElement.@NotNull Tag opcode,
                                                                   @NotNull UniExpression lhs,
                                                                   @NotNull UniExpression rhs) {
        return null;
    }

    @Override
    public @NotNull UniUnary createUnary(UniElement.@NotNull Tag opcode, @NotNull UniExpression argument) {
        return null;
    }

    @Override
    public @NotNull UniBinary createBinary(UniElement.@NotNull Tag opcode, @NotNull UniExpression lhs,
                                           @NotNull UniExpression rhs) {
        return null;
    }

    @Override
    public @NotNull UniTypeCast createTypeCast(@NotNull UniType type, @NotNull UniExpression expression) {
        return null;
    }

    @Override
    public @NotNull UniInstanceOf createInstanceOf(@NotNull UniExpression expression, @NotNull UniType type) {
        return null;
    }

    @Override
    public @NotNull UniArrayAccess createArrayAccess(@NotNull UniExpression array, @NotNull UniExpression index) {
        return null;
    }

    @Override
    public @NotNull UniIdentifier createThis() {
        return null;
    }

    @Override
    public @NotNull UniIdentifier createIdentifier(@NotNull String name) {
        return null;
    }

    @Override
    public @NotNull UniLiteral createNull() {
        return null;
    }

    @Override
    public @NotNull UniLiteral createLiteral(@NotNull TypeTag tag, @NotNull Object value) {
        return null;
    }

    @Override
    public @NotNull UniLiteral createStringLiteral(@NotNull String value) {
        return null;
    }

    @Override
    public @NotNull UniAnnotation createAnnotation(@NotNull Class<?> annotationType,
                                                   @NotNull List<@NotNull UniAnnotationAttribute> attributes) {
        return null;
    }

    @Override
    public @NotNull UniAnnotation createAnnotation(@NotNull UniType annotationType,
                                                   @NotNull List<@NotNull UniAnnotationAttribute> attributes) {
        return null;
    }

    @Override
    public @NotNull UniAnnotationAttribute createAnnotationAttribute(@NotNull String name,
                                                                     @NotNull UniAnnotationValue value) {
        return null;
    }

    @Override
    public @NotNull UniErroneous createErroneous(@NotNull List<? extends @NotNull UniElement> errors) {
        return null;
    }

    @Override
    public @NotNull UniLet createLet(@NotNull List<@NotNull UniStatement> definitions,
                                     @NotNull UniExpression expression) {
        return null;
    }

    @Override
    public @NotNull UniFieldAccess createFieldAccess(@NotNull Class<?> selected, @NotNull String name) {
        return null;
    }

    @Override
    public @NotNull UniFieldAccess createFieldAccess(@NotNull UniType selected, @NotNull String name) {
        return null;
    }

    @Override
    public @NotNull UniFieldAccess createFieldAccess(@NotNull UniExpression expression, @NotNull String name) {
        return null;
    }

    @Override
    public @NotNull UniFieldAccess createClassLiteral(@NotNull UniClassType type) {
        return null;
    }

    @Override
    public @NotNull UniFieldAccess createClassLiteral(@NotNull Class<?> type) {
        return null;
    }

    @Contract("null, _ -> null")
    protected <T> T check(Object element, Class<T> type) {
        if (element == null) return null;
        if (!type.isInstance(element))
            throw new IllegalArgumentException("Element " + element + " is not of type " + type);
        return type.cast(element);
    }

    protected <T> Stream<T> checkList(List<?> list, Class<T> type) {
        for (Object element : list) {
            if (!type.isInstance(element))
                throw new IllegalArgumentException("Element " + element + " is not of type " + type.getName());
        }
        return list.stream().map(type::cast);
    }
}
