package org.galaxy.uniflow.javac;

import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.code.Types;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Names;
import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.factories.*;
import org.galaxy.uniflow.javac.factories.JavacEnvironment;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.RoundEnvironment;

public class JavacUniflow extends Uniflow {

    public TreeMaker treeMaker;
    public Types types;
    public Names names;
    public Symtab symtab;
    public JavacTrees trees;

    public JavacUniflow(JavacProcessingEnvironment processingEnvironment) {
        treeMaker = TreeMaker.instance(processingEnvironment.getContext());
        types = Types.instance(processingEnvironment.getContext());
        names = Names.instance(processingEnvironment.getContext());
        symtab = Symtab.instance(processingEnvironment.getContext());
        trees = JavacTrees.instance(processingEnvironment.getContext());
    }

    @Override
    public @NotNull UniEnvironment createEnvironment(@NotNull RoundEnvironment roundEnv) {
        return new JavacEnvironment(roundEnv);
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
    protected @NotNull UniModuleFactory createModuleFactory() {
        return null;
    }

    public static @NotNull JavacUniflow getInstance() {
        return (JavacUniflow) Uniflow.getInstance();
    }
}
