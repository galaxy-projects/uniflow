package org.galaxy.uniflow.javac8.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.statements.UniJdk8Case;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.Reflection;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.statements.JavacStatement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class Javac8Case extends JavacStatement<JCTree.JCCase> implements UniJdk8Case {

    private static final ReflectField STATEMENTS;
    private static final ReflectField LABEL;

    public Javac8Case(JCTree.JCCase tree) {
        super(tree);
    }

    @Override
    public void setLabel(@NotNull UniCaseLabel label) {
        LABEL.set(tree, JavacUnwrapper.unwrap(label));
    }

    @Override
    public @NotNull UniCaseLabel getLabel() {
        return UniflowWrapper.wrap((JCTree.JCExpression) LABEL.get(tree));
    }

    @Override
    public @NotNull UniList<@NotNull UniStatement> getStatements() {
        return new JavacList<UniStatement, JCTree.JCStatement>(
                STATEMENTS.createGetter(tree),
                STATEMENTS.createSetter(tree),
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.CASE_TYPE);
            STATEMENTS = type.field("stats");
            LABEL = type.field("pat");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
