package org.galaxy.uniflow.javac9;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.galaxy.uniflow.javac8.Uniflow8Wrapper;
import org.galaxy.uniflow.javac9.modules.JavacModule;
import org.galaxy.uniflow.javac9.modules.directives.*;
import org.jetbrains.annotations.NotNull;

public class Uniflow9Wrapper extends Uniflow8Wrapper {

    public static final Uniflow9Wrapper INSTANCE = new Uniflow9Wrapper();

    @Override
    public UniElement wrap(JCTree element) {
        if (Reflection.MODULE_TYPE.isInstance(element))
            return wrapModule(element);
        else if (Reflection.DIRECTIVE_TYPE.isInstance(element))
            return wrapDirective(element);
        else if (element instanceof JCTree.JCCompilationUnit)
            return new Javac9CompilationUnit((JCTree.JCCompilationUnit) element);
        return super.wrap(element);
    }

    public static @NotNull UniModule wrapModule(JCTree module) {
        return new JavacModule(module);
    }

    public static @NotNull UniDirective wrapDirective(JCTree directive) {
        if (Reflection.EXPORTS_TYPE.isInstance(directive))
            return new JavacExports(directive);
        else if (Reflection.OPENS_TYPE.isInstance(directive))
            return new JavacOpens(directive);
        else if (Reflection.PROVIDES_TYPE.isInstance(directive))
            return new JavacProvides(directive);
        else if (Reflection.REQUIRES_TYPE.isInstance(directive))
            return new JavacRequires(directive);
        else if (Reflection.USES_TYPE.isInstance(directive))
            return new JavacUses(directive);
        throw new IllegalArgumentException("Unknown directive: " + directive);
    }
}
