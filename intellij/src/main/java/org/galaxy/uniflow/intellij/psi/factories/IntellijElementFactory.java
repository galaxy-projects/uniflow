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
import org.galaxy.uniflow.intellij.psi.elements.IJAnnotationAttribute;
import org.galaxy.uniflow.intellij.psi.elements.IJCatch;
import org.galaxy.uniflow.intellij.psi.elements.IJDefaultCaseLabel;
import org.galaxy.uniflow.intellij.psi.elements.resources.IJExpressionResource;
import org.galaxy.uniflow.intellij.psi.elements.resources.IJResource;
import org.galaxy.uniflow.intellij.psi.elements.resources.IJVariableResource;
import org.galaxy.uniflow.intellij.psi.expression.*;
import org.galaxy.uniflow.intellij.psi.statements.*;
import org.galaxy.uniflow.intellij.psi.types.IJClassType;
import org.galaxy.uniflow.intellij.psi.types.IJType;
import org.galaxy.uniflow.intellij.psi.types.elements.IJExpressionType;
import org.galaxy.uniflow.intellij.psi.types.elements.IJTypeParameter;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.galaxy.uniflow.intellij.psi.util.IJUtils.check;
import static org.galaxy.uniflow.intellij.psi.util.IJUtils.checkList;

public class IntellijElementFactory implements UniElementFactory {

    protected final PsiElementFactory factory;
    protected final PsiJavaParserFacade parser;
    protected final PsiFileFactory files;

    public IntellijElementFactory(PsiElementFactory factory, PsiJavaParserFacade parser, PsiFileFactory files) {
        this.factory = factory;
        this.parser = parser;
        this.files = files;
    }

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
    @SuppressWarnings("rawtypes")
    public @NotNull UniSwitch createSwitch(@NotNull UniExpression selector,
                                           @NotNull List<@NotNull UniJdk8Case> cases) {
        IJExpression<?> ijSelector = check(selector, IJExpression.class);
        Stream<IJCase> ijCases = checkList(cases, IJCase.class);

        PsiSwitchStatement newSwitch = (PsiSwitchStatement) factory.createStatementFromText("switch(cond) {}", null);
        PsiCodeBlock body = newSwitch.getBody();

        assert newSwitch.getExpression() != null;
        assert ijSelector.getElement() != null;
        assert body != null;

        newSwitch.getExpression().replace(ijSelector.getElement());
        ijCases.map(IJCase::getElement)
                .filter(Objects::nonNull)
                .forEach(body::add);

        return new IJSwitchStatement(newSwitch);
    }

    @Override
    public @NotNull UniJdk8Case createCase(@NotNull UniCaseLabel label,
                                           @NotNull List<@NotNull UniStatement> statements) {
        List<PsiStatement> psiStatements = checkList(statements, IJStatement.class)
                .map(statement -> (PsiStatement) statement.getElement())
                .toList();

        PsiSwitchLabelStatement newCase = (PsiSwitchLabelStatement) factory.createStatementFromText(
                "case \"hello\":", null);

        assert newCase.getCaseLabelElementList() != null;
        newCase.getCaseLabelElementList().getElements()[0].delete();
        newCase.getCaseLabelElementList().add(IntellijUnwrapper.unwrap(label));

        psiStatements.forEach(newCase::add);

        return new IJJava8Case(newCase, psiStatements);
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
        IJVariable ijVariable = check(variable, IJVariable.class);
        IJBlock ijBody = check(body, IJBlock.class);

        PsiCatchSection catchSection = factory.createCatchSection(
                ijVariable.getElement().getType(), variable.getName(), null);

        if (catchSection.getCatchBlock() != null)
            catchSection.getCatchBlock().replace(ijBody.getElement());
        else catchSection.add(ijBody.getElement());

        return new IJCatch(catchSection);
    }

    @Override
    public @NotNull UniConditional createTernary(@NotNull UniExpression condition,
                                                 @NotNull UniExpression thenBlock,
                                                 @NotNull UniExpression elseBlock) {
        IJExpression<?> ijCondition = check(condition, IJExpression.class);
        IJExpression<?> ijThenBlock = check(thenBlock, IJExpression.class);
        IJExpression<?> ijElseBlock = check(elseBlock, IJExpression.class);

        PsiConditionalExpression conditional = (PsiConditionalExpression) factory.createExpressionFromText(
                "a ? b : c", null);

        assert conditional.getThenExpression() != null;
        assert conditional.getElseExpression() != null;

        conditional.getCondition().replace(ijCondition.getElement());
        conditional.getThenExpression().replace(ijThenBlock.getElement());
        conditional.getElseExpression().replace(ijElseBlock.getElement());

        return new IJConditional(conditional);
    }

    @Override
    public @NotNull UniIf createIf(@NotNull UniExpression condition,
                                   @NotNull UniStatement thenBlock,
                                   @Nullable UniStatement elseBlock) {
        IJExpression<?> ijCondition = check(condition, IJExpression.class);
        IJStatement<?> ijThenBlock = check(thenBlock, IJStatement.class);
        IJStatement<?> ijElseBlock = check(elseBlock, IJStatement.class);

        PsiIfStatement result = (PsiIfStatement) factory.createStatementFromText("if (a) {} else {}", null);

        assert result.getCondition() != null;
        assert result.getThenBranch() != null;
        assert result.getElseBranch() != null;

        result.getCondition().replace(ijCondition.getElement());
        result.getThenBranch().replace(ijThenBlock.getElement());
        if (ijElseBlock != null)
            result.getElseBranch().replace(ijElseBlock.getElement());
        else result.getElseBranch().delete();

        return new IJIf(result);
    }

    @Override
    public @NotNull UniExpressionStatement createExecution(@NotNull UniExpression expression) {
        IJExpression<?> ijExpression = check(expression, IJExpression.class);
        PsiExpression psiExpression = ijExpression.getElement();
        PsiExpressionStatement result = (PsiExpressionStatement) factory.createStatementFromText("x;", null);

        result.getExpression().replace(psiExpression);

        return new IJExpressionStatement(result);
    }

    @Override
    public @NotNull UniBreak createBreak(@Nullable String label) {
        String breakString = label != null ? "break " + label + ";" : "break;";

        return new IJBreak((PsiBreakStatement) factory.createStatementFromText(breakString, null));
    }

    @Override
    public @NotNull UniContinue createContinue(@Nullable String label) {
        String continueString = label != null ? "continue" + label + ";" : "continue;";

        return new IJContinue((PsiContinueStatement) factory.createStatementFromText(continueString, null));
    }

    @Override
    public @NotNull UniReturn createReturn(@NotNull UniExpression value) {
        IJExpression<?> ijValue = check(value, IJExpression.class);
        PsiReturnStatement result = (PsiReturnStatement) factory.createStatementFromText("return 0;", null);

        if (result.getReturnValue() != null)
            result.getReturnValue().replace(ijValue.getElement());
        else result.add(ijValue.getElement());

        return new IJReturn(result);
    }

    @Override
    public @NotNull UniThrow createThrow(@NotNull UniExpression value) {
        IJExpression<?> ijValue = check(value, IJExpression.class);
        PsiThrowStatement result = (PsiThrowStatement) factory.createStatementFromText("throw null;", null);

        if (result.getException() != null)
            result.getException().replace(ijValue.getElement());
        else result.add(ijValue.getElement());

        return new IJThrow(result);
    }

    @Override
    public @NotNull UniAssert createAssert(@NotNull UniExpression condition, @Nullable UniExpression details) {
        IJExpression<?> ijCondition = check(condition, IJExpression.class);
        IJExpression<?> ijDetails = check(details, IJExpression.class);
        PsiAssertStatement result = (PsiAssertStatement) factory.createStatementFromText("assert true : \"\";", null);

        assert result.getAssertCondition() != null;
        assert result.getAssertDescription() != null;

        result.getAssertCondition().replace(ijCondition.getElement());
        if (ijDetails != null)
            result.getAssertDescription().replace(ijDetails.getElement());
        else result.getAssertDescription().delete();

        return new IJAssert(result);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniMethodInvocation createMethodInvocation(@NotNull UniExpression method,
                                                               @NotNull List<@NotNull UniType> argumentTypes,
                                                               @NotNull List<@NotNull UniExpression> args) {
        IJExpression<?> ijMethod = check(method, IJExpression.class);
        Stream<IJType> ijArgumentTypes = checkList(argumentTypes, IJType.class);
        Stream<IJExpression> ijArgs = checkList(args, IJExpression.class);
        PsiReferenceExpression methodRef = (PsiReferenceExpression) ijMethod.getElement();

        PsiMethodCallExpression call = (PsiMethodCallExpression) factory.createStatementFromText("foo()", null);
        PsiReferenceParameterList typeArgumentList = call.getTypeArgumentList();
        PsiExpressionList argumentList = call.getArgumentList();

        call.getMethodExpression().replace(methodRef);
        ijArgumentTypes.map(IntellijUnwrapper::unwrapType).forEach(typeArgumentList::add);
        ijArgs.map(IJExpression::getElement).forEach(argumentList::add);

        return new IJMethodInvocation(call);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniNewClass createNewClass(@NotNull UniExpression enclosing,
                                               @NotNull List<@NotNull UniType> argumentTypes,
                                               @NotNull List<@NotNull UniExpression> args,
                                               @NotNull UniType classType) {
        Stream<IJType> ijArgumentTypes = checkList(argumentTypes, IJType.class);
        Stream<IJExpression> ijArgs = checkList(args, IJExpression.class);
        PsiNewExpression result = (PsiNewExpression) factory.createExpressionFromText("new Object()", null);
        PsiReferenceParameterList typeArgumentList = result.getTypeArgumentList();
        PsiExpressionList argumentList = result.getArgumentList();

        assert result.getClassReference() != null;
        assert argumentList != null;

        result.getClassReference().replace(IntellijUnwrapper.unwrapType(classType));
        ijArgumentTypes.map(IntellijUnwrapper::unwrapType).forEach(typeArgumentList::add);
        ijArgs.map(IJExpression::getElement).forEach(argumentList::add);

        return new IJNewClass(result);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniNewArray createNewArrayWithDimension(@NotNull UniType elementType,
                                                            @NotNull List<@NotNull UniExpression> dimensions) {
        Stream<IJExpression> ijDimensions = checkList(dimensions, IJExpression.class);
        String dimensionString = "[]".repeat(dimensions.size());

        PsiNewExpression result = (PsiNewExpression) factory.createExpressionFromText(
                "new Object" + dimensionString, null);
        Iterator<PsiElement> dimensionIterator = ijDimensions.map(IJExpression::getElement).iterator();
        PsiExpression[] arrayDimensions = result.getArrayDimensions();

        assert result.getClassReference() != null;

        result.getClassReference().replace(IntellijUnwrapper.unwrapType(elementType));
        for (int i = 0; i < arrayDimensions.length && dimensionIterator.hasNext(); i++) {
            arrayDimensions[i].replace(dimensionIterator.next());
        }

        return new IJNewArray(result);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniNewArray createNewArrayWithElements(@NotNull UniType elementType,
                                                           @NotNull List<@NotNull UniExpression> elements) {
        Stream<IJExpression> ijElements = checkList(elements, IJExpression.class);

        PsiNewExpression result = (PsiNewExpression) factory.createExpressionFromText("new Object[] {}", null);
        PsiArrayInitializerExpression arrayInitializer = result.getArrayInitializer();

        assert result.getClassReference() != null;
        assert arrayInitializer != null;

        result.getClassReference().replace(IntellijUnwrapper.unwrapType(elementType));
        ijElements.map(IJExpression::getElement).forEach(arrayInitializer::add);

        return new IJNewArray(result);
    }

    @Override
    public @NotNull UniParenthesized createParenthesized(@NotNull UniExpression expression) {
        IJExpression<?> ijExpression = check(expression, IJExpression.class);

        PsiParenthesizedExpression result = (PsiParenthesizedExpression) factory.createExpressionFromText(
                "(expr)", null);

        if (result.getExpression() != null)
            result.getExpression().replace(ijExpression.getElement());
        else result.add(ijExpression.getElement());

        return new IJParenthesized(result);
    }

    @Override
    public @NotNull UniAssignment createAssignment(@NotNull UniExpression lhs, @NotNull UniExpression rhs) {
        IJExpression<?> ijLeft = check(lhs, IJExpression.class);
        IJExpression<?> ijRight = check(rhs, IJExpression.class);

        PsiAssignmentExpression result = (PsiAssignmentExpression) factory.createExpressionFromText("a = b", null);

        result.getLExpression().replace(ijLeft.getElement());
        if (result.getRExpression() != null)
            result.getRExpression().replace(ijRight.getElement());
        else result.add(ijRight.getElement());

        return new IJAssignment(result);
    }

    @Override
    public @NotNull UniCompoundAssignment createCompoundAssignment(@NotNull Opcode opcode,
                                                                   @NotNull UniExpression lhs,
                                                                   @NotNull UniExpression rhs) {
        String token = IJCompoundAssignment.OPCODE_CHAR_MAP.get(opcode);

        if (token == null) throw new IllegalArgumentException("Opcode " + opcode + " not supported");
        IJExpression<?> ijLeft = check(lhs, IJExpression.class);
        IJExpression<?> ijRight = check(rhs, IJExpression.class);

        PsiAssignmentExpression result = (PsiAssignmentExpression) factory.createExpressionFromText(
                "a %s b".formatted(token), null);

        result.getLExpression().replace(ijLeft.getElement());
        if (result.getRExpression() != null)
            result.getRExpression().replace(ijRight.getElement());
        else result.add(ijRight.getElement());

        return new IJCompoundAssignment(result);
    }

    @Override
    public @NotNull UniUnary createUnary(@NotNull Opcode opcode, @NotNull UniExpression argument) {
        String token = IJUnary.OPCODE_CHAR_MAP.get(opcode);

        if (token == null) throw new IllegalArgumentException("Opcode " + opcode + " not supported");
        IJExpression<?> ijArgument = check(argument, IJExpression.class);

        PsiUnaryExpression result = (PsiUnaryExpression) factory.createExpressionFromText(token, null);

        if (result.getOperand() != null)
            result.getOperand().replace(ijArgument.getElement());
        else result.add(ijArgument.getElement());

        return new IJUnary(result);
    }

    @Override
    public @NotNull UniBinary createBinary(@NotNull Opcode opcode,
                                           @NotNull UniExpression lhs,
                                           @NotNull UniExpression rhs) {
        String token = IJBinary.OPCODE_CHAR_MAP.get(opcode);
        if (token == null) throw new IllegalArgumentException("Opcode " + opcode + " not supported");
        IJExpression<?> ijLeft = check(lhs, IJExpression.class);
        IJExpression<?> ijRight = check(rhs, IJExpression.class);

        PsiBinaryExpression result = (PsiBinaryExpression) factory.createExpressionFromText(token, null);

        result.getLOperand().replace(ijLeft.getElement());
        if (result.getROperand() != null)
            result.getROperand().replace(ijRight.getElement());
        else result.add(ijRight.getElement());

        return new IJBinary(result);
    }

    @Override
    public @NotNull UniTypeCast createTypeCast(@NotNull UniType type, @NotNull UniExpression expression) {
        IJExpression<?> ijExpression = check(expression, IJExpression.class);
        PsiTypeCastExpression result = (PsiTypeCastExpression) factory.createExpressionFromText("(String) a", null);

        assert result.getCastType() != null;

        result.getCastType().replace(IntellijUnwrapper.unwrapType(type));
        if (result.getOperand() != null)
            result.getOperand().replace(ijExpression.getElement());
        else result.add(ijExpression.getElement());

        return new IJTypeCast(result);
    }

    @Override
    public @NotNull UniInstanceOf createInstanceOf(@NotNull UniExpression expression, @NotNull UniType type) {
        IJExpression<?> ijExpression = check(expression, IJExpression.class);

        PsiInstanceOfExpression result = (PsiInstanceOfExpression) factory.createExpressionFromText(
                "a instanceof String", null);

        assert result.getCheckType() != null;

        result.getOperand().replace(ijExpression.getElement());
        result.getCheckType().replace(IntellijUnwrapper.unwrapType(type));

        return new IJInstanceOf(result);
    }

    @Override
    public @NotNull UniArrayAccess createArrayAccess(@NotNull UniExpression array, @NotNull UniExpression index) {
        IJExpression<?> ijArray = check(array, IJExpression.class);
        IJExpression<?> ijIndex = check(index, IJExpression.class);
        PsiArrayAccessExpression result = (PsiArrayAccessExpression) factory.createExpressionFromText("a[0]", null);

        result.getArrayExpression().replace(ijArray.getElement());
        if (result.getIndexExpression() != null)
            result.getIndexExpression().replace(ijIndex.getElement());
        else result.add(ijArray.getElement());

        return new IJArrayAccess(result);
    }

    @Override
    public @NotNull UniIdentifier createThis() {
        return new IJIdentifier(factory.createIdentifier("this"));
    }

    @Override
    public @NotNull UniIdentifier createIdentifier(@NotNull String name) {
        return new IJIdentifier(factory.createIdentifier(name));
    }

    @Override
    public @NotNull UniLiteral createNull() {
        return new IJLiteral((PsiLiteralExpression) factory.createExpressionFromText("null", null));
    }

    @Override
    public @NotNull UniLiteral createLiteral(@NotNull TypeTag tag, @NotNull Object value) {
        return new IJLiteral((PsiLiteralExpression) factory.createExpressionFromText(String.valueOf(value), null));
    }

    @Override
    public @NotNull UniLiteral createStringLiteral(@NotNull String value) {
        return new IJLiteral((PsiLiteralExpression) factory.createExpressionFromText(value, null));
    }

    @Override
    public @NotNull UniAnnotation createAnnotation(@NotNull Class<?> annotationType,
                                                   @NotNull List<@NotNull UniAnnotationAttribute> attributes) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createAnnotation(typeFactory.createClassType(annotationType), attributes);
    }

    @Override
    public @NotNull UniAnnotation createAnnotation(@NotNull UniType annotationType,
                                                   @NotNull List<@NotNull UniAnnotationAttribute> attributes) {
        IJClassType ijAnnotationType = check(annotationType, IJClassType.class);
        Stream<IJAnnotationAttribute> ijAttributes = checkList(attributes, IJAnnotationAttribute.class);

        PsiAnnotation annotation = parser.createAnnotationFromText("@Annotation()", null);
        PsiAnnotationParameterList parameterList = annotation.getParameterList();

        assert annotation.getNameReferenceElement() != null;

        annotation.getNameReferenceElement().replace(IntellijUnwrapper.unwrapReference(ijAnnotationType));
        ijAttributes.map(IJAnnotationAttribute::getElement).forEach(parameterList::add);

        return new IJAnnotation(annotation);
    }

    @Override
    public @NotNull UniAnnotationAttribute createAnnotationAttribute(@NotNull String name,
                                                                     @NotNull UniAnnotationValue value) {
        PsiAnnotationMemberValue unwrapped = IntellijUnwrapper.unwrap(value);
        PsiAnnotation annotation = parser.createAnnotationFromText(
                "@Annotation(%s = %s)".formatted(name, unwrapped.getText()), null);
        PsiNameValuePair attribute = annotation.getParameterList().getAttributes()[0];

        return new IJAnnotationAttribute(attribute);
    }

    @Override
    public @NotNull UniFieldAccess createFieldAccess(@NotNull Class<?> selected, @NotNull String name) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createFieldAccess(typeFactory.createClassType(selected), name);
    }

    @Override
    public @NotNull UniFieldAccess createFieldAccess(@NotNull UniType selected, @NotNull String name) {
        IJExpressionType<?> ijType = check(selected, IJExpressionType.class);

        return new IJFieldAccess((PsiReferenceExpression) factory.createExpressionFromText(
                ijType.getElement().getText() + "." + name, null));
    }

    @Override
    public @NotNull UniFieldAccess createFieldAccess(@NotNull UniExpression expression, @NotNull String name) {
        IJExpression<?> ijExpression = check(expression, IJExpression.class);

        return new IJFieldAccess((PsiReferenceExpression) factory.createExpressionFromText(
                ijExpression.getElement().getText() + "." + name, null));
    }

    @Override
    public @NotNull UniFieldAccess createClassLiteral(@NotNull UniClassType type) {
        IJClassType ijType = check(type, IJClassType.class);
        PsiClass resolved = ijType.getRawType().resolve();

        if (resolved == null)
            throw new IllegalArgumentException("Class " + ijType.getRawType() + " not found");
        return new IJFieldAccess(factory.createReferenceExpression(resolved));
    }

    @Override
    public @NotNull UniFieldAccess createClassLiteral(@NotNull Class<?> type) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createClassLiteral(typeFactory.createClassType(type));
    }
}
