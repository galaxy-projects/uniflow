package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiMethodCallExpression;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;

public class IJExpressionList extends IJList<PsiExpressionList, PsiExpression, UniExpression> {

    public IJExpressionList(PsiExpressionList list) {
        super(list, UniExpression[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrap);
    }

    @Override
    protected PsiExpression[] getElements() {
        return list.getExpressions();
    }

    @Override
    protected PsiExpressionList createEmptyList() {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiMethodCallExpression call = (PsiMethodCallExpression) factory.createExpressionFromText("foo()", null);

        return call.getArgumentList();
    }
}
