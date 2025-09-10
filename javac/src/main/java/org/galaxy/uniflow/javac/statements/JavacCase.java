package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class JavacCase extends JavacElement<JCTree.JCCase> implements UniCase {

    public JavacCase(JCTree.@NotNull JCCase tree) {
        super(tree);
    }

    @Override
    public @NotNull UniList<UniCaseLabel> getLabels() {
        return new JavacList<>(
                tree.labels,
                newList -> tree.labels = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<UniExpression> getExpressions() {
        return new JavacList<>(
                tree.labels,
                newList -> tree.labels = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        ).partial(
                UniExpression.class::isInstance,
                label -> (UniExpression) label,
                Function.identity(),
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<UniStatement> getStatements() {
        return new JavacList<>(
                tree.getStatements(),
                newList -> tree.stats = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public void setBody(@NotNull UniElement body) {
        tree.body = JavacUnwrapper.unwrap(body);
    }

    @Override
    public @Nullable UniElement getBody() {
        return UniflowWrapper.wrap(tree.body);
    }

    @Override
    public @NotNull CaseKind getCaseKind() {
        return EnumUtils.convert(CaseKind.class, tree.caseKind);
    }
}
