package org.galaxy.uniflow.javac12.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.statements.UniJdk12Case;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac15.Reflection;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Javac12Case extends JavacElement<JCTree.JCCase> implements UniJdk12Case {

    private static final ReflectField LABELS;
    private static final ReflectField BODY;
    private static final ReflectField STATEMENTS;

    public Javac12Case(JCTree.@NotNull JCCase tree) {
        super(tree);
    }

    @Override
    public @NotNull UniList<UniCaseLabel> getLabels() {
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
            LABELS = type.field("labels");
            BODY = type.field("body");
            STATEMENTS = type.field("stats");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }

    public static class Javac12StatementCase extends Javac12Case implements UniJdk12StatementCase {

        public Javac12StatementCase(JCTree.@NotNull JCCase tree) {
            super(tree);
        }

        @Override
        public @NotNull UniList<UniStatement> getStatements() {
            return new JavacList<UniStatement, JCTree.JCStatement>(
                    STATEMENTS.createGetter(tree),
                    STATEMENTS.createSetter(tree),
                    UniflowWrapper::wrap,
                    JavacUnwrapper::unwrap
            );
        }

        @Override
        public @NotNull CaseKind getCaseKind() {
            return CaseKind.STATEMENT;
        }
    }

    public static class Javac12RuleCase extends Javac12Case implements UniJdk12RuleCase {

        public Javac12RuleCase(JCTree.@NotNull JCCase tree) {
            super(tree);
        }

        @Override
        public void setBody(@NotNull UniElement body) {
            BODY.set(tree, JavacUnwrapper.unwrap(body));
        }

        @Override
        public @Nullable UniElement getBody() {
            return UniflowWrapper.wrap((JCTree) BODY.get(tree));
        }

        @Override
        public @NotNull CaseKind getCaseKind() {
            return CaseKind.RULE;
        }
    }
}
