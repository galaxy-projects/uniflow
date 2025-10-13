package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.statements.UniField;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJFieldList extends IJList<PsiClass, PsiField, UniField> implements UniFieldList {

    public IJFieldList(PsiClass psiClass) {
        super(psiClass, UniField[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrap);
    }

    @Override
    protected PsiField[] getElements() {
        return list.getFields();
    }

    @Override
    protected PsiClass createEmptyList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        for (PsiField field : list.getFields())
            field.delete();
    }

    @Override
    public void removeField(@NotNull String name) {
        for (PsiField field : list.getFields()) {
            if (field.getName().equals(name)) {
                field.delete();
                break;
            }
        }
    }

    @Override
    public @Nullable UniField getField(@NotNull String name) {
        for (PsiField field : list.getFields()) {
            if (field.getName().equals(name))
                return UniflowWrapper.wrap(field);
        }
        return null;
    }
}
