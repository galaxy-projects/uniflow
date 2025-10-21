package org.galaxy.uniflow.intellij.psi.pattern;

import com.intellij.psi.PsiDeconstructionList;
import com.intellij.psi.PsiDeconstructionPattern;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.pattern.UniDeconstructionPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.intellij.psi.lists.IJList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class IJRecordPattern extends IJPattern<PsiDeconstructionPattern> implements UniDeconstructionPattern {

    public IJRecordPattern(PsiDeconstructionPattern element) {
        super(element);
    }

    @Override
    public void setDeconstructor(@NotNull UniExpression deconstructor) {
        element.getTypeElement().replace(IntellijUnwrapper.unwrapType(deconstructor));
    }

    @Override
    public @NotNull UniExpression getDeconstructor() {
        return UniflowWrapper.wrap(element.getTypeElement().getInnermostComponentReferenceElement());
    }

    @Override
    public @NotNull UniList<@NotNull UniPattern> getNestedPatterns() {
        return new IJList<>(
                element.getDeconstructionList(),
                PsiDeconstructionList::getDeconstructionComponents,
                UniPattern[]::new,
                UniflowWrapper::wrap,
                IntellijUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.ANY_PATTERN;
    }
}
