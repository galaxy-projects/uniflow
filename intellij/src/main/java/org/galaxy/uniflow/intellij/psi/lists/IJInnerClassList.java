package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiClass;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;

public class IJInnerClassList extends IJList<PsiClass, PsiClass, UniClass> {

    public IJInnerClassList(PsiClass psiClass) {
        super(psiClass, UniClass[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrap);
    }

    @Override
    protected PsiClass[] getElements() {
        return list.getInnerClasses();
    }

    @Override
    protected PsiClass createEmptyList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        for (PsiClass innerClass : list.getInnerClasses())
            innerClass.delete();
    }
}
