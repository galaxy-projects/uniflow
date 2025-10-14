package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.statements.UniCase;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.Reflection;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacCase extends JavacStatement<JCTree.JCCase> implements UniCase {

    private static final ReflectField STATEMENTS;
    private static final ReflectField LABELS;

    public JavacCase(JCTree.JCCase tree) {
        super(tree);
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

    @Override
    public @NotNull UniList<@NotNull UniCaseLabel> getLabels() {
        return new JavacList<UniCaseLabel, JCTree.JCCaseLabel>(
                LABELS.createGetter(tree),
                LABELS.createSetter(tree),
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.CASE_TYPE);
            STATEMENTS = type.field("stats");
            LABELS = type.field("labels");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
