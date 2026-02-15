package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiAssignmentExpression;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import org.galaxy.uniflow.api.expressions.UniAssignment;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IJAssignment extends IJExpression<PsiAssignmentExpression> implements UniAssignment {

    public IJAssignment(PsiAssignmentExpression element) {
        super(element);
    }

    @Override
    public void setVariable(@NotNull UniExpression variable) {
        element.getLExpression().replace(IntellijUnwrapper.unwrap(variable));
    }

    @Override
    public @NotNull UniExpression getVariable() {
        return UniflowWrapper.wrap(element.getLExpression());
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        setExpression(element, expression, this::replace);
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(element.getRExpression());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.ASSIGNMENT;
    }

    static void setExpression(PsiAssignmentExpression element,
                              UniExpression expression,
                              Consumer<PsiAssignmentExpression> replace) {
        PsiExpression unwrap = IntellijUnwrapper.unwrap(expression);

        if (element.getRExpression() != null)
            element.getRExpression().replace(unwrap);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiAssignmentExpression dummy = (PsiAssignmentExpression) factory.createTypeElementFromText(
                    element.getLExpression().getText() + " = 0", null);

            assert dummy.getRExpression() != null;
            dummy.getOperationSign().replace(element.getOperationSign());
            dummy.getRExpression().replace(unwrap);
            replace.accept(dummy);
        }
    }

    public static UniExpression create(PsiAssignmentExpression expression) {
        if (expression.getOperationSign().getTokenType() == JavaTokenType.EQ)
            return new IJAssignment(expression);
        return new IJCompoundAssignment(expression);
    }
}
