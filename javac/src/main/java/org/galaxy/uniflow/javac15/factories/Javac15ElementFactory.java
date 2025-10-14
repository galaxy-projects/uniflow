package org.galaxy.uniflow.javac15.factories;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.ListBuffer;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.factories.UniJdk15ElementFactory;
import org.galaxy.uniflow.api.pattern.UniBindingPattern;
import org.galaxy.uniflow.api.pattern.UniGuardedPattern;
import org.galaxy.uniflow.api.pattern.UniParenthesizedPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.javac.JavacClassInitializer;
import org.galaxy.uniflow.javac.JavacMethod;
import org.galaxy.uniflow.javac.JavacModifiers;
import org.galaxy.uniflow.javac.expression.JavacExpression;
import org.galaxy.uniflow.javac.statements.JavacClass;
import org.galaxy.uniflow.javac.statements.JavacVariable;
import org.galaxy.uniflow.javac.types.JavacExpressionType;
import org.galaxy.uniflow.javac.types.JavacTypeParameter;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac12.factories.Javac12ElementFactory;
import org.galaxy.uniflow.javac15.Reflection;
import org.galaxy.uniflow.javac15.pattern.JavacBindingPattern;
import org.galaxy.uniflow.javac15.pattern.JavacGuardedPattern;
import org.galaxy.uniflow.javac15.pattern.JavacParenthesizedPattern;
import org.galaxy.uniflow.javac15.pattern.JavacPattern;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class Javac15ElementFactory extends Javac12ElementFactory implements UniJdk15ElementFactory {

    private static final ReflectMethod CREATE_BINDING_PATTERN;
    private static final ReflectMethod CREATE_GUARD_PATTERN;
    private static final ReflectMethod CREATE_PARENTHESIZED_PATTERN;

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniClass createRecord(@NotNull UniModifiers modifiers, @NotNull String name,
                                          @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                          @NotNull List<@NotNull UniType> implementing,
                                          @NotNull List<@NotNull UniVariable> fields) {
        modifiers.addModifier(UniModifier.RECORD);

        JavacModifiers javacModifiers = check(modifiers, JavacModifiers.class);
        Stream<JavacTypeParameter> javacTypeParameters = checkList(typeParameters, JavacTypeParameter.class);
        Stream<JavacExpressionType> javacImplementing =
                checkList(implementing, JavacExpressionType.class);
        Stream<JavacVariable> javacFields = checkList(fields, JavacVariable.class);

        return new JavacClass(treeMaker.ClassDef(
                javacModifiers.getTree(),
                NameUtils.name(name),
                mapToList(javacTypeParameters, JavacTypeParameter::getTree),
                null,
                mapToList(javacImplementing, type -> (JCTree.JCExpression) type.getExpression()),
                com.sun.tools.javac.util.List.nil(),
                mapToList(javacFields, JavacVariable::getTree)
        ));
    }

    @Override
    @SuppressWarnings({ "rawtypes", "DuplicatedCode" })
    public @NotNull UniClass createClass(@NotNull UniModifiers modifiers,
                                         @NotNull String name,
                                         @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                         @Nullable UniType extending,
                                         @NotNull List<@NotNull UniType> implementing,
                                         @NotNull List<@NotNull UniExpression> permitting,
                                         @NotNull List<@NotNull UniVariable> fields,
                                         @NotNull List<@NotNull UniMethod> methods,
                                         @NotNull List<@NotNull UniClassInitializer> initializers) {
        JavacModifiers javacModifiers = check(modifiers, JavacModifiers.class);
        Stream<JavacTypeParameter> javacTypeParameters = checkList(typeParameters, JavacTypeParameter.class);
        JavacExpressionType javacExtending = check(extending, JavacExpressionType.class);
        Stream<JavacExpressionType> javacImplementing =
                checkList(implementing, JavacExpressionType.class);
        Stream<JavacExpression> javacPermitting = checkList(permitting, JavacExpression.class);
        Stream<JavacVariable> javacFields = checkList(fields, JavacVariable.class);
        Stream<JavacMethod> javacMethods = checkList(methods, JavacMethod.class);
        Stream<JavacClassInitializer> javacInitializers = checkList(initializers, JavacClassInitializer.class);
        ListBuffer<JCTree> buffer = new ListBuffer<>();

        javacFields.map(JavacVariable::getTree).forEach(buffer::add);
        javacMethods.map(JavacMethod::getTree).forEach(buffer::add);
        javacInitializers.map(JavacClassInitializer::getTree).forEach(buffer::add);

        return new JavacClass(treeMaker.ClassDef(
                javacModifiers.getTree(),
                NameUtils.name(name),
                mapToList(javacTypeParameters, JavacTypeParameter::getTree),
                javacExtending != null ? (JCTree.JCExpression) javacExtending.getExpression() : null,
                mapToList(javacImplementing, type -> (JCTree.JCExpression) type.getExpression()),
                mapToList(javacPermitting, permit -> (JCTree.JCExpression) permit.getTree()),
                buffer.toList()
        ));
    }

    @Override
    public @NotNull UniBindingPattern createBindingPattern(@NotNull UniVariable variable) {
        JavacVariable javacVariable = check(variable, JavacVariable.class);

        return new JavacBindingPattern(
                (JCTree.JCPattern) CREATE_BINDING_PATTERN.run(treeMaker, javacVariable.getTree()));
    }


    @Override
    public @NotNull UniGuardedPattern createGuardedPattern(@NotNull UniPattern pattern,
                                                           @NotNull UniExpression expression) {
        JavacPattern<?> javacPattern = check(pattern, JavacPattern.class);
        JavacExpression<?> javacExpression = check(expression, JavacExpression.class);

        return new JavacGuardedPattern(
                (JCTree.JCPattern) CREATE_GUARD_PATTERN.run(javacPattern.getTree(), javacExpression.getTree()));
    }

    @Override
    public @NotNull UniParenthesizedPattern createParenthesizedPattern(@NotNull UniPattern pattern) {
        JavacPattern<?> javacPattern = check(pattern, JavacPattern.class);

        return new JavacParenthesizedPattern(
                (JCTree.JCPattern) CREATE_PARENTHESIZED_PATTERN.run(treeMaker, javacPattern.getTree()));
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.TREE_MAKER);
            CREATE_BINDING_PATTERN = type.method("BindingPattern", Reflection.VARIABLE_TYPE);
            CREATE_GUARD_PATTERN = type.method("GuardPattern", Reflection.PATTERN_TYPE, Reflection.EXPRESSION_TYPE);
            CREATE_PARENTHESIZED_PATTERN = type.method("ParenthesizedPattern", Reflection.PATTERN_TYPE);
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
