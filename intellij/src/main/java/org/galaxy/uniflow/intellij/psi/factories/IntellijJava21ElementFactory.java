package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.elements.labels.UniConstantCaseLabel;
import org.galaxy.uniflow.api.elements.labels.UniPatternCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniJdk21ElementFactory;
import org.galaxy.uniflow.api.pattern.UniAnyPattern;
import org.galaxy.uniflow.api.pattern.UniDeconstructionPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.statements.UniJdk21Case;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.elements.labels.IJConstantCaseLabel;
import org.galaxy.uniflow.intellij.psi.elements.labels.IJPatternCaseLabel;
import org.galaxy.uniflow.intellij.psi.expression.IJExpression;
import org.galaxy.uniflow.intellij.psi.pattern.IJAnyPattern;
import org.galaxy.uniflow.intellij.psi.pattern.IJPattern;
import org.galaxy.uniflow.intellij.psi.pattern.IJRecordPattern;
import org.galaxy.uniflow.intellij.psi.statements.IJCase;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.galaxy.uniflow.intellij.psi.util.IJUtils.check;
import static org.galaxy.uniflow.intellij.psi.util.IJUtils.checkList;

public class IntellijJava21ElementFactory extends IntellijJava15ElementFactory implements UniJdk21ElementFactory {

    public IntellijJava21ElementFactory(PsiElementFactory factory, PsiJavaParserFacade parser, PsiFileFactory files) {
        super(factory, parser, files);
    }

    @Override
    public @NotNull UniJdk21Case createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                            @Nullable UniExpression guard,
                                            @NotNull List<@NotNull UniStatement> statements) {
        PsiSwitchLabelStatement newCase = (PsiSwitchLabelStatement) factory.createStatementFromText(
                "case a:", null);
        PsiCaseLabelElementList caseLabels = newCase.getCaseLabelElementList();
        List<PsiStatement> psiStatements = new ArrayList<>(statements.size());

        assert caseLabels != null;

        caseLabels.getElements()[0].delete();
        labels.stream().map(IntellijUnwrapper::unwrap).forEach(caseLabels::add);

        statements.stream().map(IntellijUnwrapper::unwrap).forEach(statement -> {
            newCase.add(statement);
            psiStatements.add(statement);
        });

        return new IJCase.IJStatementCase(newCase, psiStatements);
    }

    @Override
    public @NotNull UniJdk21Case createCase(@NotNull List<@NotNull UniCaseLabel> labels,
                                            @Nullable UniExpression guard,
                                            @NotNull UniElement body) {
        PsiSwitchLabeledRuleStatement newCase = (PsiSwitchLabeledRuleStatement) factory.createStatementFromText(
                "case a -> b", null);

        assert newCase.getGuardExpression() != null;
        assert newCase.getBody() != null;

        newCase.getGuardExpression().replace(IntellijUnwrapper.unwrap(guard));
        newCase.getBody().replace(IntellijUnwrapper.unwrap(body));

        return new IJCase.IJRuleCase(newCase);
    }

    @Override
    public @NotNull UniConstantCaseLabel createConstantCaseLabel(@NotNull UniExpression expression) {
        IJExpression<?> ijExpression = check(expression, IJExpression.class);
        PsiSwitchLabelStatement newCase = (PsiSwitchLabelStatement) factory.createStatementFromText("case a:", null);
        PsiCaseLabelElementList labels = newCase.getCaseLabelElementList();

        assert labels != null;

        labels.getElements()[0].delete();
        labels.add(ijExpression.getElement());

        return new IJConstantCaseLabel(labels.getElements()[0], ijExpression.getElement());
    }

    @Override
    public @NotNull UniPatternCaseLabel createPatternCaseLabel(@NotNull UniPattern pattern) {
        IJPattern<?> ijPattern = check(pattern, IJPattern.class);
        PsiSwitchLabelStatement newCase = (PsiSwitchLabelStatement) factory.createStatementFromText("case String s:",
                null);
        PsiCaseLabelElementList labels = newCase.getCaseLabelElementList();

        assert labels != null;

        labels.getElements()[0].delete();
        labels.add(ijPattern.getElement());

        return new IJPatternCaseLabel(labels.getElements()[0], ijPattern.getElement());
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public @NotNull UniAnyPattern createAnyPattern() {
        PsiSwitchLabelStatement newCase = (PsiSwitchLabelStatement) factory.createStatementFromText("case _:", null);
        PsiCaseLabelElementList labels = newCase.getCaseLabelElementList();

        assert labels != null;
        PsiCaseLabelElement element = labels.getElements()[0];

        assert element instanceof PsiUnnamedPattern;

        return new IJAnyPattern((PsiUnnamedPattern) element);
    }

    @Override
    @SuppressWarnings({ "UnstableApiUsage", "rawtypes" })
    public @NotNull UniDeconstructionPattern createDeconstructionPattern(@NotNull UniExpression deconstructor,
                                                                         @NotNull List<@NotNull UniPattern> nestedPatterns) {
        IJExpression<?> ijDeconstructor = check(deconstructor, IJExpression.class);
        Stream<IJPattern> ijNestedPatterns = checkList(nestedPatterns, IJPattern.class);

        PsiInstanceOfExpression instanceOf = (PsiInstanceOfExpression) factory.createStatementFromText(
                "a instanceof Record(var b)", null);

        assert instanceOf.getPattern() != null;

        PsiDeconstructionPattern pattern = (PsiDeconstructionPattern) instanceOf.getPattern().copy();
        PsiDeconstructionList deconstructionList = pattern.getDeconstructionList();
        PsiTypeElement typeElement = factory.createTypeElementFromText(ijDeconstructor.getElement().getText(), null);

        pattern.getTypeElement().replace(typeElement);

        deconstructionList.getDeconstructionComponents()[0].delete();
        ijNestedPatterns.map(IJPattern::getElement).forEach(deconstructionList::add);

        return new IJRecordPattern(pattern);
    }
}
