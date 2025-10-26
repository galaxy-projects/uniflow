package org.galaxy.uniflow.javac;

import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Source;
import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.code.Types;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;
import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.factories.UniElementFinder;
import org.galaxy.uniflow.api.factories.UniFiler;
import org.galaxy.uniflow.api.factories.UniMessenger;
import org.galaxy.uniflow.api.factories.UniTypeFactory;
import org.galaxy.uniflow.api.processing.UniProcessingEnvironment;
import org.galaxy.uniflow.javac.factories.*;
import org.galaxy.uniflow.javac10.Javac10Uniflow;
import org.galaxy.uniflow.javac12.Javac12Uniflow;
import org.galaxy.uniflow.javac15.Javac15Uniflow;
import org.galaxy.uniflow.javac21.Javac21Uniflow;
import org.galaxy.uniflow.javac25.Javac25Uniflow;
import org.galaxy.uniflow.javac8.Javac8Uniflow;
import org.galaxy.uniflow.javac9.Javac9Uniflow;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;

public abstract class JavacUniflow extends Uniflow {

    public TreeMaker treeMaker;
    public Types types;
    public Names names;
    public Symtab symtab;
    public JavacTrees trees;
    public Source source;
    public Filer filer;
    public Messager messager;
    public JavacElements elements;

    public JavacUniflow(com.sun.tools.javac.processing.JavacProcessingEnvironment processingEnvironment) {
        Context context = processingEnvironment.getContext();

        treeMaker = TreeMaker.instance(context);
        types = Types.instance(context);
        names = Names.instance(context);
        symtab = Symtab.instance(context);
        trees = JavacTrees.instance(context);
        source = Source.instance(context);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elements = JavacElements.instance(context);
    }

    public @NotNull UniProcessingEnvironment createRoundEnvironment(@NotNull RoundEnvironment roundEnv) {
        return new JavacProcessingEnvironmentImpl(roundEnv);
    }

    @Override
    protected @NotNull UniElementFinder createFinder() {
        return new JavacElementFinder();
    }

    @Override
    protected @NotNull UniTypeFactory createTypeFactory() {
        return new JavacTypeFactory();
    }

    public VersionedWrapper getVersionedWrapper() {
        return null;
    }

    @Override
    public @NotNull UniFiler createFiler() {
        return new JavacFiler();
    }

    @Override
    public @NotNull UniMessenger createMessenger() {
        return new JavacMessenger();
    }

    public static @NotNull JavacUniflow getInstance() {
        return (JavacUniflow) Uniflow.getInstance();
    }

    public static @NotNull Uniflow create(@NotNull ProcessingEnvironment environment) {
        if (!(environment instanceof JavacProcessingEnvironment))
            throw new IllegalArgumentException("environment must be an instance of JavacProcessingEnvironment");
        JavacProcessingEnvironment processingEnvironment = (JavacProcessingEnvironment) environment;
        Source source = Source.instance(processingEnvironment.getContext());

        if (isSourceAtLeast(source, "JDK25"))
            return new Javac25Uniflow(processingEnvironment);
        else if (isSourceAtLeast(source, "JDK21"))
            return new Javac21Uniflow(processingEnvironment);
        else if (isSourceAtLeast(source, "JDK15"))
            return new Javac15Uniflow(processingEnvironment);
        else if (isSourceAtLeast(source, "JDK12"))
            return new Javac12Uniflow(processingEnvironment);
        else if (isSourceAtLeast(source, "JDK10"))
            return new Javac10Uniflow(processingEnvironment);
        else if (isSourceAtLeast(source, "JDK9"))
            return new Javac9Uniflow(processingEnvironment);
        return new Javac8Uniflow(processingEnvironment);
    }

    public static boolean isCurrentSourceAtLeast(@NotNull String sourceName) {
        return isSourceAtLeast(getInstance().source, sourceName);
    }

    private static boolean isSourceAtLeast(Source source, String sourceName) {
        try {
            Source target = Source.valueOf(sourceName);

            return source.compareTo(target) >= 0;
        } catch (Throwable e) {
            return false;
        }
    }
}
