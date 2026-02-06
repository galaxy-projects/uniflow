package org.galaxy.uniflow.intellij.psi;

import com.intellij.openapi.module.LanguageLevelUtil;
import com.intellij.openapi.module.Module;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiJavaParserFacade;
import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.factories.*;
import org.galaxy.uniflow.intellij.psi.factories.*;
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
        LanguageLevel level = LanguageLevelUtil.getEffectiveLanguageLevel(module);
        PsiJavaParserFacade parser = facade.getParserFacade();
        PsiFileFactory files = PsiFileFactory.getInstance(module.getProject());

        if (level.isAtLeast(LanguageLevel.JDK_25))
            return new IntellijJava25ElementFactory(factory, parser, files);
        else if (level.isAtLeast(LanguageLevel.JDK_21))
            return new IntellijJava21ElementFactory(factory, parser, files);
        else if (level.isAtLeast(LanguageLevel.JDK_15))
            return new IntellijJava15ElementFactory(factory, parser, files);
        else if (level.isAtLeast(LanguageLevel.JDK_12))
            return new IntellijJava12ElementFactory(factory, parser, files);
        else if (level.isAtLeast(LanguageLevel.JDK_10))
            return new IntellijJava10ElementFactory(factory, parser, files);
        else if (level.isAtLeast(LanguageLevel.JDK_1_9))
            return new IntellijJava9ElementFactory(factory, parser, files);

        return new IntellijElementFactory(factory, parser, files);
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
