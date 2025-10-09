package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiArrayInitializerExpression;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;

public class IJArrayInitializers extends IJList<PsiArrayInitializerExpression, PsiExpression, UniExpression> {

    public IJArrayInitializers(PsiArrayInitializerExpression list) {
        super(list, UniExpression[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrap);
    }

    @Override
    protected PsiExpression[] getElements() {
        return list.getInitializers();
    }

    @Override
    protected PsiArrayInitializerExpression createEmptyList() {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;

        return (PsiArrayInitializerExpression) factory.createExpressionFromText("{}", null);
    }
}
