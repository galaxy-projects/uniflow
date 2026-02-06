package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiCaseLabelElementList;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiSwitchLabelStatement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.labels.UniCaseLabel;
import org.galaxy.uniflow.api.statements.UniJdk8Case;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.elements.labels.IJCaseLabel;
import org.galaxy.uniflow.intellij.psi.lists.statements.IJCaseStatementList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class IJJava8Case extends IJStatement<PsiSwitchLabelStatement> implements UniJdk8Case {

    private final List<PsiStatement> statements;

    public IJJava8Case(PsiSwitchLabelStatement element, List<PsiStatement> statements) {
        super(element);
        this.statements = statements;
    }

    @Override
    public void setLabel(@NotNull UniCaseLabel label) {
        PsiCaseLabelElementList list = element.getCaseLabelElementList();

        if (list == null || list.getElementCount() > 0) {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiSwitchLabelStatement newCase = (PsiSwitchLabelStatement) factory.createStatementFromText(
                    "case \"hello\": return null;", null);

            assert newCase.getCaseLabelElementList() != null;

            newCase.getCaseLabelElementList().getElements()[0].delete();
            list = newCase.getCaseLabelElementList();

            replace(newCase);
        }
        list.add(IntellijUnwrapper.unwrap(label));
    }

    @Override
    public @NotNull UniCaseLabel getLabel() {
        return new IJCaseLabel(Objects.requireNonNull(element.getCaseLabelElementList()).getElements()[0]);
    }

    @Override
    public @NotNull UniList<@NotNull UniStatement> getStatements() {
        return new IJCaseStatementList(element, statements);
    }

    @Override
    public @NotNull Kind getKind() {
        if (element.isDefaultCase())
            return Kind.DEFAULT_CASE_LABEL;
        return Kind.CASE;
    }
}
