package org.galaxy.uniflow.javac21.factories;

import com.sun.source.tree.CaseTree;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.elements.labels.UniConstantCaseLabel;
import org.galaxy.uniflow.api.elements.labels.UniPatternCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.factories.UniJdk21ElementFactory;
import org.galaxy.uniflow.api.pattern.UniAnyPattern;
import org.galaxy.uniflow.api.pattern.UniDeconstructionPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.statements.UniJdk12Case;
import org.galaxy.uniflow.api.statements.UniJdk21Case;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.elements.JavacCaseLabel;
import org.galaxy.uniflow.javac.elements.JavacDefaultCaseLabel;
import org.galaxy.uniflow.javac.expression.JavacExpression;
import org.galaxy.uniflow.javac.statements.JavacStatement;
import org.galaxy.uniflow.javac15.factories.Javac15ElementFactory;
import org.galaxy.uniflow.javac15.pattern.JavacPattern;
import org.galaxy.uniflow.javac21.Reflection;
import org.galaxy.uniflow.javac21.elements.Javac21ConstantCaseLabel;
import org.galaxy.uniflow.javac21.elements.Javac21PatternCaseLabel;
import org.galaxy.uniflow.javac21.pattern.Javac21AnyPattern;
import org.galaxy.uniflow.javac21.pattern.Javac21DeconstructionPattern;
import org.galaxy.uniflow.javac21.statements.Javac21Case;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class Javac21ElementFactory extends Javac15ElementFactory implements UniJdk21ElementFactory {

    private static final ReflectMethod CREATE_CASE;

    private static final ReflectMethod CREATE_CONSTANT_CASE_LABEL;
    private static final ReflectMethod CREATE_PATTERN_CASE_LABEL;

    private static final ReflectMethod CREATE_ANY_PATTERN;
    private static final ReflectMethod CREATE_RECORD_PATTERN;

    @Override
    public @NotNull UniJdk12Case createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                            @NotNull List<@NotNull UniStatement> statements) {
        return createCase(labels, null, statements);
    }

    @Override
    public @NotNull UniJdk12Case createCase(@NotNull List<@NotNull UniCaseLabel> labels, @NotNull UniElement body) {
        return createCase(labels, null, body);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "Since15" })
    public @NotNull UniJdk21Case createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                            @Nullable UniExpression guard,
                                            @NotNull List<@NotNull UniStatement> statements) {
        com.sun.tools.javac.util.List<JCTree.JCCaseLabel> javacLabels = createCaseLabels(labels);
        JavacExpression<?> javacGuard = check(guard, JavacExpression.class);
        Stream<JavacStatement> javacStatements = checkList(statements, JavacStatement.class);

        return new Javac21Case.Javac21StatementCase((JCTree.JCCase) CREATE_CASE.run(treeMaker,
                CaseTree.CaseKind.STATEMENT,
                javacLabels,
                javacGuard != null ? javacGuard.getTree() : null,
                mapToList(javacStatements, jc -> (JCTree.JCStatement) jc.getTree()),
                null
        ));
    }

    @Override
    @SuppressWarnings("Since15")
    public @NotNull UniJdk21Case createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                            @Nullable UniExpression guard,
                                            @NotNull UniElement body) {
        com.sun.tools.javac.util.List<JCTree.JCCaseLabel> javacLabels = createCaseLabels(labels);
        JavacExpression<?> javacGuard = check(guard, JavacExpression.class);
        JavacElement<?> javacBody = check(body, JavacElement.class);

        return new Javac21Case.Javac21RuleCase((JCTree.JCCase) CREATE_CASE.run(treeMaker,
                CaseTree.CaseKind.RULE,
                javacLabels,
                javacGuard != null ? javacGuard.getTree() : null,
                com.sun.tools.javac.util.List.nil(),
                javacBody.getTree()
        ));
    }

    @Override
    public @NotNull UniConstantCaseLabel createConstantCaseLabel(@NotNull UniExpression expression) {
        JavacExpression<?> javacExpression = check(expression, JavacExpression.class);

        return new Javac21ConstantCaseLabel(
                (JCTree.JCCaseLabel) CREATE_CONSTANT_CASE_LABEL.run(treeMaker, javacExpression.getTree()));
    }

    @Override
    public @NotNull UniPatternCaseLabel createPatternCaseLabel(@NotNull UniPattern pattern) {
        JavacPattern<?> javacPattern = check(pattern, JavacPattern.class);

        return new Javac21PatternCaseLabel(
                (JCTree.JCCaseLabel) CREATE_PATTERN_CASE_LABEL.run(treeMaker, javacPattern.getTree()));
    }

    @Override
    public @NotNull UniAnyPattern createAnyPattern() {
        return new Javac21AnyPattern((JCTree.JCPattern) CREATE_ANY_PATTERN.run(treeMaker));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniDeconstructionPattern createDeconstructionPattern(@NotNull UniExpression deconstructor,
                                                                         @NotNull List<@NotNull UniPattern> nestedPatterns) {
        JavacExpression<?> javacDeconstructor = check(deconstructor, JavacExpression.class);
        Stream<JavacPattern> javacNestedPatterns = checkList(nestedPatterns, JavacPattern.class);


        return new Javac21DeconstructionPattern((JCTree.JCPattern) CREATE_RECORD_PATTERN.run(treeMaker,
                javacDeconstructor.getTree(),
                mapToList(javacNestedPatterns, jp -> (JCTree.JCPattern) jp.getTree())
        ));
    }

    @Override
    protected JCTree.JCCaseLabel createCaseLabel(@NotNull UniCaseLabel label) {
        if (label instanceof JavacExpression<?>)
            throw new IllegalArgumentException("Expression are no more supported");
        else if (label instanceof JavacCaseLabel)
            return ((JavacCaseLabel) label).getTree();
        else if (label instanceof JavacDefaultCaseLabel)
            return ((JavacDefaultCaseLabel) label).getTree();
        else if (label instanceof JavacPattern<?>)
            return Reflection.CASE_LABEL_TYPE.cast(((JavacPattern<?>) label).getTree());
        else if (label instanceof Javac21ConstantCaseLabel)
            return ((Javac21ConstantCaseLabel) label).getTree();
        else if (label instanceof Javac21PatternCaseLabel)
            return ((Javac21PatternCaseLabel) label).getTree();
        throw new IllegalArgumentException("Case label " + label + " is invalid");
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.TREE_MAKER);
            CREATE_CASE = type.method("Case", Reflection.CASE_KIND_TYPE, Reflection.LIST_TYPE,
                    Reflection.EXPRESSION_TYPE, Reflection.LIST_TYPE, Reflection.TREE_TYPE);

            CREATE_CONSTANT_CASE_LABEL = type.method("ConstantCaseLabel", Reflection.EXPRESSION_TYPE);
            CREATE_PATTERN_CASE_LABEL = type.method("PatternCaseLabel", Reflection.PATTERN_TYPE);

            CREATE_ANY_PATTERN = type.method("AnyPattern");
            CREATE_RECORD_PATTERN = type.method("RecordPattern", Reflection.EXPRESSION_TYPE, Reflection.LIST_TYPE);
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
