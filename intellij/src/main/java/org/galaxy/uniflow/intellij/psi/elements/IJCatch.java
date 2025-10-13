package org.galaxy.uniflow.intellij.psi.elements;

import com.intellij.psi.PsiCatchSection;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiVariable;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.intellij.psi.IJElement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class IJCatch extends IJElement<PsiCatchSection> implements UniCatch {

    public IJCatch(PsiCatchSection element) {
        super(element);
    }

    @Override
    public void setParameter(@NotNull UniVariable parameter) {
        replace(IntellijUnwrapper.unwrap(parameter), element.getCatchBlock());
    }

    @Override
    public @NotNull UniVariable getParameter() {
        return UniflowWrapper.wrap(element.getParameter());
    }

    @Override
    public void setBody(@NotNull UniBlock body) {
        assert element.getParameter() != null;
        replace(element.getParameter(), IntellijUnwrapper.unwrap(body));
    }

    @Override
    public @NotNull UniBlock getBody() {
        return UniflowWrapper.wrap(element.getCatchBlock());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.CATCH;
    }

    private void replace(PsiVariable parameter, PsiCodeBlock body) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiCatchSection section = factory.createCatchSection(parameter.getType(), "e", body);

        assert section.getParameter() != null;
        assert section.getCatchBlock() != null;

        section.getParameter().replace(parameter);

        replace(section);
    }
}
