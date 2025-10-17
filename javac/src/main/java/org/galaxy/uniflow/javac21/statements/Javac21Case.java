package org.galaxy.uniflow.javac21.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.statements.UniJdk21Case;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac12.statements.Javac12Case;
import org.galaxy.uniflow.javac21.Reflection;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public abstract class Javac21Case extends Javac12Case implements UniJdk21Case {

    private static final ReflectField GUARD;

    public Javac21Case(JCTree.@NotNull JCCase tree) {
        super(tree);
    }

    @Override
    public void setGuard(@NotNull UniExpression guard) {
        GUARD.set(tree, JavacUnwrapper.unwrap(guard));
    }

    @Override
    public @NotNull UniExpression getGuard() {
        return UniflowWrapper.wrap((JCTree.JCExpression) GUARD.get(tree));
    }

    public static class Javac21StatementCase extends Javac12Case.Javac12StatementCase
            implements UniJdk21Case.UniJdk21StatementCase {

        public Javac21StatementCase(JCTree.@NotNull JCCase tree) {
            super(tree);
        }

        @Override
        public void setGuard(@NotNull UniExpression guard) {
            GUARD.set(tree, JavacUnwrapper.unwrap(guard));
        }

        @Override
        public @NotNull UniExpression getGuard() {
            return UniflowWrapper.wrap((JCTree.JCExpression) GUARD.get(tree));
        }
    }

    public static class Javac21RuleCase extends Javac12Case.Javac12RuleCase
            implements UniJdk21RuleCase {

        public Javac21RuleCase(JCTree.@NotNull JCCase tree) {
            super(tree);
        }

        @Override
        public void setGuard(@NotNull UniExpression guard) {
            GUARD.set(tree, JavacUnwrapper.unwrap(guard));
        }

        @Override
        public @NotNull UniExpression getGuard() {
            return UniflowWrapper.wrap((JCTree.JCExpression) GUARD.get(tree));
        }
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.CASE_TYPE);
            GUARD = type.field("guard");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
