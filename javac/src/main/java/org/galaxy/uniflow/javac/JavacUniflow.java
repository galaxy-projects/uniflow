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
import org.galaxy.uniflow.api.factories.*;
import org.galaxy.uniflow.api.processing.UniProcessingEnvironment;
import org.galaxy.uniflow.javac.factories.*;
import org.galaxy.uniflow.javac9.Javac9Uniflow;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;

public class JavacUniflow extends Uniflow {

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

    @Override
    protected @NotNull UniElementFactory createElementFactory() {
        return new JavacElementFactory();
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

        if (source.compareTo(Source.JDK9) >= 0)
            return new Javac9Uniflow(processingEnvironment);
        return new JavacUniflow(processingEnvironment);
    }
}
