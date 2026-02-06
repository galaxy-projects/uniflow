package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniSwitchExpression;
import org.galaxy.uniflow.api.factories.UniJdk12ElementFactory;
import org.galaxy.uniflow.api.statements.*;
import org.galaxy.uniflow.intellij.psi.IJElement;
import org.galaxy.uniflow.intellij.psi.elements.labels.IJCaseLabelBase;
import org.galaxy.uniflow.intellij.psi.expression.IJExpression;
import org.galaxy.uniflow.intellij.psi.expression.IJSwitchExpression;
import org.galaxy.uniflow.intellij.psi.statements.IJCase;
import org.galaxy.uniflow.intellij.psi.statements.IJStatement;
import org.galaxy.uniflow.intellij.psi.statements.IJSwitchStatement;
import org.galaxy.uniflow.intellij.psi.statements.IJYield;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

import static org.galaxy.uniflow.intellij.psi.util.IJUtils.check;
import static org.galaxy.uniflow.intellij.psi.util.IJUtils.checkList;

public class IntellijJava12ElementFactory extends IntellijJava10ElementFactory implements UniJdk12ElementFactory {

    public IntellijJava12ElementFactory(PsiElementFactory factory, PsiJavaParserFacade parser, PsiFileFactory files) {
        super(factory, parser, files);
    }

    @Override
    public @NotNull UniYield createYield(@NotNull UniExpression value) {
        IJExpression<?> ijValue = check(value, IJExpression.class);

        PsiYieldStatement yield = (PsiYieldStatement) factory.createStatementFromText(
                "yield %s;".formatted(ijValue.getElement().getText()), null);

        return new IJYield(yield);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniSwitch createSwitchStatement(@NotNull UniExpression selector,
                                                    @NotNull List<@NotNull UniJdk12Case> cases) {
        IJExpression<?> ijSelector = check(selector, IJExpression.class);
        Stream<IJCase> ijCases = checkList(cases, IJCase.class);

        PsiSwitchStatement newSwitch = (PsiSwitchStatement) factory.createStatementFromText("switch (a) {}", null);
        PsiCodeBlock body = newSwitch.getBody();

        assert body != null;

        ijCases.map(IJCase::getElement).forEach(body::add);

        return new IJSwitchStatement(newSwitch);
    }

    @Override
    public @NotNull UniSwitchExpression createSwitchExpression(@NotNull UniExpression selector,
                                                               @NotNull List<@NotNull UniJdk12Case> cases) {
        IJExpression<?> ijSelector = check(selector, IJExpression.class);
        Stream<IJCase> ijCases = checkList(cases, IJCase.class);

        PsiSwitchExpression newSwitch = (PsiSwitchExpression) factory.createExpressionFromText("switch (a) {}", null);
        PsiCodeBlock body = newSwitch.getBody();

        assert body != null;

        ijCases.map(IJCase::getElement).forEach(body::add);

        return new IJSwitchExpression(newSwitch);
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
        Stream<IJCaseLabelBase> ijLabels = checkList(labels, IJCaseLabelBase.class);
        List<PsiStatement> psiStatements = checkList(statements, IJStatement.class)
                .map(statement -> (PsiStatement) statement.getElement())
                .toList();

        PsiSwitchLabelStatement newCase = (PsiSwitchLabelStatement) factory.createStatementFromText("case \"test\": ",
                null);
        PsiCaseLabelElementList labelList = newCase.getCaseLabelElementList();

        assert labelList != null;

        labelList.getElements()[0].delete();
        ijLabels.map(IJCaseLabelBase::getElement).forEach(labelList::add);
        psiStatements.forEach(newCase::add);

        return new IJCase.IJStatementCase(newCase, psiStatements);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniJdk12Case createCase(@NotNull List<@NotNull UniCaseLabel> labels, @NotNull UniElement body) {
        Stream<IJCaseLabelBase> ijLabels = checkList(labels, IJCaseLabelBase.class);
        IJElement<?> ijBody = check(body, IJElement.class);

        PsiSwitchLabeledRuleStatement newCase = (PsiSwitchLabeledRuleStatement) factory.createStatementFromText(
                "case \"test\" -> \"test\"", null);

        assert newCase.getBody() != null;
        PsiCaseLabelElementList labelList = newCase.getCaseLabelElementList();

        assert labelList != null;

        labelList.getElements()[0].delete();
        ijLabels.map(IJCaseLabelBase::getElement).forEach(labelList::add);
        newCase.getBody().replace(ijBody.getElement());

        return new IJCase.IJRuleCase(newCase);
    }
}
