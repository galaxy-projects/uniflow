package org.galaxy.uniflow.javac15.factories;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.ListBuffer;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniJdk15ElementFactory;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class Javac15ElementFactory extends Javac12ElementFactory implements UniJdk15ElementFactory {

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
        JavacExpressionType<JCTree.JCExpression, ?> javacExtending = check(extending, JavacExpressionType.class);
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
                javacExtending != null ? javacExtending.getExpression() : null,
                mapToList(javacImplementing, type -> (JCTree.JCExpression) type.getExpression()),
                mapToList(javacPermitting, permit -> (JCTree.JCExpression) permit.getTree()),
                buffer.toList()
        ));
    }
}
