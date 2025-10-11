package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiSwitchBlock;
import com.intellij.psi.PsiSwitchStatement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniSwitch;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.lists.IJCaseList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IJSwitchStatement extends IJStatement<PsiSwitchStatement> implements UniSwitch {

    public IJSwitchStatement(PsiSwitchStatement element) {
        super(element);
    }

    @Override
    public void setSelector(@NotNull UniExpression selector) {
        replaceSelector(element, selector, this::replace);
    }

    @Override
    public @NotNull UniExpression getSelector() {
        return UniflowWrapper.wrap(element.getExpression());
    }

    @Override
    public @NotNull UniList<UniCase> getCases() {
        return new IJCaseList(element);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.SWITCH;
    }

    @SuppressWarnings("unchecked")
    public static <T extends PsiSwitchBlock> void replaceSelector(T switchBlock,
                                                                  UniExpression selector,
                                                                  Consumer<T> replace) {
        PsiExpression expression = IntellijUnwrapper.unwrap(selector);

        if (switchBlock.getExpression() != null)
            switchBlock.getExpression().replace(expression);
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            T dummy = (T) factory.createExpressionFromText("switch (a) {}", switchBlock);

            assert dummy.getExpression() != null;
            assert switchBlock.getBody() != null;
            assert dummy.getBody() != null;
            dummy.getExpression().replace(expression);
            dummy.getBody().replace(switchBlock.getBody());
            replace.accept(dummy);
        }
    }
}
