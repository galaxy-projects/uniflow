package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiDisjunctionType;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniUnionType;
import org.galaxy.uniflow.intellij.psi.lists.IJReadOnlyList;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJUnionType extends IJType<PsiDisjunctionType> implements UniUnionType {

    public IJUnionType(PsiDisjunctionType type) {
        super(type);
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getTypeAlternatives() {
        return new IJReadOnlyList<>(type.getDisjunctions().stream().map(UniflowWrapper::wrap).toList(), UniType[]::new);
    }
}
