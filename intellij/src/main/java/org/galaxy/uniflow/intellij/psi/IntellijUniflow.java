package org.galaxy.uniflow.intellij.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.factories.*;
import org.jetbrains.annotations.NotNull;

public class IntellijUniflow extends Uniflow {

    public PsiElementFactory factory;

    public IntellijUniflow(Project project) {
        JavaPsiFacade psiFacade = JavaPsiFacade.getInstance(project);

        factory = psiFacade.getElementFactory();
    }

    @Override
    protected @NotNull UniElementFinder createFinder() {
        return null;
    }

    @Override
    protected @NotNull UniTypeFactory createTypeFactory() {
        return null;
    }

    @Override
    protected @NotNull UniElementFactory createElementFactory() {
        return null;
    }

    @Override
    public @NotNull UniFiler createFiler() {
        return null;
    }

    @Override
    public @NotNull UniMessenger createMessenger() {
        return null;
    }

    public static @NotNull IntellijUniflow getInstance() {
        return (IntellijUniflow) Uniflow.getInstance();
    }
}
