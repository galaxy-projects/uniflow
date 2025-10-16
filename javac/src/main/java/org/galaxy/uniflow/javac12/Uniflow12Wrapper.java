package org.galaxy.uniflow.javac12;

import com.sun.source.tree.CaseTree;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.statements.UniJdk15Case;
import org.galaxy.uniflow.javac12.expression.JavacSwitchExpression;
import org.galaxy.uniflow.javac12.statements.Javac12Case;
import org.galaxy.uniflow.javac12.statements.JavacYield;
import org.galaxy.uniflow.javac9.Uniflow9Wrapper;
import org.jetbrains.annotations.Nullable;

public class Uniflow12Wrapper extends Uniflow9Wrapper {

    public static final Uniflow12Wrapper INSTANCE = new Uniflow12Wrapper();

    @Override
    public @Nullable UniElement wrap(JCTree element) {
        if (element instanceof JCTree.JCCase)
            return wrap((JCTree.JCCase) element);
        else if (Reflection.SWITCH_EXPRESSION_TYPE.isInstance(element))
            return new JavacSwitchExpression((JCTree.JCExpression) element);
        else if (Reflection.YIELD_TYPE.isInstance(element))
            return new JavacYield((JCTree.JCStatement) element);
        return super.wrap(element);
    }

    @SuppressWarnings("Since15")
    public static UniJdk15Case wrap(JCTree.JCCase jcCase) {
        if (jcCase.getCaseKind() == CaseTree.CaseKind.RULE)
            return new Javac12Case.Javac12RuleCase(jcCase);
        else if (jcCase.getCaseKind() == CaseTree.CaseKind.STATEMENT)
            return new Javac12Case.Javac12StatementCase(jcCase);
        return null;
    }
}
