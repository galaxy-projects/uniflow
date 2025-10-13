package org.galaxy.uniflow.intellij.psi.lists.statements;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassInitializer;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.intellij.psi.lists.IJList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;

public class IJClassInitializerList extends IJList<PsiClass, PsiClassInitializer, UniClassInitializer> {

    public IJClassInitializerList(PsiClass psiClass) {
        super(psiClass, UniClassInitializer[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrap);
    }

    @Override
    protected PsiClassInitializer[] getElements() {
        return list.getInitializers();
    }

    @Override
    protected PsiClass createEmptyList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        for (PsiClassInitializer initializer : list.getInitializers())
            initializer.delete();
    }
}
