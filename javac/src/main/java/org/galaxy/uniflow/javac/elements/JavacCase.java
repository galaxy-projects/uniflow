package org.galaxy.uniflow.javac.elements;

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
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
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
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public @NotNull UniList<UniExpression> getExpressions() {
//        List<JCTree.JCExpression> affected = tree.labels.stream()
//                .filter(p -> p instanceof JCTree.JCExpression)
//                .map(p -> (JCTree.JCExpression) p)
//                .collect(List.collector());
//        java.util.List<JCTree.JCCaseLabel> notAffected = tree.labels.stream()
//                .filter(p -> !(p instanceof JCTree.JCExpression))
//                .collect(Collectors.toList());
//
//        return new JavacList<>(
//                affected,
//                newList -> {
//                    ListBuffer<JCTree.JCCaseLabel> result = new ListBuffer<>();
//
//                    result.addAll(notAffected);
//                    result.addAll(newList);
//                    tree.labels = result.toList();
//                },
//                UniUtils::uni,
//                JavacUtils::javac
//        );
        return new JavacList<>(
                tree.labels,
                newList -> tree.labels = newList,
                UniUtils::uni,
                JavacUtils::javac
        ).partial(
                UniExpression.class::isInstance,
                label -> (UniExpression) label,
                Function.identity(),
                JavacUtils::javac
        );
    }

    @Override
    public @NotNull UniList<UniStatement> getStatements() {
        return new JavacList<>(
                tree.getStatements(),
                newList -> tree.stats = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public void setBody(@NotNull UniElement body) {
        tree.body = JavacUtils.javac(body);
    }

    @Override
    public @Nullable UniElement getBody() {
        return UniUtils.uni(tree.body);
    }

    @Override
    public @NotNull CaseKind getCaseKind() {
        return EnumUtils.convert(CaseKind.class, tree.caseKind);
    }
}
