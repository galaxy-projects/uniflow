package org.galaxy.uniflow.javac12.factories;

import com.sun.source.tree.CaseTree;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniSwitchExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.factories.UniJdk12ElementFactory;
import org.galaxy.uniflow.api.statements.UniJdk12Case;
import org.galaxy.uniflow.api.statements.UniJdk8Case;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.statements.UniYield;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.expression.JavacExpression;
import org.galaxy.uniflow.javac.statements.JavacStatement;
import org.galaxy.uniflow.javac10.factories.Javac10ElementFactory;
import org.galaxy.uniflow.javac12.Reflection;
import org.galaxy.uniflow.javac12.expression.JavacSwitchExpression;
import org.galaxy.uniflow.javac12.statements.Javac12Case;
import org.galaxy.uniflow.javac12.statements.JavacYield;
import org.galaxy.uniflow.javac15.pattern.JavacPattern;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectMethod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class Javac12ElementFactory extends Javac10ElementFactory implements UniJdk12ElementFactory {

    private static final ReflectMethod CREATE_CASE;

    @Override
    public @NotNull UniYield createYield(@NotNull UniExpression value) {
        JavacExpression<?> javacValue = check(value, JavacExpression.class);

        return new JavacYield(treeMaker.Yield(javacValue.getTree()));
    }

    @Override
    public @NotNull UniSwitchExpression createSwitchExpression(@NotNull UniExpression selector,
                                                               @NotNull List<@NotNull UniJdk12Case> cases) {
        JavacExpression<?> javacSelector = check(selector, JavacExpression.class);
        Stream<Javac12Case> javacCases = checkList(cases, Javac12Case.class);

        return new JavacSwitchExpression(treeMaker.SwitchExpression(
                javacSelector.getTree(),
                mapToList(javacCases, Javac12Case::getTree)
        ));
    }

    @Override
    public @NotNull UniJdk8Case createCase(@NotNull UniCaseLabel label,
                                           @NotNull List<@NotNull UniStatement> statements) {
        throw new UnsupportedOperationException(
                "Use UniJdk12ElementFactory#createCase(List<UniCaseLabel>, List<UniStatement>) instead");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniJdk12Case createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                            @NotNull List<@NotNull UniStatement> statements) {
        com.sun.tools.javac.util.List<JCTree.JCCaseLabel> caseLabels = createCaseLabels(labels);
        Stream<JavacStatement> javacStatements = checkList(statements, JavacStatement.class);

        //noinspection Since15
        return new Javac12Case.Javac12StatementCase((JCTree.JCCase) CREATE_CASE.run(treeMaker,
                CaseTree.CaseKind.STATEMENT,
                caseLabels,
                mapToList(javacStatements, st -> (JCTree.JCStatement) st.getTree()),
                null
        ));
    }

    @Override
    public @NotNull UniJdk12Case createCase(@NotNull List<@NotNull UniCaseLabel> labels, @NotNull UniElement body) {
        com.sun.tools.javac.util.List<JCTree.JCCaseLabel> caseLabels = createCaseLabels(labels);
        JavacElement<?> javacBody = check(body, JavacElement.class);

        //noinspection Since15
        return new Javac12Case.Javac12RuleCase((JCTree.JCCase) CREATE_CASE.run(treeMaker,
                CaseTree.CaseKind.RULE,
                caseLabels,
                com.sun.tools.javac.util.List.nil(),
                javacBody.getTree()
        ));
    }

    protected com.sun.tools.javac.util.List<JCTree.JCCaseLabel> createCaseLabels(List<UniCaseLabel> labels) {
        return labels.stream().map(this::createCaseLabel).collect(com.sun.tools.javac.util.List.collector());
    }

    @Override
    protected JCTree.JCCaseLabel createCaseLabel(@NotNull UniCaseLabel label) {
        if (label instanceof JavacPattern<?>)
            return Reflection.CASE_LABEL_TYPE.cast(((JavacPattern<?>) label).getTree());
        return super.createCaseLabel(label);
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.TREE_MAKER);
            CREATE_CASE = type.method("Case", Reflection.CASE_KIND, Reflection.LIST_TYPE, Reflection.LIST_TYPE,
                    Reflection.TREE_TYPE);
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
