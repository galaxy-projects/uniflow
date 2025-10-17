package org.galaxy.uniflow.javac8.factories;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.statements.UniJdk8Case;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.Reflection;
import org.galaxy.uniflow.javac.factories.JavacElementFactory;
import org.galaxy.uniflow.javac.statements.JavacStatement;
import org.galaxy.uniflow.javac8.statements.Javac8Case;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectMethod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class Javac8ElementFactory extends JavacElementFactory {

    private static final ReflectMethod CREATE_CASE;

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniJdk8Case createCase(@NotNull UniCaseLabel label,
                                           @NotNull List<@NotNull UniStatement> statements) {
        JCTree.JCCaseLabel javacLabel = createCaseLabel(label);
        Stream<JavacStatement> javacStatements = checkList(statements, JavacStatement.class);

        return new Javac8Case((JCTree.JCCase) CREATE_CASE.run(treeMaker,
                javacLabel,
                javacStatements.map(JavacStatement::getTree).collect(com.sun.tools.javac.util.List.collector())
        ));
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.TREE_MAKER);
            CREATE_CASE = type.method("Case", Reflection.EXPRESSION_TYPE, Reflection.LIST_TYPE);
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
