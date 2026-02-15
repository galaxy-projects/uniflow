package org.galaxy.uniflow.intellij.psi.lists;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.lists.UniMethodList;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.intellij.psi.signature.IJMethodSignature;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class IJMethodList extends IJList<PsiClass, PsiMethod, UniMethod> implements UniMethodList {

    public IJMethodList(PsiClass psiClass, Function<PsiClass, PsiMethod[]> getter) {
        super(psiClass, getter, UniMethod[]::new, UniflowWrapper::wrap, IntellijUnwrapper::unwrap);
    }

    @Override
    public void removeMethod(@NotNull UniMethodSignature signature) {
        UniMethod method = getMethod(signature);

        if (method != null)
            remove(method);
    }

    @Override
    public @NotNull UniMethod @NotNull [] getMethods(@NotNull String name) {
        return elementStream()
                .filter(method -> method.getName().equals(name))
                .map(UniflowWrapper::wrap)
                .toArray(UniMethod[]::new);
    }

    @Override
    public @Nullable UniMethod getMethod(@NotNull UniMethodSignature signature) {
        return elementStream()
                .filter(method -> new IJMethodSignature(method).equals(signature))
                .findFirst()
                .map(UniflowWrapper::wrap)
                .orElse(null);
    }
}
