package org.galaxy.uniflow.intellij.psi;

import com.intellij.openapi.module.Module;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFileFactory;
import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.factories.*;
import org.galaxy.uniflow.intellij.psi.factories.IntellijElementFactory;
import org.jetbrains.annotations.NotNull;

public class IntellijUniflow extends Uniflow {

    public Module module;
    public JavaPsiFacade facade;
    public PsiElementFactory factory;

    public IntellijUniflow(Module module) {
        this.module = module;
        facade = JavaPsiFacade.getInstance(module.getProject());
        factory = facade.getElementFactory();
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
        // TODO: check by java version
//        LanguageLevel level = LanguageLevelUtil.getEffectiveLanguageLevel(module);
//
//        if (level.isAtLeast(LanguageLevel.JDK_1_8)) {
//
//        }

        return new IntellijElementFactory(factory, facade.getParserFacade(),
                PsiFileFactory.getInstance(module.getProject()));
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
