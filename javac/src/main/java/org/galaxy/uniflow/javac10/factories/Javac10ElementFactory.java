package org.galaxy.uniflow.javac10.factories;

import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniJdk10ElementFactory;
import org.galaxy.uniflow.api.factories.UniTypeFactory;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.annotations.JavacAnnotation;
import org.galaxy.uniflow.javac.expression.JavacExpression;
import org.galaxy.uniflow.javac.statements.JavacVariable;
import org.galaxy.uniflow.javac.types.JavacExpressionType;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac9.factories.JavacJigsawElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class Javac10ElementFactory extends JavacJigsawElementFactory implements UniJdk10ElementFactory {

    @Override
    public @NotNull UniVariable createVarVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                                  @NotNull String name,
                                                  @NotNull Class<?> type,
                                                  @Nullable UniExpression init) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createVarVariable(annotations, name, typeFactory.createClassType(type), init);
    }

    @Override
    public @NotNull UniVariable createVarVariable(@NotNull List<@NotNull UniAnnotation> annotations,
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
                true
        ));
    }
}
