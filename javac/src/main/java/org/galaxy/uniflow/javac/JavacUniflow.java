package org.galaxy.uniflow.javac;

import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.code.Types;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Names;
import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.api.factories.UniElementFinder;
import org.galaxy.uniflow.api.factories.UniModuleFactory;
import org.galaxy.uniflow.api.factories.UniTypeFactory;

public class JavacUniflow extends Uniflow {

    private final JavacProcessingEnvironment processingEnvironment;

    public TreeMaker treeMaker;
    public Types types;
    public Names names;
    public Symtab symtab;

    public JavacUniflow(JavacProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;
        treeMaker = TreeMaker.instance(processingEnvironment.getContext());
        types = Types.instance(processingEnvironment.getContext());
        names = Names.instance(processingEnvironment.getContext());
        symtab = Symtab.instance(processingEnvironment.getContext());
    }

    @Override
    protected UniElementFinder createFinder() {
        return null;
    }

    @Override
    protected UniTypeFactory createTypeFactory() {
        return null;
    }

    @Override
    protected UniElementFactory createElementFactory() {
        return null;
    }

    @Override
    protected UniModuleFactory createModuleFactory() {
        return null;
    }

    public static JavacUniflow getInstance() {
        return (JavacUniflow) Uniflow.getInstance();
    }
}
