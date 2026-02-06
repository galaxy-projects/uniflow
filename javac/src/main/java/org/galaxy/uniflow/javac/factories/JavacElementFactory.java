package org.galaxy.uniflow.javac.factories;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
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
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.*;
import org.galaxy.uniflow.javac.annotations.JavacAnnotation;
import org.galaxy.uniflow.javac.annotations.JavacAnnotationAttribute;
import org.galaxy.uniflow.javac.elements.JavacCaseLabel;
import org.galaxy.uniflow.javac.elements.JavacCatch;
import org.galaxy.uniflow.javac.elements.JavacDefaultCaseLabel;
import org.galaxy.uniflow.javac.elements.resources.JavacExpressionResource;
import org.galaxy.uniflow.javac.elements.resources.JavacResource;
import org.galaxy.uniflow.javac.elements.resources.JavacVariableResource;
import org.galaxy.uniflow.javac.expression.*;
import org.galaxy.uniflow.javac.statements.*;
import org.galaxy.uniflow.javac.types.JavacClassType;
import org.galaxy.uniflow.javac.types.JavacExpressionType;
import org.galaxy.uniflow.javac.types.JavacType;
import org.galaxy.uniflow.javac.types.JavacTypeParameter;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac8.statements.Javac8Case;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.galaxy.uniflow.javac.util.JavacUtils.*;

public abstract class JavacElementFactory implements UniElementFactory {

    protected final TreeMaker treeMaker;

    public JavacElementFactory() {
        treeMaker = JavacUniflow.getInstance().treeMaker;
    }

    @Override
    public @NotNull UniPackage createPackage(@NotNull String name) {
        return new JavacPackage(treeMaker.PackageDecl(
                com.sun.tools.javac.util.List.nil(),
                treeMaker.Ident(NameUtils.name(name))
        ));
    }

    @Override
    public @NotNull UniModifiers createModifiers(@NotNull List<@NotNull UniModifier> modifiers,
                                                 @NotNull List<@NotNull UniAnnotation> annotations) {
        Stream<JavacAnnotation> javacAnnotations = checkList(annotations, JavacAnnotation.class);

        return new JavacModifiers(treeMaker.Modifiers(
                UniModifier.asLongFlags(modifiers),
                mapToList(javacAnnotations, JavacAnnotation::getTree)
        ));
    }

    @Override
    public @NotNull UniClass createClass(@NotNull UniModifiers modifiers,
                                         @NotNull String name,
                                         @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                         @Nullable UniType extending,
                                         @NotNull List<@NotNull UniType> implementing) {
        return createClass(modifiers, name, typeParameters, extending, implementing, Collections.emptyList());
    }

    @Override
    @SuppressWarnings({ "DuplicatedCode", "rawtypes" })
    public @NotNull UniClass createClass(@NotNull UniModifiers modifiers,
                                         @NotNull String name,
                                         @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                         @Nullable UniType extending,
                                         @NotNull List<@NotNull UniType> implementing,
                                         @NotNull List<@NotNull UniClassInitializer> initializers) {
        JavacModifiers javacModifiers = check(modifiers, JavacModifiers.class);
        Stream<JavacTypeParameter> javacTypeParameters = checkList(typeParameters, JavacTypeParameter.class);
        JavacExpressionType javacExtending = check(extending, JavacExpressionType.class);
        Stream<JavacExpressionType> javacImplementing =
                checkList(implementing, JavacExpressionType.class);
        Stream<JavacClassInitializer> javacInitializers = checkList(initializers, JavacClassInitializer.class);

        return new JavacClass(treeMaker.ClassDef(
                javacModifiers.getTree(),
                NameUtils.name(name),
                mapToList(javacTypeParameters, JavacTypeParameter::getTree),
                javacExtending != null ? (JCTree.JCExpression) javacExtending.getExpression() : null,
                mapToList(javacImplementing, type -> (JCTree.JCExpression) type.getExpression()),
                mapToList(javacInitializers, JavacClassInitializer::getTree)
        ));
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
        Stream<JavacAnnotation> javacAnnotations = checkList(annotations, JavacAnnotation.class);
        JavacExpressionType<?, ?> javacType = check(type, JavacExpressionType.class);
        JavacExpression<?> javacInit = check(init, JavacExpression.class);

        return new JavacVariable(treeMaker.VarDef(
                treeMaker.Modifiers(0, mapToList(javacAnnotations, JavacAnnotation::getTree)),
                NameUtils.name(name),
                javacType.getExpression(),
                javacInit != null ? javacInit.getTree() : null,
                false
        ));
    }

    @Override
    public @NotNull UniParameter createParameter(@NotNull List<@NotNull UniAnnotation> annotations,
                                                 @NotNull String name,
                                                 @NotNull Class<?> type) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createParameter(annotations, name, typeFactory.createClassType(type));
    }

    @Override
    public @NotNull UniParameter createParameter(@NotNull List<@NotNull UniAnnotation> annotations,
                                                 @NotNull String name,
                                                 @NotNull UniType type) {
        Stream<JavacAnnotation> javacAnnotations = checkList(annotations, JavacAnnotation.class);
        JavacExpressionType<?, ?> javacType = check(type, JavacExpressionType.class);

        return new JavacParameter(treeMaker.VarDef(
                treeMaker.Modifiers(0, mapToList(javacAnnotations, JavacAnnotation::getTree)),
                NameUtils.name(name),
                javacType.getExpression(),
                null,
                false
        ));
    }

    @Override
    public @NotNull UniEmpty createSkip() {
        return new JavacEmpty(treeMaker.Skip());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniBlock createBlock(boolean isStatic, @NotNull List<@NotNull UniStatement> statements) {
        Stream<JavacStatement> javacStatements = checkList(statements, JavacStatement.class);
        long flags = isStatic ? Flags.STATIC : 0;

        return new JavacBlock(treeMaker.Block(
                flags,
                mapToList(javacStatements, st -> (JCTree.JCStatement) st.getTree())
        ));
    }

    @Override
    public @NotNull UniDoWhileLoop createDoWhileLoop(@NotNull UniStatement body, @NotNull UniExpression condition) {
        JavacStatement<?> javacBody = check(body, JavacStatement.class);
        JavacExpression<?> javacCondition = check(condition, JavacExpression.class);

        return new JavacDoWhileLoop(treeMaker.DoLoop(javacBody.getTree(), javacCondition.getTree()));
    }

    @Override
    public @NotNull UniWhileLoop createWhileLoop(@NotNull UniExpression condition, @NotNull UniStatement body) {
        JavacExpression<?> javacCondition = check(body, JavacExpression.class);
        JavacStatement<?> javacBody = check(condition, JavacStatement.class);

        return new JavacWhileLoop(treeMaker.WhileLoop(javacCondition.getTree(), javacBody.getTree()));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniForLoop createForLoop(@NotNull List<@NotNull UniStatement> init,
                                             @NotNull UniExpression condition,
                                             @NotNull List<@NotNull UniExpressionStatement> step,
                                             @NotNull UniStatement body) {
        Stream<JavacStatement> javacInit = checkList(init, JavacStatement.class);
        JavacExpression<?> javacCondition = check(condition, JavacExpression.class);
        Stream<JavacExpressionStatement> javacStep = checkList(step, JavacExpressionStatement.class);
        JavacStatement<?> javacBody = check(body, JavacStatement.class);

        return new JavacForLoop(treeMaker.ForLoop(
                mapToList(javacInit, st -> (JCTree.JCStatement) st.getTree()),
                javacCondition.getTree(),
                mapToList(javacStep, JavacExpressionStatement::getTree),
                javacBody.getTree()
        ));
    }

    @Override
    public @NotNull UniEnhancedForLoop createForEachLoop(@NotNull UniParameter variable,
                                                         @NotNull UniExpression iterable,
                                                         @NotNull UniStatement body) {
        JavacParameter javacParameter = check(variable, JavacParameter.class);
        JavacExpression<?> javacIterable = check(iterable, JavacExpression.class);
        JavacStatement<?> javacBody = check(body, JavacStatement.class);

        return new JavacEnhancedForLoop(treeMaker.ForeachLoop(
                javacParameter.getTree(),
                javacIterable.getTree(),
                javacBody.getTree()
        ));
    }

    @Override
    public @NotNull UniLabel createLabel(@NotNull String name, @NotNull UniStatement body) {
        JavacStatement<?> javacBody = check(body, JavacStatement.class);

        return new JavacLabel(treeMaker.Labelled(
                NameUtils.name(name),
                javacBody.getTree()
        ));
    }

    @Override
    public @NotNull UniSwitch createSwitch(@NotNull UniExpression selector,
                                           @NotNull List<@NotNull UniJdk8Case> cases) {
        JavacExpression<?> javacSelector = check(selector, JavacExpression.class);
        Stream<Javac8Case> javacCases = checkList(cases, Javac8Case.class);

        return new JavacSwitch(treeMaker.Switch(
                javacSelector.getTree(),
                mapToList(javacCases, Javac8Case::getTree)
        ));
    }

    @Override
    public @NotNull UniDefaultCaseLabel createDefaultCase() {
        return new JavacDefaultCaseLabel(treeMaker.DefaultCaseLabel());
    }

    @Override
    public @NotNull UniSynchronized createSynchronized(@NotNull UniExpression lock, @NotNull UniBlock body) {
        JavacExpression<?> javacLock = check(lock, JavacExpression.class);
        JavacBlock javacBody = check(body, JavacBlock.class);

        return new JavacSynchronized(treeMaker.Synchronized(
                javacLock.getTree(),
                javacBody.getTree()
        ));
    }

    @Override
    public @NotNull UniExpressionResource createResource(@NotNull UniExpression expression) {
        return new JavacExpressionResource(expression);
    }

    @Override
    public @NotNull UniVariableResource createResource(@NotNull UniVariable variable) {
        return new JavacVariableResource(variable);
    }

    @Override
    public @NotNull UniTry createTry(@NotNull UniBlock body,
                                     @NotNull List<@NotNull UniCatch> catches,
                                     @Nullable UniBlock finallyBlock) {
        return createTry(Collections.emptyList(), body, catches, finallyBlock);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniTry createTry(@NotNull List<@NotNull UniResource> resources,
                                     @NotNull UniBlock body,
                                     @NotNull List<@NotNull UniCatch> catches,
                                     @Nullable UniBlock finallyBlock) {
        Stream<JavacResource> javacResources = checkList(resources, JavacResource.class);
        JavacBlock javacBody = check(body, JavacBlock.class);
        Stream<JavacCatch> javacCatches = checkList(catches, JavacCatch.class);
        JavacBlock javacFinally = check(finallyBlock, JavacBlock.class);

        return new JavacTry(treeMaker.Try(
                javacResources.map(JavacResource::getElement)
                        .map(JavacUnwrapper::unwrap)
                        .collect(com.sun.tools.javac.util.List.collector()),
                javacBody.getTree(),
                mapToList(javacCatches, JavacCatch::getTree),
                javacFinally != null ? javacFinally.getTree() : null
        ));
    }

    @Override
    public @NotNull UniCatch createCatch(@NotNull UniVariable variable, @NotNull UniBlock body) {
        JavacVariable javacVariable = check(variable, JavacVariable.class);
        JavacBlock javacBody = check(body, JavacBlock.class);

        return new JavacCatch(treeMaker.Catch(
                javacVariable.getTree(),
                javacBody.getTree()
        ));
    }

    @Override
    public @NotNull UniConditional createTernary(@NotNull UniExpression condition,
                                                 @NotNull UniExpression thenBlock,
                                                 @NotNull UniExpression elseBlock) {
        JavacExpression<?> javacCondition = check(condition, JavacExpression.class);
        JavacExpression<?> javacThenBlock = check(thenBlock, JavacExpression.class);
        JavacExpression<?> javacElseBlock = check(elseBlock, JavacExpression.class);

        return new JavacConditional(treeMaker.Conditional(
                javacCondition.getTree(),
                javacThenBlock.getTree(),
                javacElseBlock.getTree()
        ));
    }

    @Override
    public @NotNull UniIf createIf(@NotNull UniExpression condition,
                                   @NotNull UniStatement thenBlock,
                                   @Nullable UniStatement elseBlock) {
        JavacExpression<?> javacCondition = check(condition, JavacExpression.class);
        JavacStatement<?> javacThenBlock = check(thenBlock, JavacStatement.class);
        JavacStatement<?> javacElseBlock = check(elseBlock, JavacStatement.class);

        return new JavacIf(treeMaker.If(
                javacCondition.getTree(),
                javacThenBlock.getTree(),
                javacElseBlock != null ? javacElseBlock.getTree() : null
        ));
    }

    @Override
    public @NotNull UniExpressionStatement createExecution(@NotNull UniExpression expression) {
        JavacExpression<?> javacExpression = check(expression, JavacExpression.class);

        return new JavacExpressionStatement(treeMaker.Exec(javacExpression.getTree()));
    }

    @Override
    public @NotNull UniBreak createBreak(@Nullable String label) {
        return new JavacBreak(treeMaker.Break(label != null ? NameUtils.name(label) : null));
    }

    @Override
    public @NotNull UniContinue createContinue(@Nullable String label) {
        return new JavacContinue(treeMaker.Continue(label != null ? NameUtils.name(label) : null));
    }

    @Override
    public @NotNull UniReturn createReturn(@NotNull UniExpression value) {
        JavacExpression<?> javacValue = check(value, JavacExpression.class);

        return new JavacReturn(treeMaker.Return(javacValue.getTree()));
    }

    @Override
    public @NotNull UniThrow createThrow(@NotNull UniExpression value) {
        JavacExpression<?> javacValue = check(value, JavacExpression.class);

        return new JavacThrow(treeMaker.Throw(javacValue.getTree()));
    }

    @Override
    public @NotNull UniAssert createAssert(@NotNull UniExpression condition, @Nullable UniExpression details) {
        JavacExpression<?> javacCondition = check(condition, JavacExpression.class);
        JavacExpression<?> javacDetails = check(details, JavacExpression.class);

        return new JavacAssert(treeMaker.Assert(
                javacCondition.getTree(),
                javacDetails != null ? javacDetails.getTree() : null
        ));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniMethodInvocation createMethodInvocation(@NotNull UniExpression method,
                                                               @NotNull List<@NotNull UniType> argumentTypes,
                                                               @NotNull List<@NotNull UniExpression> args) {
        JavacExpression<?> javacMethod = check(method, JavacExpression.class);
        Stream<JavacExpressionType> javacArgumentTypes = checkList(argumentTypes, JavacExpressionType.class);
        Stream<JavacExpression> javacArgs = checkList(args, JavacExpression.class);

        return new JavacMethodInvocation(treeMaker.Apply(
                mapToList(javacArgumentTypes, type -> (JCTree.JCExpression) type.getExpression()),
                javacMethod.getTree(),
                mapToList(javacArgs, expr -> (JCTree.JCExpression) expr.getTree())
        ));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniNewClass createNewClass(@NotNull UniExpression enclosing,
                                               @NotNull List<@NotNull UniType> argumentTypes,
                                               @NotNull List<@NotNull UniExpression> args,
                                               @NotNull UniType classType) {
        JavacExpression<?> javacEnclosing = check(enclosing, JavacExpression.class);
        Stream<JavacExpressionType> javacArgumentTypes = checkList(argumentTypes, JavacExpressionType.class);
        Stream<JavacExpression> javacArgs = checkList(args, JavacExpression.class);
        JavacExpressionType<?, ?> javacClassType = check(classType, JavacExpressionType.class);

        return new JavacNewClass(treeMaker.NewClass(
                javacEnclosing.getTree(),
                mapToList(javacArgumentTypes, type -> (JCTree.JCExpression) type.getExpression()),
                javacClassType.getExpression(),
                mapToList(javacArgs, expr -> (JCTree.JCExpression) expr.getTree()),
                null
        ));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniNewArray createNewArrayWithDimension(@NotNull UniType elementType,
                                                            @NotNull List<@NotNull UniExpression> dimensions) {
        JavacExpressionType<?, ?> javacElementType = check(elementType, JavacExpressionType.class);
        Stream<JavacExpression> javacDimensions = checkList(dimensions, JavacExpression.class);

        return new JavacNewArray(treeMaker.NewArray(
                javacElementType.getExpression(),
                mapToList(javacDimensions, expr -> (JCTree.JCExpression) expr.getTree()),
                com.sun.tools.javac.util.List.nil()
        ));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniNewArray createNewArrayWithElements(@NotNull UniType elementType,
                                                           @NotNull List<@NotNull UniExpression> elements) {
        JavacExpressionType<?, ?> javacElementType = check(elementType, JavacExpressionType.class);
        Stream<JavacExpression> javacElements = checkList(elements, JavacExpression.class);

        return new JavacNewArray(treeMaker.NewArray(
                javacElementType.getExpression(),
                com.sun.tools.javac.util.List.nil(),
                mapToList(javacElements, expr -> (JCTree.JCExpression) expr.getTree())
        ));
    }

    @Override
    public @NotNull UniParenthesized createParenthesized(@NotNull UniExpression expression) {
        JavacExpression<?> javacExpression = check(expression, JavacExpression.class);

        return new JavacParenthesized(treeMaker.Parens(javacExpression.getTree()));
    }

    @Override
    public @NotNull UniAssignment createAssignment(@NotNull UniExpression lhs, @NotNull UniExpression rhs) {
        JavacExpression<?> javacLhs = check(lhs, JavacExpression.class);
        JavacExpression<?> javacRhs = check(rhs, JavacExpression.class);

        return new JavacAssignment(treeMaker.Assign(
                javacLhs.getTree(),
                javacRhs.getTree()
        ));
    }

    @Override
    public @NotNull UniCompoundAssignment createCompoundAssignment(@NotNull Opcode opcode,
                                                                   @NotNull UniExpression lhs,
                                                                   @NotNull UniExpression rhs) {
        JavacExpression<?> javacLhs = check(lhs, JavacExpression.class);
        JavacExpression<?> javacRhs = check(rhs, JavacExpression.class);

        return new JavacCompoundAssignment(treeMaker.Assignop(
                EnumUtils.convert(JCTree.Tag.class, opcode),
                javacLhs.getTree(),
                javacRhs.getTree()
        ));
    }

    @Override
    public @NotNull UniUnary createUnary(@NotNull Opcode opcode, @NotNull UniExpression argument) {
        JavacExpression<?> javacArgument = check(argument, JavacExpression.class);

        return new JavacUnary(treeMaker.Unary(
                EnumUtils.convert(JCTree.Tag.class, opcode),
                javacArgument.getTree()
        ));
    }

    @Override
    public @NotNull UniBinary createBinary(@NotNull Opcode opcode,
                                           @NotNull UniExpression lhs,
                                           @NotNull UniExpression rhs) {
        JavacExpression<?> javacLhs = check(lhs, JavacExpression.class);
        JavacExpression<?> javacRhs = check(rhs, JavacExpression.class);

        return new JavacBinary(treeMaker.Binary(
                EnumUtils.convert(JCTree.Tag.class, opcode),
                javacLhs.getTree(),
                javacRhs.getTree()
        ));
    }

    @Override
    public @NotNull UniTypeCast createTypeCast(@NotNull UniType type, @NotNull UniExpression expression) {
        JavacType<?, ?> javacType = check(type, JavacType.class);
        JavacExpression<?> javacExpression = check(expression, JavacExpression.class);

        if (javacType.getExpression() != null)
            return new JavacTypeCast(treeMaker.TypeCast(javacType.getExpression(), javacExpression.getTree()));
        return new JavacTypeCast(treeMaker.TypeCast(javacType.getRawType(), javacExpression.getTree()));
    }

    @Override
    public @NotNull UniInstanceOf createInstanceOf(@NotNull UniExpression expression, @NotNull UniType type) {
        JavacExpression<?> javacExpression = check(expression, JavacExpression.class);
        JavacType<?, ?> javacType = check(type, JavacType.class);

        return new JavacInstanceOf(treeMaker.TypeTest(javacExpression.getTree(), javacType.getExpression()));
    }

    @Override
    public @NotNull UniArrayAccess createArrayAccess(@NotNull UniExpression array, @NotNull UniExpression index) {
        JavacExpression<?> javacArray = check(array, JavacExpression.class);
        JavacExpression<?> javacIndex = check(index, JavacExpression.class);

        return new JavacArrayAccess(treeMaker.Indexed(javacArray.getTree(), javacIndex.getTree()));
    }

    @Override
    public @NotNull UniIdentifier createThis() {
        return new JavacIdentifier(treeMaker.Ident(JavacUniflow.getInstance().names._this));
    }

    @Override
    public @NotNull UniIdentifier createIdentifier(@NotNull String name) {
        return new JavacIdentifier(treeMaker.Ident(NameUtils.name(name)));
    }

    @Override
    public @NotNull UniLiteral createNull() {
        return new JavacLiteral(treeMaker.Literal(com.sun.tools.javac.code.TypeTag.BOT, null));
    }

    @Override
    public @NotNull UniLiteral createLiteral(@NotNull TypeTag tag, @NotNull Object value) {
        return new JavacLiteral(treeMaker.Literal(
                EnumUtils.convert(com.sun.tools.javac.code.TypeTag.class, tag),
                value
        ));
    }

    @Override
    public @NotNull UniLiteral createStringLiteral(@NotNull String value) {
        return new JavacLiteral(treeMaker.Literal(value));
    }

    @Override
    public @NotNull UniAnnotation createAnnotation(@NotNull Class<?> annotationType,
                                                   @NotNull List<@NotNull UniAnnotationAttribute> attributes) {
        UniType annotationTypeAsType = Uniflow.getInstance().getTypeFactory().createType(annotationType);

        return createAnnotation(annotationTypeAsType, attributes);
    }

    @Override
    public @NotNull UniAnnotation createAnnotation(@NotNull UniType annotationType,
                                                   @NotNull List<@NotNull UniAnnotationAttribute> attributes) {
        JavacType<?, ?> javacAnnotationType = check(annotationType, JavacType.class);
        Stream<JavacAnnotationAttribute> javacAttributes = checkList(attributes, JavacAnnotationAttribute.class);

        return new JavacAnnotation(treeMaker.Annotation(
                javacAnnotationType.getExpression(),
                mapToList(javacAttributes, JavacAnnotationAttribute::getTree)
        ));
    }

    @Override
    public @NotNull UniAnnotationAttribute createAnnotationAttribute(@NotNull String name,
                                                                     @NotNull UniAnnotationValue value) {
        if (!(value instanceof JavacAnnotationAttribute))
            throw new IllegalArgumentException("Value is not type of JavacAnnotationAttribute");

        return new JavacAnnotationAttribute(
                treeMaker.Assign(
                        treeMaker.Ident(NameUtils.name(name)),
                        JavacUnwrapper.unwrap(value)
                ),
                name,
                value
        );
    }

    @Override
    public @NotNull UniFieldAccess createFieldAccess(@NotNull Class<?> selected, @NotNull String name) {
        UniType selectedType = Uniflow.getInstance().getTypeFactory().createType(selected);

        return createFieldAccess(selectedType, name);
    }

    @Override
    public @NotNull UniFieldAccess createFieldAccess(@NotNull UniType selected, @NotNull String name) {
        JavacExpressionType<?, ?> javacSelected = check(selected, JavacExpressionType.class);

        return new JavacFieldAccess(treeMaker.Select(
                javacSelected.getExpression(),
                NameUtils.name(name)
        ));
    }

    @Override
    public @NotNull UniFieldAccess createFieldAccess(@NotNull UniExpression expression, @NotNull String name) {
        JavacExpression<?> javacExpression = check(expression, JavacExpression.class);

        return new JavacFieldAccess(treeMaker.Select(
                javacExpression.getTree(),
                NameUtils.name(name)
        ));
    }

    @Override
    public @NotNull UniFieldAccess createClassLiteral(@NotNull Class<?> type) {
        return createClassLiteral(Uniflow.getInstance().getTypeFactory().createClassType(type));
    }

    @Override
    public @NotNull UniFieldAccess createClassLiteral(@NotNull UniClassType type) {
        JavacClassType javacType = check(type, JavacClassType.class);

        return new JavacFieldAccess((JCTree.JCFieldAccess) treeMaker.ClassLiteral(javacType.getRawType()));
    }

    protected JCTree.JCCaseLabel createCaseLabel(@NotNull UniCaseLabel label) {
        if (label instanceof JavacCaseLabel)
            return ((JavacCaseLabel) label).getTree();
        else if (label instanceof JavacDefaultCaseLabel)
            return ((JavacDefaultCaseLabel) label).getTree();
        else if (label instanceof JavacExpression<?>)
            return Reflection.CASE_LABEL_TYPE.cast(((JavacExpression<?>) label).getTree());
        throw new IllegalArgumentException("Case label " + label + " is invalid");
    }
}
