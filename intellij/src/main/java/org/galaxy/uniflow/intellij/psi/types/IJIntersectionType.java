package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.psi.PsiIntersectionType;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniIntersectionType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.lists.IJEmptyList;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class IJIntersectionType extends IJType<PsiIntersectionType> implements UniIntersectionType {

    public IJIntersectionType(PsiIntersectionType type) {
        super(type);
    }

    @Override
    public @NotNull List<@NotNull UniType> getComponents() {
        return Arrays.stream(type.getConjuncts()).map(UniflowWrapper::wrap).toList();
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getInterfaces() {
        return IJEmptyList.create(UniType.class);
    }

    @Override
    public void setSupertype(@NotNull UniType supertype) {}

    @Override
    public @NotNull UniType getSupertype() {
        throw new UnsupportedOperationException();
    }
}
