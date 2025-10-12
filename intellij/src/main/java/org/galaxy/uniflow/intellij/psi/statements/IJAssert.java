package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiAssertStatement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniAssert;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJAssert extends IJStatement<PsiAssertStatement> implements UniAssert {

    public IJAssert(PsiAssertStatement element) {
        super(element);
    }

    @Override
    public void setCondition(@NotNull UniExpression condition) {
        PsiExpression expression = IntellijUnwrapper.unwrap(condition);

        if (element.getAssertCondition() != null)
            element.getAssertCondition().replace(expression);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            String text = "assert condition" + (element.getAssertDescription() != null ? " : desc" : "");
            PsiAssertStatement newAssert = (PsiAssertStatement) factory.createStatementFromText(text, null);

            assert newAssert.getAssertCondition() != null;
            newAssert.getAssertCondition().replace(expression);
            if (element.getAssertDescription() != null) {
                assert newAssert.getAssertDescription() != null;
                newAssert.getAssertDescription().replace(element.getAssertDescription());
            }
            replace(newAssert);
        }
    }

    @Override
    public @NotNull UniExpression getCondition() {
        return UniflowWrapper.wrap(element.getAssertCondition());
    }

    @Override
    public void setDetails(@Nullable UniExpression details) {
        PsiExpression expression = IntellijUnwrapper.unwrap(details);

        if (element.getAssertDescription() != null)
            element.getAssertDescription().replace(expression);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiAssertStatement newAssert = (PsiAssertStatement) factory.createStatementFromText(
                    "assert condition : desc", null);

            assert element.getAssertCondition() != null;
            assert element.getAssertDescription() != null;
            assert newAssert.getAssertCondition() != null;
            assert newAssert.getAssertDescription() != null;
            newAssert.getAssertCondition().replace(element.getAssertCondition());
            newAssert.getAssertDescription().replace(expression);
            replace(newAssert);
        }
    }

    @Override
    public @Nullable UniExpression getDetails() {
        return UniflowWrapper.wrap(element.getAssertDescription());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.ASSERT;
    }
}
