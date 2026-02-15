package org.galaxy.uniflow.javac;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.UniMethodBuilder;
import org.galaxy.uniflow.javac.expression.JavacExpression;
import org.galaxy.uniflow.javac.lists.JavacMethodList;
import org.galaxy.uniflow.javac.statements.JavacBlock;
import org.galaxy.uniflow.javac.statements.JavacClass;
import org.galaxy.uniflow.javac.statements.JavacParameter;
import org.galaxy.uniflow.javac.statements.JavacVariable;
import org.galaxy.uniflow.javac.types.JavacExpressionType;
import org.galaxy.uniflow.javac.types.JavacTypeParameter;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.galaxy.uniflow.javac.util.JavacUtils.*;

public class JavacMethodBuilder extends UniMethodBuilder {

    public JavacMethodBuilder(UniClass owner, String name, boolean constructor) {
        super(owner, name, constructor);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniMethod build() {
        checkArgs();

        JavacClass javacClass = check(owner, JavacClass.class);
        JavacModifiers javacModifiers = check(modifiers, JavacModifiers.class);
        JavacExpressionType<?, ?> javacReturnType = constructor ? null : check(returnType, JavacExpressionType.class);
        Stream<JavacTypeParameter> javacTypeParameters = checkList(typeParameters, JavacTypeParameter.class);
        JavacVariable javacReceiveParam = check(receiveParameter, JavacVariable.class);
        Stream<JavacParameter> javacParameters = checkList(parameters, JavacParameter.class);
        Stream<JavacExpression> javacThrown = checkList(thrown, JavacExpression.class);
        JavacBlock javacBody = body != null ?
                check(body.get(), JavacBlock.class) : null;
        JavacExpression<?> javacDefaultValue = defaultValue != null ?
                check(defaultValue.get(), JavacExpression.class) : null;
        TreeMaker treeMaker = JavacUniflow.getInstance().treeMaker;

        JavacMethod method = new JavacMethod(treeMaker.MethodDef(
                javacModifiers.getTree(),
                NameUtils.name(name),
                nullCheck(javacReturnType, JavacExpressionType::getExpression),
                mapToList(javacTypeParameters, JavacTypeParameter::getTree),
                nullCheck(javacReceiveParam, JavacVariable::getTree),
                mapToList(javacParameters, JavacVariable::getTree),
                mapToList(javacThrown, threw -> (JCTree.JCExpression) threw.getTree()),
                nullCheck(javacBody, JavacBlock::getTree),
                nullCheck(javacDefaultValue, JavacExpression::getTree)
        ));

        JavacMethodList methods = constructor ? javacClass.getConstructors() : javacClass.getMethods();

        methods.addLast(method);

        return method;
    }

    private <T, R> R nullCheck(T value, Function<T, R> func) {
        return value != null ? func.apply(value) : null;
    }
}
